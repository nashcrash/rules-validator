package it.sitissimo.validation.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.sitissimo.validation.web.rest.TestUtil;

public class RvRuleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvRule.class);
        RvRule rvRule1 = new RvRule();
        rvRule1.setId(1L);
        RvRule rvRule2 = new RvRule();
        rvRule2.setId(rvRule1.getId());
        assertThat(rvRule1).isEqualTo(rvRule2);
        rvRule2.setId(2L);
        assertThat(rvRule1).isNotEqualTo(rvRule2);
        rvRule1.setId(null);
        assertThat(rvRule1).isNotEqualTo(rvRule2);
    }
}
