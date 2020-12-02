package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RvRuleMapperTest {

    private RvRuleMapper rvRuleMapper;

    @BeforeEach
    public void setUp() {
        rvRuleMapper = new RvRuleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rvRuleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rvRuleMapper.fromId(null)).isNull();
    }
}
