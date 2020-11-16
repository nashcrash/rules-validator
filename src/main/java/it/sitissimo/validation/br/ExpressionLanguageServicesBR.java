package it.sitissimo.validation.br;

import it.sitissimo.validation.service.RvRuleService;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpressionLanguageServicesBR {
    @Autowired
    private RvRuleService rvRuleService;

    public RvRuleDTO rule(String ruleName) {
        return rvRuleService.findOne(ruleName).orElse(null);
    }
}
