package it.sitissimo.validation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.sitissimo.validation.web.rest.TestUtil;

public class RvOperatorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvOperatorDTO.class);
        RvOperatorDTO rvOperatorDTO1 = new RvOperatorDTO();
        rvOperatorDTO1.setId(1L);
        RvOperatorDTO rvOperatorDTO2 = new RvOperatorDTO();
        assertThat(rvOperatorDTO1).isNotEqualTo(rvOperatorDTO2);
        rvOperatorDTO2.setId(rvOperatorDTO1.getId());
        assertThat(rvOperatorDTO1).isEqualTo(rvOperatorDTO2);
        rvOperatorDTO2.setId(2L);
        assertThat(rvOperatorDTO1).isNotEqualTo(rvOperatorDTO2);
        rvOperatorDTO1.setId(null);
        assertThat(rvOperatorDTO1).isNotEqualTo(rvOperatorDTO2);
    }
}
