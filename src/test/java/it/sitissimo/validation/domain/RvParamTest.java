package it.sitissimo.validation.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.sitissimo.validation.web.rest.TestUtil;

public class RvParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvParam.class);
        RvParam rvParam1 = new RvParam();
        rvParam1.setId(1L);
        RvParam rvParam2 = new RvParam();
        rvParam2.setId(rvParam1.getId());
        assertThat(rvParam1).isEqualTo(rvParam2);
        rvParam2.setId(2L);
        assertThat(rvParam1).isNotEqualTo(rvParam2);
        rvParam1.setId(null);
        assertThat(rvParam1).isNotEqualTo(rvParam2);
    }
}
