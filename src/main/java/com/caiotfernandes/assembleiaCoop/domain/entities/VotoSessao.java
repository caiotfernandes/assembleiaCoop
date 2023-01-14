package com.caiotfernandes.assembleiaCoop.domain.entities;

import com.caiotfernandes.assembleiaCoop.domain.enums.Voto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "tb_voto_sessao")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotoSessao implements Serializable {

    @Serial
    private static final long serialVersionUID = -9002612992993432123L;

    @EmbeddedId
    private VotoSessaoId id;

    @Enumerated(EnumType.STRING)
    private Voto voto;

    @Embeddable
    static class VotoSessaoId {

        @ManyToOne
        @JoinColumn(name = "sessao_id")
        private Sessao sessao;
        @JoinColumn(name = "associado_id")
        private Associado associado;

    }

}
