package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.ConverterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Converter} and its DTO {@link ConverterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConverterMapper extends EntityMapper<ConverterDTO, Converter> {


    @Mapping(target = "params", ignore = true)
    @Mapping(target = "removeParam", ignore = true)
    Converter toEntity(ConverterDTO converterDTO);

    default Converter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Converter converter = new Converter();
        converter.setId(id);
        return converter;
    }
}
