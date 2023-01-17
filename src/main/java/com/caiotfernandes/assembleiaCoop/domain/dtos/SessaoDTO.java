package com.caiotfernandes.assembleiaCoop.domain.dtos;

import com.caiotfernandes.assembleiaCoop.domain.entities.Sessao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessaoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5436917786182246788L;

    @NotNull(message = "pautaId deve ser informado")
    private Long pautaId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date endDate;

    public static SessaoDTO fromSessao(Sessao sessao) {
        return builder()
                .pautaId(sessao.getPauta().getId())
                .endDate(sessao.getEndDate())
                .build();
    }
}
