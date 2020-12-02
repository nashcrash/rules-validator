package it.sitissimo.validation.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import it.sitissimo.validation.domain.enumeration.RvParamType;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvOperatorParam} entity.
 */
public class RvOperatorParamDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private RvParamType type;


    private Long operatorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RvParamType getType() {
        return type;
    }

    public void setType(RvParamType type) {
        this.type = type;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long rvOperatorId) {
        this.operatorId = rvOperatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvOperatorParamDTO)) {
            return false;
        }

        return id != null && id.equals(((RvOperatorParamDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvOperatorParamDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", operatorId=" + getOperatorId() +
            "}";
    }
}
