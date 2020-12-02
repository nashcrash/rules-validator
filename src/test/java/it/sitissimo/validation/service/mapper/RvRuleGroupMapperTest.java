package it.sitissimo.validation.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RvRuleGroupMapperTest {

    private RvRuleGroupMapper rvRuleGroupMapper;

    @BeforeEach
    public void setUp() {
        rvRuleGroupMapper = new RvRuleGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rvRuleGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rvRuleGroupMapper.fromId(null)).isNull();
    }
}
