package org.grupo5.mspayment.repository;

import org.grupo5.mspayment.domain.Payment;
import org.grupo5.mspayment.domain.dtos.PaymentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT u FROM Payment u WHERE u.customer_Id = :id")
    Payment find(Long id);


}
