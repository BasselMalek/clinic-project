package com.clinic.project.Adapters.Repositories;

import com.clinic.project.Domain.Model.Bill;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillingRepository {
    Bill save(Bill bill);
    Optional<Bill> findById(UUID id);
    List<Bill> findByPatientId(UUID patientId);
    Optional<Bill> findByAppointmentId(UUID appointmentId);
    void delete(UUID id);
}
