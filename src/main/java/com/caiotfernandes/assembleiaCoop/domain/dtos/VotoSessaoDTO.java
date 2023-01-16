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

    @NotNull(message = "sessaoId deve ser informado")
    private Long sessaoId;
    @NotNull(message = "associadoId deve ser informado")
    private Long associadoId;
    @NotNull(message = "voto deve ser informado")
    private String voto;

    public static VotoSessaoDTO fromVotoSessao(VotoSessao votoSessao) {
        return VotoSessaoDTO.builder()
                .sessaoId(votoSessao.getSessao().getId())
                .associadoId(votoSessao.getAssociado().getId())
                .voto(votoSessao.getVoto().toString())
                .build();
    }

}
