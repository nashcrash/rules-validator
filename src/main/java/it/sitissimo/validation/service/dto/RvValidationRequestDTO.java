package it.sitissimo.validation.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the Validation service.
 */
@Data
public class RvValidationRequestDTO implements Serializable {
    @NotNull
    private String ruleCode;
    @NotNull
    private String model;
}
