package it.sitissimo.validation.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvRuleGroup} entity.
 */
public class RvRuleGroupDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String ruleGroupName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleGroupName() {
        return ruleGroupName;
    }

    public void setRuleGroupName(String ruleGroupName) {
        this.ruleGroupName = ruleGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvRuleGroupDTO)) {
            return false;
        }

        return id != null && id.equals(((RvRuleGroupDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvRuleGroupDTO{" +
            "id=" + getId() +
            ", ruleGroupName='" + getRuleGroupName() + "'" +
            "}";
    }
}
