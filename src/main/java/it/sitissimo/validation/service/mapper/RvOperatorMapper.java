package it.sitissimo.validation.service.mapper;


import it.sitissimo.validation.domain.*;
import it.sitissimo.validation.service.dto.RvOperatorDTO;

import org.mapstruct.*;

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
}
