package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.ParamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Param} and its DTO {@link ParamDTO}.
 */
@Mapper(componentModel = "spring", uses = {ConverterMapper.class})
public interface ParamMapper extends EntityMapper<ParamDTO, Param> {


    @Mapping(target = "removeConverter", ignore = true)
    @Mapping(target = "rules", ignore = true)
    @Mapping(target = "removeRule", ignore = true)
    Param toEntity(ParamDTO paramDTO);

    default Param fromId(Long id) {
        if (id == null) {
            return null;
        }
        Param param = new Param();
        param.setId(id);
        return param;
    }
}
