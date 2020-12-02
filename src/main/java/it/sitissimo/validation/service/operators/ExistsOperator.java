package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component(GenericOperator.Names.existsOperator)
public class ExistsOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);

        Collection<? extends JsonNode> collection = (Collection<? extends JsonNode>) params[0];
        RvRuleDTO rvRuleDTO = (RvRuleDTO) params[1];

        boolean valid = false;
        int found = checkRuleOnCollection(rvRuleDTO, collection, 1);
        if (found > 0) {
            valid = true;
        }

        if (valid) {
            return makeValidResponse();
        } else {
            return makeRvValidationResultDetailDTO(GenericOperator.Names.existsOperator, params, jsonNode, ruleDTO);
        }
    }

}
