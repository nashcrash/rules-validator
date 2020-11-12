package it.sitissimo.validation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.sitissimo.validation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ConverterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Converter.class);
        Converter converter1 = new Converter();
        converter1.setId(1L);
        Converter converter2 = new Converter();
        converter2.setId(converter1.getId());
        assertThat(converter1).isEqualTo(converter2);
        converter2.setId(2L);
        assertThat(converter1).isNotEqualTo(converter2);
        converter1.setId(null);
        assertThat(converter1).isNotEqualTo(converter2);
    }
}
