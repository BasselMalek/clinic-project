package com.clinic.project.Adapters.Repositories;

import com.clinic.project.Domain.Model.Bill;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findById(long id);
    List<Bill> findByPatientId(long patientId);
    Optional<Bill> findByAppointmentId(long appointmentId);
    
    // Add this method for deletion
    void deleteById(long id);
}
