package com.clinic.project.Adapters.Controllers;

import com.clinic.project.Domain.Model.Bill;
import com.clinic.project.Domain.Model.BillingItem;
import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Services.BillingService;
import com.clinic.project.Adapters.Repositories.AppointmentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final BillingService billingService;
    private final AppointmentRepository appointmentRepository;

    public BillingController(BillingService billingService, AppointmentRepository appointmentRepository) {
        this.billingService = billingService;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/{billId}")
    public ResponseEntity<Bill> getBill(@PathVariable Long billId) {
        try {
            Bill bill = billingService.getBill(billId);
            return ResponseEntity.ok(bill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bill>> getUserBills(@PathVariable Long userId) {
        try {
            List<Bill> bills = billingService.getBillsByUserId(userId);
            return ResponseEntity.ok(bills);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Bill> getBillByAppointment(@PathVariable Long appointmentId) {
        try {
            Bill bill = billingService.getBillByAppointment(appointmentId);
            return ResponseEntity.ok(bill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PostMapping("/generate/{appointmentId}")
    public ResponseEntity<Bill> generateBill(@PathVariable Long appointmentId) {
        try {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found with ID: " + appointmentId));

            // if (appointment.getType() == null) {
            //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment type is not set for appointment ID: " + appointmentId);
            // }
    
            Bill bill = billingService.generateBillFromAppointment(appointmentId);
            return ResponseEntity.ok(bill);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{billId}/items")
    public ResponseEntity<Bill> updateBillItems(@PathVariable Long billId,
                                                @RequestBody List<BillingItem> items) {
        try {
            Bill updatedBill = billingService.updateBillItems(billId, items);
            return ResponseEntity.ok(updatedBill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PutMapping("/{billId}/notes")
    public ResponseEntity<Bill> addNotesToBill(@PathVariable Long billId,
                                               @RequestBody String note) {
        try {
            Bill updatedBill = billingService.notateBill(billId, note);
            return ResponseEntity.ok(updatedBill);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}