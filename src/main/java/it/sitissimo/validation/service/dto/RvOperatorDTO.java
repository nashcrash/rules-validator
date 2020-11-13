package it.sitissimo.validation.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvOperator} entity.
 */
@Data
public class RvOperatorDTO implements Serializable {
    private Long id;
    @NotNull
    private String operatorCode;
    private String description;
    @NotNull
    private String beanName;
    private Integer numberOfParams;

}
