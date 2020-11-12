package it.sitissimo.validation.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvParam} entity.
 */
public class RvParamDTO implements Serializable {
    
    private Long id;

    private String value;

    private Set<RvConverterDTO> rvConverters = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<RvConverterDTO> getRvConverters() {
        return rvConverters;
    }

    public void setRvConverters(Set<RvConverterDTO> rvConverters) {
        this.rvConverters = rvConverters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvParamDTO)) {
            return false;
        }

        return id != null && id.equals(((RvParamDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvParamDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", rvConverters='" + getRvConverters() + "'" +
            "}";
    }
}
