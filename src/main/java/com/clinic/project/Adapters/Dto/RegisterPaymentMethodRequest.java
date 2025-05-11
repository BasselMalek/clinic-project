package com.clinic.project.Adapters.Dto;

public class RegisterPaymentMethodRequest {
    private long patientId;
    private String name;

    // Getters and Setters
    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}