package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(GenericOperator.Names.notEqualsOperator)
public class NotEqualsOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);

        Object ref1 = params[0];
        Object ref2 = params[1];

        if (!ref1.equals(ref2)) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(GenericOperator.Names.notEqualsOperator, params, jsonNode, ruleDTO);
        }
    }
}
