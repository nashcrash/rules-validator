package it.sitissimo.validation.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sitissimo.validation.br.ApplyRuleBR;
import it.sitissimo.validation.domain.RvOperator;
import it.sitissimo.validation.service.RvRuleService;
import it.sitissimo.validation.service.ValidationService;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationRequestDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.web.rest.errors.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Service Implementation for managing {@link RvOperator}.
 */
@Service
@Transactional
public class ValidationServiceImpl implements ValidationService {

    private final Logger log = LoggerFactory.getLogger(ValidationServiceImpl.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RvRuleService rvRuleService;

    @Autowired
    private ApplyRuleBR applyRuleBR;

    @Override
    @Transactional(readOnly = true)
    public RvValidationResultDTO validate(RvValidationRequestDTO validationRequestDTO) throws ValidationException {
        RvValidationResultDTO resultDTO = new RvValidationResultDTO();
        try {
            JsonNode model = objectMapper.readTree(validationRequestDTO.getModelJson());
            RvRuleDTO ruleDTO = rvRuleService.findOne(validationRequestDTO.getRuleCode()).orElse(null);
            if (ruleDTO == null) {
                throw new ValidationException("Rule not found: " + validationRequestDTO.getRuleCode());
            }
            List<RvValidationResultDetailDTO> detailDTOS = applyRuleBR.applyRule(ruleDTO, model);
            if (!CollectionUtils.isEmpty(detailDTOS)) {
                resultDTO.setValid(false);
                resultDTO.getDetails().addAll(detailDTOS);
            }
        } catch (JsonProcessingException e) {
            throw new ValidationException("Error during model processing", e);
        }
        return resultDTO;
    }


}
