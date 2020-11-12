package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.RvRule;
import it.sitissimo.validation.repository.RvRuleRepository;
import it.sitissimo.validation.service.RvRuleService;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.mapper.RvRuleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
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
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.sitissimo.validation.domain.enumeration.RvRuleMode;
/**
 * Integration tests for the {@link RvRuleResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RvRuleResourceIT {

    private static final String DEFAULT_RULE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final RvRuleMode DEFAULT_MODE = RvRuleMode.FIRST_ERROR;
    private static final RvRuleMode UPDATED_MODE = RvRuleMode.ALL_VALIDATION;

    @Autowired
    private RvRuleRepository rvRuleRepository;

    @Mock
    private RvRuleRepository rvRuleRepositoryMock;

    @Autowired
    private RvRuleMapper rvRuleMapper;

    @Mock
    private RvRuleService rvRuleServiceMock;

    @Autowired
    private RvRuleService rvRuleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRvRuleMockMvc;

    private RvRule rvRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvRule createEntity(EntityManager em) {
        RvRule rvRule = new RvRule()
            .ruleCode(DEFAULT_RULE_CODE)
            .description(DEFAULT_DESCRIPTION)
            .mode(DEFAULT_MODE);
        return rvRule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvRule createUpdatedEntity(EntityManager em) {
        RvRule rvRule = new RvRule()
            .ruleCode(UPDATED_RULE_CODE)
            .description(UPDATED_DESCRIPTION)
            .mode(UPDATED_MODE);
        return rvRule;
    }

    @BeforeEach
    public void initTest() {
        rvRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRvRule() throws Exception {
        int databaseSizeBeforeCreate = rvRuleRepository.findAll().size();
        // Create the RvRule
        RvRuleDTO rvRuleDTO = rvRuleMapper.toDto(rvRule);
        restRvRuleMockMvc.perform(post("/api/rv-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the RvRule in the database
        List<RvRule> rvRuleList = rvRuleRepository.findAll();
        assertThat(rvRuleList).hasSize(databaseSizeBeforeCreate + 1);
        RvRule testRvRule = rvRuleList.get(rvRuleList.size() - 1);
        assertThat(testRvRule.getRuleCode()).isEqualTo(DEFAULT_RULE_CODE);
        assertThat(testRvRule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRvRule.getMode()).isEqualTo(DEFAULT_MODE);
    }

    @Test
    @Transactional
    public void createRvRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rvRuleRepository.findAll().size();

        // Create the RvRule with an existing ID
        rvRule.setId(1L);
        RvRuleDTO rvRuleDTO = rvRuleMapper.toDto(rvRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvRuleMockMvc.perform(post("/api/rv-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvRule in the database
        List<RvRule> rvRuleList = rvRuleRepository.findAll();
        assertThat(rvRuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRuleCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvRuleRepository.findAll().size();
        // set the field null
        rvRule.setRuleCode(null);

        // Create the RvRule, which fails.
        RvRuleDTO rvRuleDTO = rvRuleMapper.toDto(rvRule);


        restRvRuleMockMvc.perform(post("/api/rv-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleDTO)))
            .andExpect(status().isBadRequest());

        List<RvRule> rvRuleList = rvRuleRepository.findAll();
        assertThat(rvRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvRuleRepository.findAll().size();
        // set the field null
        rvRule.setMode(null);

        // Create the RvRule, which fails.
        RvRuleDTO rvRuleDTO = rvRuleMapper.toDto(rvRule);


        restRvRuleMockMvc.perform(post("/api/rv-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleDTO)))
            .andExpect(status().isBadRequest());

        List<RvRule> rvRuleList = rvRuleRepository.findAll();
        assertThat(rvRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRvRules() throws Exception {
        // Initialize the database
        rvRuleRepository.saveAndFlush(rvRule);

        // Get all the rvRuleList
        restRvRuleMockMvc.perform(get("/api/rv-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleCode").value(hasItem(DEFAULT_RULE_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRvRulesWithEagerRelationshipsIsEnabled() throws Exception {
        when(rvRuleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRvRuleMockMvc.perform(get("/api/rv-rules?eagerload=true"))
            .andExpect(status().isOk());

        verify(rvRuleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRvRulesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(rvRuleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRvRuleMockMvc.perform(get("/api/rv-rules?eagerload=true"))
            .andExpect(status().isOk());

        verify(rvRuleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRvRule() throws Exception {
        // Initialize the database
        rvRuleRepository.saveAndFlush(rvRule);

        // Get the rvRule
        restRvRuleMockMvc.perform(get("/api/rv-rules/{id}", rvRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvRule.getId().intValue()))
            .andExpect(jsonPath("$.ruleCode").value(DEFAULT_RULE_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRvRule() throws Exception {
        // Get the rvRule
        restRvRuleMockMvc.perform(get("/api/rv-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRvRule() throws Exception {
        // Initialize the database
        rvRuleRepository.saveAndFlush(rvRule);

        int databaseSizeBeforeUpdate = rvRuleRepository.findAll().size();

        // Update the rvRule
        RvRule updatedRvRule = rvRuleRepository.findById(rvRule.getId()).get();
        // Disconnect from session so that the updates on updatedRvRule are not directly saved in db
        em.detach(updatedRvRule);
        updatedRvRule
            .ruleCode(UPDATED_RULE_CODE)
            .description(UPDATED_DESCRIPTION)
            .mode(UPDATED_MODE);
        RvRuleDTO rvRuleDTO = rvRuleMapper.toDto(updatedRvRule);

        restRvRuleMockMvc.perform(put("/api/rv-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleDTO)))
            .andExpect(status().isOk());

        // Validate the RvRule in the database
        List<RvRule> rvRuleList = rvRuleRepository.findAll();
        assertThat(rvRuleList).hasSize(databaseSizeBeforeUpdate);
        RvRule testRvRule = rvRuleList.get(rvRuleList.size() - 1);
        assertThat(testRvRule.getRuleCode()).isEqualTo(UPDATED_RULE_CODE);
        assertThat(testRvRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRvRule.getMode()).isEqualTo(UPDATED_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRvRule() throws Exception {
        int databaseSizeBeforeUpdate = rvRuleRepository.findAll().size();

        // Create the RvRule
        RvRuleDTO rvRuleDTO = rvRuleMapper.toDto(rvRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvRuleMockMvc.perform(put("/api/rv-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvRule in the database
        List<RvRule> rvRuleList = rvRuleRepository.findAll();
        assertThat(rvRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRvRule() throws Exception {
        // Initialize the database
        rvRuleRepository.saveAndFlush(rvRule);

        int databaseSizeBeforeDelete = rvRuleRepository.findAll().size();

        // Delete the rvRule
        restRvRuleMockMvc.perform(delete("/api/rv-rules/{id}", rvRule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RvRule> rvRuleList = rvRuleRepository.findAll();
        assertThat(rvRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
