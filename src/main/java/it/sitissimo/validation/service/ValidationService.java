package it.sitissimo.validation.service;

import it.sitissimo.validation.domain.enumeration.EFormat;
import it.sitissimo.validation.service.dto.RvValidationRequestDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDTO;
import it.sitissimo.validation.service.errors.ValidationException;

/**
 * Service Interface for validation.
 */
public interface ValidationService {

    RvValidationResultDTO validate(EFormat format, RvValidationRequestDTO validationRequestDTO) throws ValidationException;
}
