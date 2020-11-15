package it.sitissimo.validation.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvParam} entity.
 */
@Data
@EqualsAndHashCode
public class RvParamDTO implements Serializable {
    private Long id;
    private String value;
    private Set<RvConverterDTO> rvConverters = new HashSet<>();

}
