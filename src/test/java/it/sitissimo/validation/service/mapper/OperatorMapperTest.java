package it.sitissimo.validation.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperatorMapperTest {
    private OperatorMapper operatorMapper;

    @BeforeEach
    public void setUp() {
        operatorMapper = new OperatorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(operatorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(operatorMapper.fromId(null)).isNull();
    }
}
