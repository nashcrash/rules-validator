package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.web.rest.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(GenericOperator.Names.trueOperator)
public class TrueOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        return makeValidResponse();
    }
}
