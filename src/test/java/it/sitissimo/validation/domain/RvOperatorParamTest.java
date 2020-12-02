package it.sitissimo.validation.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.sitissimo.validation.web.rest.TestUtil;

public class RvOperatorParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvOperatorParam.class);
        RvOperatorParam rvOperatorParam1 = new RvOperatorParam();
        rvOperatorParam1.setId(1L);
        RvOperatorParam rvOperatorParam2 = new RvOperatorParam();
        rvOperatorParam2.setId(rvOperatorParam1.getId());
        assertThat(rvOperatorParam1).isEqualTo(rvOperatorParam2);
        rvOperatorParam2.setId(2L);
        assertThat(rvOperatorParam1).isNotEqualTo(rvOperatorParam2);
        rvOperatorParam1.setId(null);
        assertThat(rvOperatorParam1).isNotEqualTo(rvOperatorParam2);
    }
}
