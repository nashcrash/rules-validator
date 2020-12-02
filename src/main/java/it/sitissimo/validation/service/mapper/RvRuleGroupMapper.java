package it.sitissimo.validation.service.mapper;

import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RvRuleGroupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RvRuleGroup} and its DTO {@link RvRuleGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RvRuleGroupMapper extends EntityMapper<RvRuleGroupDTO, RvRuleGroup> {
    default RvRuleGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        RvRuleGroup rvRuleGroup = new RvRuleGroup();
        rvRuleGroup.setId(id);
        return rvRuleGroup;
    }
}
