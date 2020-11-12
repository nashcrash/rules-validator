package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rule} and its DTO {@link RuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {OperatorMapper.class, ParameterMapper.class})
public interface RuleMapper extends EntityMapper<RuleDTO, Rule> {

    @Mapping(source = "operator.id", target = "operatorId")
    RuleDTO toDto(Rule rule);

    @Mapping(source = "operatorId", target = "operator")
    @Mapping(target = "removeParameter", ignore = true)
    Rule toEntity(RuleDTO ruleDTO);

    default Rule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rule rule = new Rule();
        rule.setId(id);
        return rule;
    }
}
