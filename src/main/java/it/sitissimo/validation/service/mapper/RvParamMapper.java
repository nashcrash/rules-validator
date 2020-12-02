package it.sitissimo.validation.service.mapper;

import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RvParamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RvParam} and its DTO {@link RvParamDTO}.
 */
@Mapper(componentModel = "spring", uses = { RvConverterMapper.class })
public interface RvParamMapper extends EntityMapper<RvParamDTO, RvParam> {
    @Mapping(target = "removeRvConverter", ignore = true)
    @Mapping(target = "rvRules", ignore = true)
    @Mapping(target = "removeRvRule", ignore = true)
    RvParam toEntity(RvParamDTO rvParamDTO);

    default RvParam fromId(Long id) {
        if (id == null) {
            return null;
        }
        RvParam rvParam = new RvParam();
        rvParam.setId(id);
        return rvParam;
    }

    @Mappings({ @Mapping(source = "id", target = "id", ignore = true) })
    RvParamDTO clone(RvParamDTO dto);
}
