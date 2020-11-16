package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component(GenericOperator.Names.forEachOperator)
public class ForEachOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);

        Collection<? extends JsonNode> collection = (Collection<? extends JsonNode>) params[0];
        RvRuleDTO rvRuleDTO = (RvRuleDTO) params[1];

        List<RvValidationResultDetailDTO> result = new ArrayList<>();
        for (JsonNode model : collection) {
            List<RvValidationResultDetailDTO> detailDTOS = applyRuleBR.applyRule(ruleDTO, model);
            result.addAll(detailDTOS);
        }

        return result;
    }
}
