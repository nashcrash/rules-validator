package it.sitissimo.validation.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.Converter} entity.
 */
public class ConverterDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String converterCode;

    private String description;

    @NotNull
    private String beanName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConverterCode() {
        return converterCode;
    }

    public void setConverterCode(String converterCode) {
        this.converterCode = converterCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConverterDTO)) {
            return false;
        }

        return id != null && id.equals(((ConverterDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConverterDTO{" +
            "id=" + getId() +
            ", converterCode='" + getConverterCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            "}";
    }
}
