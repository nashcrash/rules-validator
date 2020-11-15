package it.sitissimo.validation.br;

import com.fasterxml.jackson.databind.JsonNode;
import it.sitissimo.validation.service.converters.GenericConverter;
import it.sitissimo.validation.service.dto.RvConverterDTO;
import it.sitissimo.validation.service.dto.RvParamDTO;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDetailDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import it.sitissimo.validation.service.operators.GenericOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ApplyRuleBR {
    @Autowired(required = false)
    private Map<String, GenericOperator> operators;

    @Autowired(required = false)
    private Map<String, GenericConverter> converters;

    @Autowired
    private ExpressionLanguageBR expressionLanguageBR;

    public List<RvValidationResultDetailDTO> applyRule(RvRuleDTO rvRuleDTO, JsonNode model) throws ValidationException {
        List<RvValidationResultDetailDTO> resultDetailsDTO = new ArrayList<>();
        if (rvRuleDTO.getOperator() != null && StringUtils.isNotEmpty(rvRuleDTO.getOperator().getBeanName())) {
            GenericOperator operator = operators.get(rvRuleDTO.getOperator().getBeanName());
            if (operator != null) {
                resultDetailsDTO = operator.validate(evaluateParams(rvRuleDTO.getRvParams(), rvRuleDTO, model), model, rvRuleDTO);
            }
        }
        return resultDetailsDTO;
    }

    public Object[] evaluateParams(List<RvParamDTO> rvParams, RvRuleDTO ruleDTO, JsonNode model) throws ValidationException {
        List<Object> result=new ArrayList<>();
        if (rvParams!=null) {
            for(RvParamDTO rvParam: rvParams) {
                result.add(evaluateParam(rvParam, ruleDTO, model));
            }
        }
        return result.toArray();
    }

    public Object evaluateParam(RvParamDTO rvParam, RvRuleDTO ruleDTO, JsonNode model) throws ValidationException {
        Object evaluate = expressionLanguageBR.evaluate(rvParam.getValue(), ruleDTO, model);
        if (rvParam.getRvConverters()!=null) {
            for(RvConverterDTO rvConverterDTO: rvParam.getRvConverters()) {
                if (StringUtils.isNotEmpty(rvConverterDTO.getBeanName())) {
                    GenericConverter converter = converters.get(rvConverterDTO.getBeanName());
                    evaluate = converter.convert(evaluate);
                }
            }
        }
        return evaluate;
    }
}
