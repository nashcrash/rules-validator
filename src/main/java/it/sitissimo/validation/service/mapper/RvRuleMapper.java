package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.RvRule;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity {@link RvRule} and its DTO {@link RvRuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {RvOperatorMapper.class, RvParamMapper.class})
public interface RvRuleMapper extends EntityMapper<RvRuleDTO, RvRule> {

    @Mappings({
        @Mapping(source = "operator.id", target = "operator"),
        @Mapping(target = "removeRvParam", ignore = true),
    })
    RvRule toEntity(RvRuleDTO rvRuleDTO);

    default RvRule fromId(Long id) {
        if (id == null) {
            return null;
        }
        RvRule rvRule = new RvRule();
        rvRule.setId(id);
        return rvRule;
    }

    @Mappings({
        @Mapping(source = "id", target = "id", ignore = true),
    })
    RvRuleDTO clone(RvRuleDTO dto);
}
