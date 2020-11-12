package it.sitissimo.validation.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.sitissimo.validation.web.rest.TestUtil;

public class RvConverterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvConverter.class);
        RvConverter rvConverter1 = new RvConverter();
        rvConverter1.setId(1L);
        RvConverter rvConverter2 = new RvConverter();
        rvConverter2.setId(rvConverter1.getId());
        assertThat(rvConverter1).isEqualTo(rvConverter2);
        rvConverter2.setId(2L);
        assertThat(rvConverter1).isNotEqualTo(rvConverter2);
        rvConverter1.setId(null);
        assertThat(rvConverter1).isNotEqualTo(rvConverter2);
    }
}
