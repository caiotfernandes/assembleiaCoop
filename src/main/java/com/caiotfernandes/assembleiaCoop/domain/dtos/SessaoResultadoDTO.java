package com.caiotfernandes.assembleiaCoop.domain.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SessaoResultadoDTO {

    private Long sessaoId;
    private String nomePauta;
    private String resultado;
    private Long quantidadeVotos;

}
