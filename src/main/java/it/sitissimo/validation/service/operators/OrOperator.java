package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.br.ApplyRuleBR;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component(GenericOperator.Names.orOperator)
public class OrOperator extends AbstractOperator implements GenericOperator {
    @Autowired
    private ApplyRuleBR applyRuleBR;

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);

        List<RvValidationResultDetailDTO> results = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            List<RvValidationResultDetailDTO> detailDTOS = applyRuleBR.applyRule((RvRuleDTO) params[i], jsonNode);
            results.addAll(detailDTOS);
            if (CollectionUtils.isEmpty(detailDTOS)) {
                return detailDTOS;
            }
        }
        return results;
    }
}
