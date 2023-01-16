package com.caiotfernandes.assembleiaCoop.domain.dtos;

import com.caiotfernandes.assembleiaCoop.domain.entities.VotoSessao;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VotoSessaoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6010293462423167762L;

    @NotNull
    private Long sessaoId;
    @NotNull
    private Long associadoId;
    @NotNull
    private String voto;

    public static VotoSessaoDTO fromVotoSessao(VotoSessao votoSessao) {
        return VotoSessaoDTO.builder()
                .sessaoId(votoSessao.getSessao().getId())
                .associadoId(votoSessao.getAssociado().getId())
                .voto(votoSessao.getVoto().toString())
                .build();
    }

}
