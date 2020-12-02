package it.sitissimo.validation.service.dto;

import it.sitissimo.validation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RvConverterDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvConverterDTO.class, true);
        RvConverterDTO rvConverterDTO1 = new RvConverterDTO();
        rvConverterDTO1.setId(1L);
        RvConverterDTO rvConverterDTO2 = new RvConverterDTO();
        assertThat(rvConverterDTO1).isNotEqualTo(rvConverterDTO2);
        rvConverterDTO2.setId(rvConverterDTO1.getId());
        assertThat(rvConverterDTO1).isEqualTo(rvConverterDTO2);
        rvConverterDTO2.setId(2L);
        assertThat(rvConverterDTO1).isNotEqualTo(rvConverterDTO2);
        rvConverterDTO1.setId(null);
        assertThat(rvConverterDTO1).isNotEqualTo(rvConverterDTO2);
    }
}
