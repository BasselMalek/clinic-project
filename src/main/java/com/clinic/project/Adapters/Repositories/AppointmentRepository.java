package com.clinic.project.Adapters.Repositories;

import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctor(User doctor);
}

