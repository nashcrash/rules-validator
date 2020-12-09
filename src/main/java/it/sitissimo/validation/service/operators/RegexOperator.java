package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(GenericOperator.Names.regexOperator)
public class RegexOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);
        String ref1 = String.valueOf(params[0]);
        String ref2 = String.valueOf(params[1]);

        if (ref1.matches(ref2)) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(GenericOperator.Names.regexOperator, params, jsonNode, ruleDTO);
        }
    }
}