package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component(GenericOperator.Names.sizeLeOperator)
public class SizeLesserOrEqualsOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);
        boolean valid = false;

        Number number = (Number) params[1];
        if (params[0] instanceof String) {
            valid = ((String) params[0]).length() <= number.intValue();
        } else if (params[0] instanceof Collection<?>) {
            valid = ((Collection<?>) params[0]).size() <= number.intValue();
        }
        if (valid) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(Names.sizeLeOperator, params, jsonNode, ruleDTO);
        }
    }
}
