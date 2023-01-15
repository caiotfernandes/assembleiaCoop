package com.caiotfernandes.assembleiaCoop.repositories;

import com.caiotfernandes.assembleiaCoop.domain.entities.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}
