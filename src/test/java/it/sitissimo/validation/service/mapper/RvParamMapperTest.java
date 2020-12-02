package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RvParamMapperTest {

    private RvParamMapper rvParamMapper;

    @BeforeEach
    public void setUp() {
        rvParamMapper = new RvParamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rvParamMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rvParamMapper.fromId(null)).isNull();
    }
}
