package it.sitissimo.validation.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterMapperTest {
    private ConverterMapper converterMapper;

    @BeforeEach
    public void setUp() {
        converterMapper = new ConverterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(converterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(converterMapper.fromId(null)).isNull();
    }
}
