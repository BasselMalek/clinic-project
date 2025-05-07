package com.clinic.project.Domain.Services;

import com.clinic.project.Adapters.Repositories.AppointmentRepository;
import com.clinic.project.Adapters.Repositories.BillingRepository;
import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Model.AppointmentStatus;
import com.clinic.project.Domain.Model.Bill;
import com.clinic.project.Domain.Model.BillingItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BillingService {
    private final BillingRepository billingRepository;
    private final AppointmentRepository appointmentRepository;

    public BillingService(BillingRepository billingRepository, AppointmentRepository appointmentRepository) {
        this.billingRepository = billingRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public Bill getBill(UUID billId) {
        return billingRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @Transactional(readOnly = true)
    public List<Bill> getBillsByPatientId(UUID patientId) {
        return billingRepository.findByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public Bill getBillByAppointmentId(UUID appointmentId) {
        return billingRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new RuntimeException("Bill not found for this appointment"));
    }

    @Transactional
    public Bill generateBillFromAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (appointment.getStatus() != AppointmentStatus.ATTENDED) {
            throw new IllegalStateException("Cannot generate bill for missed appointment");
        }
        Bill bill = new Bill(UUID.randomUUID(), appointmentId, appointment.getPatientId());

        List<BillingItem> billItems = new ArrayList<>();

        // Add standard appointment fee
        billItems.add(new BillingItem(
                "Standard Consult fee",
                new java.math.BigDecimal("100.00"),
                bill
        ));

        switch (appointment.getType()) {
            case PROCEDURE:
                billItems.add(new BillingItem("In-office Procedure Fee", new BigDecimal("500.00"), bill));
            case CHECKUP:
                billItems.add(new BillingItem("Regular Checkup fee", new BigDecimal("50.00"), bill));
            case PHYSICAL:
                billItems.add(new BillingItem("Full Physical fee", new BigDecimal("150.00"), bill));
            case CONSULT:
            default:
                break;
        }
        bill.setItems(billItems);
        return billingRepository.save(bill);
    }
    @Transactional
    public Bill updateBillItems(UUID billId, List<BillingItem> items) {
        Bill bill = billingRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setItems(items);
        return billingRepository.save(bill);
    }

    @Transactional
    public Bill markBillAsPaid(UUID billId, UUID paymentId) {
        Bill bill = billingRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        bill.markAsPaid(paymentId, LocalDateTime.now());
        return billingRepository.save(bill);
    }

    @Transactional
    public Bill notateBill(UUID billId, String note){
        Bill bill = billingRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
        bill.setNotes(note);
        return billingRepository.save(bill);
    }
}