package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Domain.Model.Bill;
import com.clinic.project.Domain.Model.BillingItem;
import com.clinic.project.Domain.Services.BillingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }


    @GetMapping("/{billId}")
    public ResponseEntity<Bill> getBill(@PathVariable UUID billId) {
        try {
            Bill bill = billingService.getBill(billId);
            return ResponseEntity.ok(bill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Bill>> getPatientBills(@PathVariable UUID patientId) {
        List<Bill> bills = billingService.getBillsByPatientId(patientId);
        return ResponseEntity.ok(bills);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Bill> getBillByAppointment(@PathVariable UUID appointmentId) {
        try {
            Bill bill = billingService.getBillByAppointmentId(appointmentId);
            return ResponseEntity.ok(bill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/generate/{appointmentId}")
    public ResponseEntity<Bill> generateBill(@PathVariable UUID appointmentId) {
        try {
            Bill bill = billingService.generateBillFromAppointment(appointmentId);
            return ResponseEntity.ok(bill);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{billId}/items")
    public ResponseEntity<Bill> updateBillItems(@PathVariable UUID billId,
                                                @RequestBody List<BillingItem> items) {
        try {
            Bill updatedBill = billingService.updateBillItems(billId, items);
            return ResponseEntity.ok(updatedBill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{billId}/pay")
    public ResponseEntity<Bill> markBillAsPaid(@PathVariable UUID billId,
                                               @RequestBody UUID paymentId) {
        try {
            Bill paidBill = billingService.markBillAsPaid(billId, paymentId);
            return ResponseEntity.ok(paidBill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{billId}/notes")
    public ResponseEntity<Bill> addNotesToBill(@PathVariable UUID billId,
                                               @RequestBody String note) {
        try {
            Bill updatedBill = billingService.notateBill(billId, note);
            return ResponseEntity.ok(updatedBill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}