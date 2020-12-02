package it.sitissimo.validation.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.sitissimo.validation.web.rest.TestUtil;

public class RvOperatorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvOperator.class);
        RvOperator rvOperator1 = new RvOperator();
        rvOperator1.setId(1L);
        RvOperator rvOperator2 = new RvOperator();
        rvOperator2.setId(rvOperator1.getId());
        assertThat(rvOperator1).isEqualTo(rvOperator2);
        rvOperator2.setId(2L);
        assertThat(rvOperator1).isNotEqualTo(rvOperator2);
        rvOperator1.setId(null);
        assertThat(rvOperator1).isNotEqualTo(rvOperator2);
    }
}
