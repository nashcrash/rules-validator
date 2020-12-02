package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(GenericOperator.Names.ifElseOperator)
public class IfElseOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);

        RvRuleDTO _if = (RvRuleDTO) params[0];
        RvRuleDTO _then = (RvRuleDTO) params[1];
        RvRuleDTO _else = null;
        if (params.length > 2) {
            _else = (RvRuleDTO) params[2];
        }

        List<RvValidationResultDetailDTO> detailDTOS = applyRuleBR.applyRule(_if, jsonNode);
        if (CollectionUtils.isEmpty(detailDTOS)) {
            return applyRuleBR.applyRule(_then, jsonNode);
        } else if (_else != null) {
            return applyRuleBR.applyRule(_else, jsonNode);
        }

        return makeValidResponse();
    }
}
