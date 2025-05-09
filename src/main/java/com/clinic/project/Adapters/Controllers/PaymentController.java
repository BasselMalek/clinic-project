package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Domain.Model.Bill;
import com.clinic.project.Domain.Model.PaymentMethod;
import com.clinic.project.Domain.Services.BillingService;
import com.clinic.project.Domain.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService, BillingService billingService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/register")
    public ResponseEntity<PaymentMethod> registerPaymentMethod(@RequestBody long patientId, String name) {
        try {
            PaymentMethod paymentMethod = paymentService.registerPaymentMethod(patientId, name, BigDecimal.ZERO);
            return new ResponseEntity<>(paymentMethod, HttpStatus.CREATED);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/method/{patientId}")
    public ResponseEntity<PaymentMethod> getPaymentMethods(@PathVariable long patientId) {
        try {
            PaymentMethod paymentMethod = paymentService.getPaymentMethodsByPatientId(patientId);
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/balance/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> checkBalance(@PathVariable long paymentMethodId) {
        try {
            PaymentMethod paymentMethod = paymentService.getPaymentMethodById(paymentMethodId);
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (Exception e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/recharge/{paymentMethodId}")
    public ResponseEntity<PaymentMethod> rechargeBalance(
            @PathVariable long paymentMethodId,
            @RequestBody String amount) {
        try {
            PaymentMethod paymentMethod = paymentService.modifyBalance(paymentMethodId, new BigDecimal(amount));
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pay/{billId}")
    public ResponseEntity<PaymentMethod> payBill(
            @PathVariable long billId,
            @RequestBody long patientId) {
        try {
            PaymentMethod paymentMethod = paymentService.getPaymentMethodsByPatientId(patientId);
            paymentMethod = paymentService.payBill(paymentMethod.getId(), billId);
            return new ResponseEntity<>(paymentMethod, HttpStatus.OK);
        } catch (RuntimeException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment Failed");
        }
    }
}