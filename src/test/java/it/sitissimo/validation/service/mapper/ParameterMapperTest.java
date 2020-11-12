package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ParameterMapperTest {

    private ParameterMapper parameterMapper;

    @BeforeEach
    public void setUp() {
        parameterMapper = new ParameterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(parameterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(parameterMapper.fromId(null)).isNull();
    }
}
