package com.clinic.project.Domain.Services;

import com.clinic.project.Adapters.Repositories.AppointmentRepository;
import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Model.AppointmentStatus;
import com.clinic.project.Domain.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(User patient) {
        return appointmentRepository.findByPatient(patient);
    }

    public List<Appointment> getAppointmentsByDoctor(User doctor) {
        return appointmentRepository.findByDoctor(doctor);
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment updateStatus(Long id, AppointmentStatus status) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isPresent()) {
            Appointment app = optional.get();
            app.setStatus(status);
            return appointmentRepository.save(app);
        }
        return null;
    }
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
    
    public Appointment updateAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment != null) {
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    
    
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }  
}
