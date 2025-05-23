package com.clinic.project.Domain.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name = "billing_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BillingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String label;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    public BillingItem() {
    }

    public BillingItem(String label, BigDecimal value, Bill bill) {
    
        this.label = label;
        this.value = value;
        this.bill = bill;
    }

    // Getters and Setters
    public long getId() {
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
