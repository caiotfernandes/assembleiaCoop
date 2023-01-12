package com.caiotfernandes.assembleiaCoop.repositories;

import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
