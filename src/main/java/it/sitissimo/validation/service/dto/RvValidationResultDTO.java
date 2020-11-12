package it.sitissimo.validation.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the Validation service.
 */
@Data
public class RvValidationResultDTO implements Serializable {
    private boolean valid;
    private List<RvValidationResultDetailDTO> details;

}
