package com.clinic.project.Adapters.Repositories;

import com.clinic.project.Domain.Model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
     Optional<PaymentMethod> findByPatientId(long id);
}
