package com.clinic.project.Domain.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID appointmentId;

    @Column(nullable = false)
    private UUID patientId;

    private LocalDateTime issuedDate;

    @Column(nullable = false)
    private UUID paymentID;

    private LocalDateTime paidDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingItem> items;


    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 1000)
    private String notes;

    // Constructors
    public Bill() {
    }

    public Bill(UUID appointmentId, UUID patientId) {
        this.id = UUID.randomUUID();
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.status = BillStatus.UNPAID;
        this.issuedDate = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }
    public UUID getAppointmentId() {
        return appointmentId;
    }
    public UUID getPatientId() {
        return patientId;
    }

    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public BillStatus getStatus() {
        return status;
    }

    public List<BillingItem> getItems() {
        return items;
    }

    public void setItems(List<BillingItem> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
        BigDecimal preTax = this.items.stream().map(BillingItem::getValue).reduce(BigDecimal.ZERO,
                BigDecimal::add);
        this.items.add(new BillingItem("14% VAT", (preTax.multiply(new BigDecimal("0.14"))), this));
        this.totalAmount = this.items.stream().map(BillingItem::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void markAsPaid(UUID paymentID, LocalDateTime paymentTime){
        this.paymentID = paymentID;
        this.status = BillStatus.PAID;
        this.paidDate = paymentTime;
    }
    public void cancelBill(){
        this.status = BillStatus.CANCELED;
    }
}