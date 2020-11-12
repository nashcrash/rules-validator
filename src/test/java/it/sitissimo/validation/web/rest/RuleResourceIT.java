package it.sitissimo.validation.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.sitissimo.validation.ValidationRulesApp;
import it.sitissimo.validation.domain.Rule;
import it.sitissimo.validation.domain.enumeration.RuleMode;
import it.sitissimo.validation.repository.RuleRepository;
import it.sitissimo.validation.service.RuleService;
import it.sitissimo.validation.service.dto.RuleDTO;
import it.sitissimo.validation.service.mapper.RuleMapper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RuleResource} REST controller.
 */
@SpringBootTest(classes = ValidationRulesApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RuleResourceIT {
    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final RuleMode DEFAULT_MODE = RuleMode.FIRST_ERROR;
    private static final RuleMode UPDATED_MODE = RuleMode.ALL_VALIDATION;

    @Autowired
    private RuleRepository ruleRepository;

    @Mock
    private RuleRepository ruleRepositoryMock;

    @Autowired
    private RuleMapper ruleMapper;

    @Mock
    private RuleService ruleServiceMock;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleMockMvc;

    private Rule rule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createEntity(EntityManager em) {
        Rule rule = new Rule().ruleCode(DEFAULT_RULE_CODE).description(DEFAULT_DESCRIPTION).mode(DEFAULT_MODE);
        return rule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createUpdatedEntity(EntityManager em) {
        Rule rule = new Rule().ruleCode(UPDATED_RULE_CODE).description(UPDATED_DESCRIPTION).mode(UPDATED_MODE);
        return rule;
    }

    @BeforeEach
    public void initTest() {
        rule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRule() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();
        // Create the Rule
        RuleDTO ruleDTO = ruleMapper.toDto(rule);
        restRuleMockMvc
            .perform(post("/api/rules").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isCreated());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate + 1);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testRule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRule.getMode()).isEqualTo(DEFAULT_MODE);
    }

    @Test
    @Transactional
    public void createRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule with an existing ID
        rule.setId(1L);
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleMockMvc
            .perform(post("/api/rules").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRuleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setRuleCode(null);

        // Create the Rule, which fails.
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        restRuleMockMvc
            .perform(post("/api/rules").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ruleRepository.findAll().size();
        // set the field null
        rule.setMode(null);

        // Create the Rule, which fails.
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        restRuleMockMvc
            .perform(post("/api/rules").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRules() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList
        restRuleMockMvc
            .perform(get("/api/rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllRulesWithEagerRelationshipsIsEnabled() throws Exception {
        when(ruleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRuleMockMvc.perform(get("/api/rules?eagerload=true")).andExpect(status().isOk());

        verify(ruleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllRulesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ruleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRuleMockMvc.perform(get("/api/rules?eagerload=true")).andExpect(status().isOk());

        verify(ruleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get the rule
        restRuleMockMvc
            .perform(get("/api/rules/{id}", rule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rule.getId().intValue()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRule() throws Exception {
        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Update the rule
        Rule updatedRule = ruleRepository.findById(rule.getId()).get();
        // Disconnect from session so that the updates on updatedRule are not directly saved in db
        em.detach(updatedRule);
        updatedRule.ruleCode(UPDATED_RULE_CODE).description(UPDATED_DESCRIPTION).mode(UPDATED_MODE);
        RuleDTO ruleDTO = ruleMapper.toDto(updatedRule);

        restRuleMockMvc
            .perform(put("/api/rules").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isOk());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRule.getMode()).isEqualTo(UPDATED_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRule() throws Exception {
        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Create the Rule
        RuleDTO ruleDTO = ruleMapper.toDto(rule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleMockMvc
            .perform(put("/api/rules").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ruleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        int databaseSizeBeforeDelete = ruleRepository.findAll().size();

        // Delete the rule
        restRuleMockMvc
            .perform(delete("/api/rules/{id}", rule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
