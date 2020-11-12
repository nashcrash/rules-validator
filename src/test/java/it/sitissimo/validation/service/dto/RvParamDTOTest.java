package it.sitissimo.validation.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.sitissimo.validation.web.rest.TestUtil;

public class RvParamDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RvParamDTO.class);
        RvParamDTO rvParamDTO1 = new RvParamDTO();
        rvParamDTO1.setId(1L);
        RvParamDTO rvParamDTO2 = new RvParamDTO();
        assertThat(rvParamDTO1).isNotEqualTo(rvParamDTO2);
        rvParamDTO2.setId(rvParamDTO1.getId());
        assertThat(rvParamDTO1).isEqualTo(rvParamDTO2);
        rvParamDTO2.setId(2L);
        assertThat(rvParamDTO1).isNotEqualTo(rvParamDTO2);
        rvParamDTO1.setId(null);
        assertThat(rvParamDTO1).isNotEqualTo(rvParamDTO2);
    }
}
