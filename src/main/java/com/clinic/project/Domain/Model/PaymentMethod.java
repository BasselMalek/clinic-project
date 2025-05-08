package com.clinic.project.Domain.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String name;

    @Column(nullable = false)
    private long patientId;

    @Column(precision = 10, scale = 2)
    private BigDecimal balance;

    public PaymentMethod(){}

    public PaymentMethod(long patientId, String name, BigDecimal init){
        this.patientId = patientId;
        this.balance = init;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getPatientId() {
        return this.patientId;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void modifyBalance(BigDecimal adjustment){
        if (this.balance.add(adjustment).compareTo(BigDecimal.ZERO) < 0){
            throw new RuntimeException("Adjustment cannot take balance into negatives");
        }else {
        this.balance = this.balance.add(adjustment);}

    }

}
