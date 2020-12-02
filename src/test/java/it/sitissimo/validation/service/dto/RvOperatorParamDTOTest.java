package it.sitissimo.validation.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import it.sitissimo.validation.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class RvOperatorParamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvOperatorParamDTO.class);
        RvOperatorParamDTO rvOperatorParamDTO1 = new RvOperatorParamDTO();
        rvOperatorParamDTO1.setId(1L);
        RvOperatorParamDTO rvOperatorParamDTO2 = new RvOperatorParamDTO();
        assertThat(rvOperatorParamDTO1).isNotEqualTo(rvOperatorParamDTO2);
        rvOperatorParamDTO2.setId(rvOperatorParamDTO1.getId());
        assertThat(rvOperatorParamDTO1).isEqualTo(rvOperatorParamDTO2);
        rvOperatorParamDTO2.setId(2L);
        assertThat(rvOperatorParamDTO1).isNotEqualTo(rvOperatorParamDTO2);
        rvOperatorParamDTO1.setId(null);
        assertThat(rvOperatorParamDTO1).isNotEqualTo(rvOperatorParamDTO2);
    }
}
