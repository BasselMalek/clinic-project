package com.clinic.project.Domain.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment; 

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime issuedDate;
    private LocalDateTime paidDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingItem> items = new ArrayList<>(); // Initialize the list

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(length = 1000)
    private String notes;

    // Constructors
    public Bill() {
    }

    public Bill(Appointment appointment, User patient) { // Updated to use Appointment and Patient entities
        this.appointment = appointment;
        this.user = patient;
        this.status = BillStatus.UNPAID;
        this.issuedDate = LocalDateTime.now();
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public User getPatient() {
        return user;
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
        BigDecimal preTax = this.items.stream().map(BillingItem::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public void markAsPaid(LocalDateTime paymentTime) { // Updated to use long
        this.status = BillStatus.PAID;
        this.paidDate = paymentTime;
    }

    public void cancelBill() {
        this.status = BillStatus.CANCELED;
    }
}
