package com.caiotfernandes.assembleiaCoop.resources.exceptions;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 2012534955841439242L;
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
