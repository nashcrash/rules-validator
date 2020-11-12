package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RvConverterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RvConverter} and its DTO {@link RvConverterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RvConverterMapper extends EntityMapper<RvConverterDTO, RvConverter> {


    @Mapping(target = "rvParams", ignore = true)
    @Mapping(target = "removeRvParam", ignore = true)
    RvConverter toEntity(RvConverterDTO rvConverterDTO);

    default RvConverter fromId(Long id) {
        if (id == null) {
            return null;
        }
        RvConverter rvConverter = new RvConverter();
        rvConverter.setId(id);
        return rvConverter;
    }
}
