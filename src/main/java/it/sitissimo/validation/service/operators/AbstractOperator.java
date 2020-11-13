package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.br.ExpressionLanguageBR;
import it.sitissimo.validation.domain.enumeration.RvRuleMode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.web.rest.errors.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOperator implements GenericOperator {
    @Autowired
    private ExpressionLanguageBR expressionLanguageBR;

    protected void checkParams(Object[] params, int size) throws ValidationException {
        if (params == null || params.length < size) {
            throw new ValidationException("Invalid parameters number");
        }
    }

    protected List<RvValidationResultDetailDTO> makeValidResponse() {
        return new ArrayList<>();
    }

    protected List<RvValidationResultDetailDTO> makeRvValidationResultDetailDTO(String message, Object[] attributes, JsonNode model, RvRuleDTO ruleDTO) {
        return makeRvValidationResultDetailDTO(message, attributes, model, ruleDTO, null);
    }

    protected List<RvValidationResultDetailDTO> makeRvValidationResultDetailDTO(String message, Object[] attributes, JsonNode model, RvRuleDTO ruleDTO, List<RvValidationResultDetailDTO> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        RvValidationResultDetailDTO detailDTO = new RvValidationResultDetailDTO();
        detailDTO.setMessage(message);
        detailDTO.setAttributes(attributes);
        detailDTO.setLevel(ruleDTO.getLevel());
        detailDTO.setRuleCode(ruleDTO.getRuleCode());
        detailDTO.setDescription((String) expressionLanguageBR.evaluate(ruleDTO.getDescription(), ruleDTO, model));
        list.add(detailDTO);
        return list;
    }

    protected boolean singleMode(RvRuleDTO ruleDTO) {
        return RvRuleMode.FIRST_ERROR.equals(ruleDTO.getMode());
    }
}
