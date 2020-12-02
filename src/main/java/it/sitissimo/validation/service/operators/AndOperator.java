package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component(GenericOperator.Names.andOperator)
public class AndOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);
        List<RvValidationResultDetailDTO> results = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            List<RvValidationResultDetailDTO> detailDTOS = applyRuleBR.applyRule((RvRuleDTO) params[i], jsonNode);
            results.addAll(detailDTOS);
            if (singleMode(ruleDTO) && !CollectionUtils.isEmpty(detailDTOS)) {
                return detailDTOS;
            }
        }
        return results;
    }
}
