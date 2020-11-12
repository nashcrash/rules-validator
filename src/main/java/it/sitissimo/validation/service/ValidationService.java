package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.RvOperatorDTO;
import it.sitissimo.validation.service.dto.RvValidationRequestDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for validation.
 */
public interface ValidationService {

    RvValidationResultDTO validate(RvValidationRequestDTO validationRequestDTO);
}
