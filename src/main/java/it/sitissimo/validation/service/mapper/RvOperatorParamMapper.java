package it.sitissimo.validation.service.mapper;

import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RvOperatorParamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RvOperatorParam} and its DTO {@link RvOperatorParamDTO}.
 */
@Mapper(componentModel = "spring", uses = { RvOperatorMapper.class })
public interface RvOperatorParamMapper extends EntityMapper<RvOperatorParamDTO, RvOperatorParam> {
    @Mapping(source = "operator.id", target = "operatorId")
    RvOperatorParamDTO toDto(RvOperatorParam rvOperatorParam);

    @Mapping(source = "operatorId", target = "operator")
    RvOperatorParam toEntity(RvOperatorParamDTO rvOperatorParamDTO);

    default RvOperatorParam fromId(Long id) {
        if (id == null) {
            return null;
        }
        RvOperatorParam rvOperatorParam = new RvOperatorParam();
        rvOperatorParam.setId(id);
        return rvOperatorParam;
    }
}
