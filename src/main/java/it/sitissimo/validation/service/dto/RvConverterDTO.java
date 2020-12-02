package it.sitissimo.validation.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvConverter} entity.
 */
@Data
public class RvConverterDTO implements Serializable {
    private Long id;
    @NotNull
    private String converterCode;
    private String description;
    @NotNull
    private String beanName;
}
