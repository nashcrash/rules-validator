package it.sitissimo.validation.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the Validation service.
 */
@Data
public class RvValidationResultDTO implements Serializable {
    private boolean valid = true;
    private List<RvValidationResultDetailDTO> details = new ArrayList<>();

    public void addRvValidationResultDetailDTO(RvValidationResultDetailDTO detailDTO) {
        details.add(detailDTO);
    }
}
