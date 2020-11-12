package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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
