package org.grupo5.mscalculate.repository;

import org.grupo5.mscalculate.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculateRepository extends JpaRepository<Rule, Long> {
}
