package it.sitissimo.validation.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
