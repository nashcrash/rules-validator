package it.sitissimo.validation.service.dto;

import it.sitissimo.validation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RvRuleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvRuleDTO.class, true);
        RvRuleDTO rvRuleDTO1 = new RvRuleDTO();
        rvRuleDTO1.setId(1L);
        RvRuleDTO rvRuleDTO2 = new RvRuleDTO();
        assertThat(rvRuleDTO1).isNotEqualTo(rvRuleDTO2);
        rvRuleDTO2.setId(rvRuleDTO1.getId());
        assertThat(rvRuleDTO1).isEqualTo(rvRuleDTO2);
        rvRuleDTO2.setId(2L);
        assertThat(rvRuleDTO1).isNotEqualTo(rvRuleDTO2);
        rvRuleDTO1.setId(null);
        assertThat(rvRuleDTO1).isNotEqualTo(rvRuleDTO2);
    }
}
