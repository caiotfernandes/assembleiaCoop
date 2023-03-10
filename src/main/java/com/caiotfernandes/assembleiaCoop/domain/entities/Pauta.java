package com.caiotfernandes.assembleiaCoop.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "tb_pauta")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pauta implements Serializable {

    @Serial
    private static final long serialVersionUID = 6359871754727076149L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
