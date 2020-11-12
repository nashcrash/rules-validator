package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RvConverterMapperTest {

    private RvConverterMapper rvConverterMapper;

    @BeforeEach
    public void setUp() {
        rvConverterMapper = new RvConverterMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rvConverterMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rvConverterMapper.fromId(null)).isNull();
    }
}
