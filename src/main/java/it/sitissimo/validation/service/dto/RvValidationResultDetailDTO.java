package it.sitissimo.validation.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the Validation service.
 */
@Data
public class RvValidationResultDetailDTO implements Serializable {
    private String field;
    private String rules;
    private String level;
    private String description;
    private String[] attributes;
}
