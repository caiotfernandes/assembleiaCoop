package com.caiotfernandes.assembleiaCoop.repositories;

import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
}
