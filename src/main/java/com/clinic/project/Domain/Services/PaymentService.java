package com.clinic.project.Domain.Services;

import com.clinic.project.Domain.Model.BillStatus;
import com.clinic.project.Domain.Model.PaymentMethod;
import com.clinic.project.Adapters.Repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final BillingService billingService;

    @Autowired
    public PaymentService(PaymentMethodRepository paymentMethodRepository, BillingService billingService) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.billingService = billingService;
    }

    @Transactional
    public PaymentMethod registerPaymentMethod(long patientId, String name, BigDecimal initialBalance) {
        if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        PaymentMethod paymentMethod = new PaymentMethod(patientId, name, initialBalance);
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod getPaymentMethodById(long paymentMethodId) {
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
    }

    public PaymentMethod getPaymentMethodsByPatientId(long patientId) {
        return paymentMethodRepository.findByPatientId(patientId).orElseThrow(() -> new RuntimeException("No payment method for this account"));
    }

    @Transactional
    public PaymentMethod modifyBalance(long paymentMethodId, BigDecimal amount) {
        PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);
        try {
            paymentMethod.modifyBalance(amount);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed Payment.");
        }
        return paymentMethodRepository.save(paymentMethod);
    }

    @Transactional
    public PaymentMethod payBill(long paymentMethodId, long billId) {
        if (billingService.getBill(billId).getStatus() == BillStatus.UNPAID) {
            PaymentMethod paymentMethod = getPaymentMethodById(paymentMethodId);
            try {
                paymentMethod.modifyBalance(billingService.getBill(billId).getTotalAmount());
            } catch (RuntimeException e) {
                throw new RuntimeException("Failed Payment.");
            }
            billingService.markBillAsPaid(billId);
            return paymentMethodRepository.save(paymentMethod);
        } else {
            throw new RuntimeException("Invalid bill option");
        }

    }
}