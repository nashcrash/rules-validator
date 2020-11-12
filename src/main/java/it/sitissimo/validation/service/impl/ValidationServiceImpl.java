package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.domain.RvOperator;
import it.sitissimo.validation.repository.RvOperatorRepository;
import it.sitissimo.validation.service.RvOperatorService;
import it.sitissimo.validation.service.ValidationService;
import it.sitissimo.validation.service.dto.RvOperatorDTO;
import it.sitissimo.validation.service.dto.RvValidationRequestDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDTO;
import it.sitissimo.validation.service.mapper.RvOperatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RvOperator}.
 */
@Service
@Transactional
public class ValidationServiceImpl implements ValidationService {

    private final Logger log = LoggerFactory.getLogger(ValidationServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public RvValidationResultDTO validate(RvValidationRequestDTO validationRequestDTO) {
        return null;
    }
}
