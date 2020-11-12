package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ParamMapperTest {

    private ParamMapper paramMapper;

    @BeforeEach
    public void setUp() {
        paramMapper = new ParamMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paramMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paramMapper.fromId(null)).isNull();
    }
}
