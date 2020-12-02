package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RvOperatorParamMapperTest {

    private RvOperatorParamMapper rvOperatorParamMapper;

    @BeforeEach
    public void setUp() {
        rvOperatorParamMapper = new RvOperatorParamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rvOperatorParamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rvOperatorParamMapper.fromId(null)).isNull();
    }
}
