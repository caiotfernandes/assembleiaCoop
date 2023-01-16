package com.caiotfernandes.assembleiaCoop.domain.entities;

import com.caiotfernandes.assembleiaCoop.domain.enums.Voto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "tb_voto_sessao")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"sessao","associado"})
public class VotoSessao implements Serializable {

    @Serial
    private static final long serialVersionUID = -9002612992993432123L;

    @EmbeddedId
    private VotoSessaoId id;

    @ManyToOne
    @MapsId("sessaoId")
    @JsonBackReference
    private Sessao sessao;

    @ManyToOne
    @MapsId("associadoId")
    private Associado associado;

    @Enumerated(EnumType.STRING)
    private Voto voto;

    public VotoSessao(Sessao sessao, Associado associado, Voto voto) {
        this.sessao = sessao;
        this.associado = associado;
        this.voto = voto;
        this.id = new VotoSessaoId(sessao.getId(), associado.getId());
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(of = {"sessaoId", "associadoId"})
    static class VotoSessaoId implements Serializable{

        @Serial
        private static final long serialVersionUID = -5095017241970414126L;

        @Column(name = "sessao_id")
        private Long sessaoId;
        @Column(name = "associado_id")
        private Long associadoId;
    }
}
