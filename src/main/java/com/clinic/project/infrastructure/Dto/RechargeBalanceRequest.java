package com.clinic.project.infrastructure.Dto;

public class RechargeBalanceRequest {
    
    private long paymentMethodId;
    private double amount;

    // Getters and Setters
    public long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
