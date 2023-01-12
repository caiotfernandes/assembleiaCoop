package com.caiotfernandes.assembleiaCoop.domain.dtos;

import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class PautaDTO {

    private Long id;
    private String name;

    public static PautaDTO fromPauta(Pauta pauta) {
        return builder()
                .id(pauta.getId())
                .name(pauta.getName())
                .build();
    }
}
