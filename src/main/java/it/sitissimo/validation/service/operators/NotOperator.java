package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.br.ApplyRuleBR;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.web.rest.errors.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component(GenericOperator.Names.notOperator)
public class NotOperator extends AbstractOperator implements GenericOperator {
    @Autowired
    private ApplyRuleBR applyRuleBR;

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 1);
        List<RvValidationResultDetailDTO> detailDTOS = applyRuleBR.applyRule((RvRuleDTO) params[0], jsonNode);
        if (!CollectionUtils.isEmpty(detailDTOS)) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(GenericOperator.Names.notOperator, params, jsonNode, ruleDTO);
        }
    }
}
