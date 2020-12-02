package it.sitissimo.validation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.sitissimo.validation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class RvRuleGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvRuleGroupDTO.class);
        RvRuleGroupDTO rvRuleGroupDTO1 = new RvRuleGroupDTO();
        rvRuleGroupDTO1.setId(1L);
        RvRuleGroupDTO rvRuleGroupDTO2 = new RvRuleGroupDTO();
        assertThat(rvRuleGroupDTO1).isNotEqualTo(rvRuleGroupDTO2);
        rvRuleGroupDTO2.setId(rvRuleGroupDTO1.getId());
        assertThat(rvRuleGroupDTO1).isEqualTo(rvRuleGroupDTO2);
        rvRuleGroupDTO2.setId(2L);
        assertThat(rvRuleGroupDTO1).isNotEqualTo(rvRuleGroupDTO2);
        rvRuleGroupDTO1.setId(null);
        assertThat(rvRuleGroupDTO1).isNotEqualTo(rvRuleGroupDTO2);
    }
}
