package it.sitissimo.validation.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvOperator} entity.
 */
public class RvOperatorDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String operatorCode;

    private String description;

    @NotNull
    private String beanName;

    private Integer numberOfParams;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
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

    public Integer getNumberOfParams() {
        return numberOfParams;
    }

    public void setNumberOfParams(Integer numberOfParams) {
        this.numberOfParams = numberOfParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvOperatorDTO)) {
            return false;
        }

        return id != null && id.equals(((RvOperatorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvOperatorDTO{" +
            "id=" + getId() +
            ", operatorCode='" + getOperatorCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            ", numberOfParams=" + getNumberOfParams() +
            "}";
    }
}
