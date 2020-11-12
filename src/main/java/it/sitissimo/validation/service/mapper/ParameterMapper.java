package it.sitissimo.validation.service.mapper;

import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.ParameterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parameter} and its DTO {@link ParameterDTO}.
 */
@Mapper(componentModel = "spring", uses = { ConverterMapper.class })
public interface ParameterMapper extends EntityMapper<ParameterDTO, Parameter> {
    @Mapping(target = "removeConverter", ignore = true)
    @Mapping(target = "rules", ignore = true)
    @Mapping(target = "removeRule", ignore = true)
    Parameter toEntity(ParameterDTO parameterDTO);

    default Parameter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parameter parameter = new Parameter();
        parameter.setId(id);
        return parameter;
    }
}
