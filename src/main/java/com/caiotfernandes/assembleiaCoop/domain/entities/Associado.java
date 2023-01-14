package com.caiotfernandes.assembleiaCoop.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "tb_associado")
public class Associado implements Serializable {

    @Serial
    private static final long serialVersionUID = -6826894923189787545L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
