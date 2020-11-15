package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(GenericOperator.Names.lesserOrEqualsOperator)
public class LesserOrEqualsOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);

        if (((Comparable) params[0]).compareTo((Comparable) params[1]) <= 0) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(GenericOperator.Names.lesserOrEqualsOperator, params, jsonNode, ruleDTO);
        }
    }
}