package it.sitissimo.validation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.sitissimo.validation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class RvRuleGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvRuleGroup.class);
        RvRuleGroup rvRuleGroup1 = new RvRuleGroup();
        rvRuleGroup1.setId(1L);
        RvRuleGroup rvRuleGroup2 = new RvRuleGroup();
        rvRuleGroup2.setId(rvRuleGroup1.getId());
        assertThat(rvRuleGroup1).isEqualTo(rvRuleGroup2);
        rvRuleGroup2.setId(2L);
        assertThat(rvRuleGroup1).isNotEqualTo(rvRuleGroup2);
        rvRuleGroup1.setId(null);
        assertThat(rvRuleGroup1).isNotEqualTo(rvRuleGroup2);
    }
}
