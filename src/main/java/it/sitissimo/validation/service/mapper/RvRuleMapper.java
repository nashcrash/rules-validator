package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RvRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RvRule} and its DTO {@link RvRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {RvOperatorMapper.class, RvParamMapper.class})
public interface RvRuleMapper extends EntityMapper<RvRuleDTO, RvRule> {

    @Mapping(source = "operator.id", target = "operatorId")
    RvRuleDTO toDto(RvRule rvRule);

    @Mapping(source = "operatorId", target = "operator")
    @Mapping(target = "removeRvParam", ignore = true)
    RvRule toEntity(RvRuleDTO rvRuleDTO);

    default RvRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        RvRule rvRule = new RvRule();
        rvRule.setId(id);
        return rvRule;
    }
}
