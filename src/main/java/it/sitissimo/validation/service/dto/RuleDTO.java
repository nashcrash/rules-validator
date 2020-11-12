package it.sitissimo.validation.service.dto;

import it.sitissimo.validation.domain.enumeration.RuleMode;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.Rule} entity.
 */
public class RuleDTO implements Serializable {
    private Long id;

    @NotNull
    private String ruleCode;

    private String description;

    @NotNull
    private RuleMode mode;

    private Long operatorId;
    private Set<ParameterDTO> parameters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RuleMode getMode() {
        return mode;
    }

    public void setMode(RuleMode mode) {
        this.mode = mode;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Set<ParameterDTO> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParameterDTO> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RuleDTO)) {
            return false;
        }

        return id != null && id.equals(((RuleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RuleDTO{" +
            "id=" + getId() +
            ", ruleCode='" + getRuleCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", mode='" + getMode() + "'" +
            ", operatorId=" + getOperatorId() +
            ", parameters='" + getParameters() + "'" +
            "}";
    }
}