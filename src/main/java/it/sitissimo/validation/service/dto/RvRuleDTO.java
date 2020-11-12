package it.sitissimo.validation.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import it.sitissimo.validation.domain.enumeration.RvRuleLevel;
import it.sitissimo.validation.domain.enumeration.RvRuleMode;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvRule} entity.
 */
public class RvRuleDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String ruleCode;

    private String description;

    @NotNull
    private RvRuleLevel level;

    @NotNull
    private RvRuleMode mode;


    private Long operatorId;
    private Set<RvParamDTO> rvParams = new HashSet<>();
    
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

    public RvRuleLevel getLevel() {
        return level;
    }

    public void setLevel(RvRuleLevel level) {
        this.level = level;
    }

    public RvRuleMode getMode() {
        return mode;
    }

    public void setMode(RvRuleMode mode) {
        this.mode = mode;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long rvOperatorId) {
        this.operatorId = rvOperatorId;
    }

    public Set<RvParamDTO> getRvParams() {
        return rvParams;
    }

    public void setRvParams(Set<RvParamDTO> rvParams) {
        this.rvParams = rvParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvRuleDTO)) {
            return false;
        }

        return id != null && id.equals(((RvRuleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvRuleDTO{" +
            "id=" + getId() +
            ", ruleCode='" + getRuleCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", level='" + getLevel() + "'" +
            ", mode='" + getMode() + "'" +
            ", operatorId=" + getOperatorId() +
            ", rvParams='" + getRvParams() + "'" +
            "}";
    }
}
