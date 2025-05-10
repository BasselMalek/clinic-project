package com.clinic.project.infrastructure.Dto;

import com.clinic.project.Domain.Model.AppointmentStatus;
import com.clinic.project.Domain.Model.AppointmentType;

import java.time.LocalDateTime;

public class AppointmentRequestDto {
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private Long patientId;
    private String patientUsername;
    private Long doctorId;
    private String doctorUsername;
    private String appointmentType;

    // Getters and Setters
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }
    public String getAppointmentType() {
        return appointmentType;
    }
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
   
}