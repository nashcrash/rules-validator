package it.sitissimo.validation.service.dto;

import it.sitissimo.validation.domain.enumeration.RvRuleLevel;
import it.sitissimo.validation.domain.enumeration.RvRuleMode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link it.sitissimo.validation.domain.RvRule} entity.
 */
@Data
public class RvRuleDTO implements Serializable {
    private Long id;

    @NotNull
    private String ruleCode;

    private String description;

    @NotNull
    private RvRuleLevel level;

    @NotNull
    private RvRuleMode mode;

    private RvRuleGroupDTO group;
    private RvOperatorDTO operator;
    private List<RvParamDTO> rvParams = new ArrayList<>();
}
