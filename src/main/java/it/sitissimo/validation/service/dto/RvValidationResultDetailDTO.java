package it.sitissimo.validation.service.dto;

import it.sitissimo.validation.domain.enumeration.RvRuleLevel;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the Validation service.
 */
@Data
public class RvValidationResultDetailDTO implements Serializable {
    private String ruleCode;
    private RvRuleLevel level;
    private String operatorCode;
    private String description;
    private String message;
    private Object[] attributes;
}
