package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.RvConverter;
import it.sitissimo.validation.service.dto.RvConverterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

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

    @Mappings({
        @Mapping(source = "id", target = "id", ignore = true),
    })
    RvConverterDTO clone(RvConverterDTO dto);
}
