package com.caiotfernandes.assembleiaCoop.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "tb_sessao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Sessao implements Serializable {

    @Serial
    private static final long serialVersionUID = -16211818096984659L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessao_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

    private Date startDate;

    private Date endDate;

    @OneToMany(mappedBy = "id.sessao")
    private List<VotoSessao> votoList = new ArrayList<>();
}
