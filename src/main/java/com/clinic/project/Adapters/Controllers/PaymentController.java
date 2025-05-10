package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Domain.Model.Bill;
import com.clinic.project.Domain.Model.BillStatus;
import com.clinic.project.Domain.Model.PaymentMethod;
import com.clinic.project.Domain.Services.BillingService;
import com.clinic.project.Domain.Services.PaymentService;
import com.clinic.project.infrastructure.Dto.PayBillRequest;
import com.clinic.project.infrastructure.Dto.RechargeBalanceRequest;
import com.clinic.project.infrastructure.Dto.RegisterPaymentMethodRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final BillingService billingService;

    @Autowired
    public PaymentController(PaymentService paymentService, BillingService billingService) {
        this.paymentService = paymentService;
        this.billingService = billingService;
    }

    @PostMapping("/register")
    public ResponseEntity<PaymentMethod> registerPaymentMethod(@RequestBody RegisterPaymentMethodRequest request) {
        try {
            PaymentMethod paymentMethod = paymentService.registerPaymentMethod(request.getPatientId(),
                    request.getName(), BigDecimal.ZERO);
            return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/method/{patientId}")
    public ResponseEntity<PaymentMethod> getPaymentMethods(@PathVariable long patientId) {
        try {
            PaymentMethod paymentMethod = paymentService.getPaymentMethodsByPatientId(patientId);
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/balance/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> checkBalance(@PathVariable long paymentMethodId) {
        try {
            PaymentMethod paymentMethod = paymentService.getPaymentMethodById(paymentMethodId);
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/recharge/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> rechargeBalance(
            @PathVariable long paymentMethodId,
            @RequestBody RechargeBalanceRequest request) {
        try {
            PaymentMethod paymentMethod = paymentService.modifyBalance(paymentMethodId,
                    new BigDecimal(request.getAmount()));
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pay/{billId}")
    public ResponseEntity<PaymentMethod> payBill(
            @PathVariable long billId,
            @RequestBody PayBillRequest request) {
        try {
            // Validate the payment method
            PaymentMethod paymentMethod = paymentService.getPaymentMethodById(request.getPaymentMethodId());
            if (paymentMethod == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method not found");
            }

            // Validate the bill
            Bill bill = billingService.getBill(billId);
            if (bill == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bill not found");
            }

            // Check if the bill is unpaid
            if (bill.getStatus() != BillStatus.UNPAID) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bill is not in UNPAID status");
            }

            // Check if the payment method has sufficient balance
            BigDecimal billAmount = bill.getTotalAmount();
            if (paymentMethod.getBalance().compareTo(billAmount) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance in payment method");
            }

            // Process the payment
            paymentMethod = paymentService.payBill(request.getPaymentMethodId(), billId);
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);

        } catch (ResponseStatusException e) {
            throw e; // Re-throw ResponseStatusException for proper HTTP response
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred: " + e.getMessage());
        }
    }
}