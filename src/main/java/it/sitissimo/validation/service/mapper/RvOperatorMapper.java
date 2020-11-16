package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.RvOperator;
import it.sitissimo.validation.service.dto.RvOperatorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper for the entity {@link RvOperator} and its DTO {@link RvOperatorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RvOperatorMapper extends EntityMapper<RvOperatorDTO, RvOperator> {


    default RvOperator fromId(Long id) {
        if (id == null) {
            return null;
        }
        RvOperator rvOperator = new RvOperator();
        rvOperator.setId(id);
        return rvOperator;
    }

    @Mappings({
        @Mapping(source = "id", target = "id", ignore = true),
    })
    RvOperatorDTO clone(RvOperatorDTO dto);
}
