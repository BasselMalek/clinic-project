package com.clinic.project.Adapters.Repositories;


import com.clinic.project.Domain.Model.Bill;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findById(Long id);

    List<Bill> findByUser_Id(Long userId);

    // OR, if you want to query by the appointment ID
    @Query("SELECT b FROM Bill b WHERE b.appointment.id = :appointmentId")
    List<Bill> findByAppointmentId(@Param("appointmentId") Long appointmentId);

    void deleteById(Long id);

}
