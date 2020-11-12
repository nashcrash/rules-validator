package it.sitissimo.validation.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.Param} entity.
 */
public class ParamDTO implements Serializable {
    
    private Long id;

    private String value;

    private Set<ConverterDTO> converters = new HashSet<>();
    
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

    public Set<ConverterDTO> getConverters() {
        return converters;
    }

    public void setConverters(Set<ConverterDTO> converters) {
        this.converters = converters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParamDTO)) {
            return false;
        }

        return id != null && id.equals(((ParamDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParamDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", converters='" + getConverters() + "'" +
            "}";
    }
}
