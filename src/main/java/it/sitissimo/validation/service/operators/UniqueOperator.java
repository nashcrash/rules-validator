package it.sitissimo.validation.service.operators;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component(GenericOperator.Names.uniqueOperator)
public class UniqueOperator extends AbstractOperator implements GenericOperator {

    @Override
    public List<RvValidationResultDetailDTO> validate(Object[] params, JsonNode jsonNode, RvRuleDTO ruleDTO) throws ValidationException {
        checkParams(params, 2);

        String duplicatedKey = null;
        Collection<? extends JsonNode> collection = (Collection<? extends JsonNode>) params[0];
        String[] keys = ((String) params[1]).split(",");

        Collection<String> keyMap = new ArrayList<>();
        for (Iterator<? extends JsonNode> iterator = collection.iterator(); iterator.hasNext() && duplicatedKey == null; ) {
            JsonNode model = iterator.next();
            String key = extractKey(model, keys);
            if (keyMap.contains(key)) {
                duplicatedKey = key;
            } else {
                keyMap.add(key);
            }
        }

        if (duplicatedKey == null) {
            return makeValidResponse();
        } else {
            List<Object> paramsNew = new ArrayList<>();
            paramsNew.add(duplicatedKey);
            paramsNew.addAll(Arrays.asList(params));
            return makeRvValidationResultDetailDTO(GenericOperator.Names.uniqueOperator, paramsNew.toArray(), jsonNode, ruleDTO);
        }
    }

    private String extractKey(JsonNode model, String[] keys) {
        StringBuilder sb = new StringBuilder("");
        for (String key : keys) {
            sb.append(model.get(key)).append(";");
        }
        return sb.toString();
    }
}
