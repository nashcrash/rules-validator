package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RvOperatorMapperTest {

    private RvOperatorMapper rvOperatorMapper;

    @BeforeEach
    public void setUp() {
        rvOperatorMapper = new RvOperatorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rvOperatorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rvOperatorMapper.fromId(null)).isNull();
    }
}
