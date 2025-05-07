package com.clinic.project.Adapters.Repositories;

import com.clinic.project.Domain.Model.Appointment;
import com.clinic.project.Domain.Model.AppointmentStatus;
import com.clinic.project.Domain.Model.AppointmentType;
import com.clinic.project.Domain.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByDoctor(User doctor);
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByType(AppointmentType type);
}

