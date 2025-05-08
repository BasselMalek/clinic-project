package com.clinic.project.Domain.Services;

import com.clinic.project.Adapters.Repositories.AppointmentRepository;
import com.clinic.project.Adapters.Repositories.BillingRepository;
import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Model.AppointmentStatus;
import com.clinic.project.Domain.Model.Bill;
import com.clinic.project.Domain.Model.BillingItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final AppointmentRepository appointmentRepository;

    public BillingService(BillingRepository billingRepository, AppointmentRepository appointmentRepository) {
        this.billingRepository = billingRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    public Bill getBill(Long billId) {
        return billingRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found with ID: " + billId));
    }

    @Transactional(readOnly = true)
    public List<Bill> getBillsByPatientId(Long patientId) {
        return billingRepository.findByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public Bill getBillByAppointmentId(Long appointmentId) {
        List<Bill> bills = billingRepository.findByAppointmentId(appointmentId);
        if (bills.isEmpty()) {
            throw new IllegalArgumentException("Bill not found for appointment ID: " + appointmentId);
        }
        return bills.get(0); // Assuming you want the first bill in the list
    }

    @Transactional
    public Bill generateBillFromAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));

        if (appointment.getStatus() != AppointmentStatus.ATTENDED) {
            throw new IllegalStateException("Cannot generate bill for an appointment that was not attended");
        }

        Long patientId = appointment.getPatient().getId();

        Bill bill = new Bill(appointmentId, patientId);
        List<BillingItem> billItems = new ArrayList<>();

        // Add standard appointment fee
        billItems.add(new BillingItem("Standard Consult Fee", new BigDecimal("100.00"), bill));

        // Add additional fees based on appointment type
        switch (appointment.getType()) {
            case PROCEDURE:
                billItems.add(new BillingItem("In-office Procedure Fee", new BigDecimal("500.00"), bill));
                break;
            case CHECKUP:
                billItems.add(new BillingItem("Regular Checkup Fee", new BigDecimal("50.00"), bill));
                break;
            case PHYSICAL:
                billItems.add(new BillingItem("Full Physical Fee", new BigDecimal("150.00"), bill));
                break;
            case CONSULT:
            default:
                break;
        }

        bill.setItems(billItems);
        return billingRepository.save(bill);
    }

    @Transactional
    public Bill updateBillItems(Long billId, List<BillingItem> items) {
        Bill bill = billingRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found with ID: " + billId));
        bill.setItems(items);
        return billingRepository.save(bill);
    }

    @Transactional
    public Bill markBillAsPaid(Long billId) {
        Bill bill = billingRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found with ID: " + billId));

        bill.markAsPaid(LocalDateTime.now());
        return billingRepository.save(bill);
    }

    @Transactional
    public Bill notateBill(Long billId, String note) {
        Bill bill = billingRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("Bill not found with ID: " + billId));
        bill.setNotes(note);
        return billingRepository.save(bill);
    }
}