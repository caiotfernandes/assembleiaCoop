package com.caiotfernandes.assembleiaCoop.domain.dtos;

import com.caiotfernandes.assembleiaCoop.domain.entities.Associado;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssociadoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2711787499539418035L;

    private Long id;
    @NotNull(message = "name deve ser informado")
    private String name;

    public static AssociadoDTO fromAssociado(Associado associado) {
        return builder()
                .id(associado.getId())
                .name(associado.getName())
                .build();
    }
}
