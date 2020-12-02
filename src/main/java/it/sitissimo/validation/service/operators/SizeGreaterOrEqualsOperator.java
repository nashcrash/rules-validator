package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component(GenericOperator.Names.sizeGeOperator)
public class SizeGreaterOrEqualsOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);
        boolean valid = false;

        int number = Integer.parseInt(String.valueOf(params[1]));
        if (params[0] instanceof String) {
            valid = ((String) params[0]).length() >= number;
        } else if (params[0] instanceof Collection<?>) {
            valid = ((Collection<?>) params[0]).size() >= number;
        }
        if (valid) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(Names.sizeGeOperator, params, jsonNode, ruleDTO);
        }
    }
}
