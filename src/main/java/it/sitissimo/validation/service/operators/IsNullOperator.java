package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(GenericOperator.Names.isNullOperator)
public class IsNullOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 1);

        Object ref1 = params[0];

        if (ref1 == null) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(GenericOperator.Names.isNullOperator, params, jsonNode, ruleDTO);
        }
    }
}
