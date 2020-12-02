package it.sitissimo.validation.service.mapper;

import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RvRule} and its DTO {@link RvRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = { RvRuleGroupMapper.class, RvOperatorMapper.class, RvParamMapper.class })
public interface RvRuleMapper extends EntityMapper<RvRuleDTO, RvRule> {
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "operator", target = "operator")
    RvRuleDTO toDto(RvRule rvRule);

    @Mapping(source = "groupId", target = "group")
    @Mapping(source = "operator.id", target = "operator")
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

    @Mappings({ @Mapping(source = "id", target = "id", ignore = true) })
    RvRuleDTO clone(RvRuleDTO dto);
}
