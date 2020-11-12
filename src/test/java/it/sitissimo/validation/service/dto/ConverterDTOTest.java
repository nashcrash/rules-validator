package it.sitissimo.validation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.sitissimo.validation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ConverterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConverterDTO.class);
        ConverterDTO converterDTO1 = new ConverterDTO();
        converterDTO1.setId(1L);
        ConverterDTO converterDTO2 = new ConverterDTO();
        assertThat(converterDTO1).isNotEqualTo(converterDTO2);
        converterDTO2.setId(converterDTO1.getId());
        assertThat(converterDTO1).isEqualTo(converterDTO2);
        converterDTO2.setId(2L);
        assertThat(converterDTO1).isNotEqualTo(converterDTO2);
        converterDTO1.setId(null);
        assertThat(converterDTO1).isNotEqualTo(converterDTO2);
    }
}
