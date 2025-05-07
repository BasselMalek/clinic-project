package com.clinic.project.Domain.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "billing_items")
public class BillingItem {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String label;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    public BillingItem() {
    }

    public BillingItem(String label, BigDecimal value, Bill bill) {
        this.id = UUID.randomUUID();
        this.label = label;
        this.value = value;
        this.bill = bill;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Bill getBill() {
        return bill;
    }
}
