package it.sitissimo.validation.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.Operator} entity.
 */
public class OperatorDTO implements Serializable {
    private Long id;

    @NotNull
    private String operatorCode;

    private String description;

    @NotNull
    private String beanName;

    private Integer numberOfParameters;

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

    public Integer getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setNumberOfParameters(Integer numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperatorDTO)) {
            return false;
        }

        return id != null && id.equals(((OperatorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperatorDTO{" +
            "id=" + getId() +
            ", operatorCode='" + getOperatorCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            ", numberOfParameters=" + getNumberOfParameters() +
            "}";
    }
}
