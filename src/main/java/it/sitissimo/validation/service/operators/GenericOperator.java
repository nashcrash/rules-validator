package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.web.rest.errors.ValidationException;

import java.util.List;

public interface GenericOperator {
    List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException;

    interface Names {
        String equalsOperator = "equalsOperator";
        String notEqualsOperator = "notEqualsOperator";
        String greaterThanOperator = "greaterThanOperator";
        String greaterOrEqualsOperator = "greaterOrEqualsOperator";
        String lesserThanOperator = "lesserThanOperator";
        String lesserOrEqualsOperator = "lesserOrEqualsOperator";
        String isNullOperator = "isNullOperator";
        String isNotNullOperator = "isNotNullOperator";
        String regexOperator = "regexOperator";
        String existsOperator = "existsOperator";
        String notExistsOperator = "notExistsOperator";
        String forEachOperator = "forEachOperator";
        String existsEqualsOperator = "existsEqualsOperator";
        String existsLesserThanOperator = "existsLesserThanOperator";
        String existsGreaterThanOperator = "existsGreaterThanOperator";
        String uniqueOperator = "uniqueOperator";
        String ifElseOperator = "ifElseOperator";
        String andOperator = "andOperator";
        String orOperator = "orOperator";
        String notOperator = "notOperator";
        String trueOperator = "trueOperator";
        String falseOperator = "falseOperator";
    }
}
