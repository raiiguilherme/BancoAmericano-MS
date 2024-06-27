package org.grupo5.mscustomer.repository;

import org.grupo5.mscustomer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
