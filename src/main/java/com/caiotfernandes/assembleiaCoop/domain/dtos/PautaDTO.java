package com.caiotfernandes.assembleiaCoop.domain.dtos;

import com.caiotfernandes.assembleiaCoop.domain.entities.Pauta;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaDTO {

    private Long id;
    @NotNull(message = "name deve ser informado")
    private String name;

    public static PautaDTO fromPauta(Pauta pauta) {
        return builder()
                .id(pauta.getId())
                .name(pauta.getName())
                .build();
    }
}
