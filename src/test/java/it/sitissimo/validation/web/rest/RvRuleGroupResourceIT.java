package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.RvRuleGroup;
import it.sitissimo.validation.repository.RvRuleGroupRepository;
import it.sitissimo.validation.service.RvRuleGroupService;
import it.sitissimo.validation.service.dto.RvRuleGroupDTO;
import it.sitissimo.validation.service.mapper.RvRuleGroupMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RvRuleGroupResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RvRuleGroupResourceIT {

    private static final String DEFAULT_RULE_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RULE_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private RvRuleGroupRepository rvRuleGroupRepository;

    @Autowired
    private RvRuleGroupMapper rvRuleGroupMapper;

    @Autowired
    private RvRuleGroupService rvRuleGroupService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRvRuleGroupMockMvc;

    private RvRuleGroup rvRuleGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvRuleGroup createEntity(EntityManager em) {
        RvRuleGroup rvRuleGroup = new RvRuleGroup()
            .ruleGroupName(DEFAULT_RULE_GROUP_NAME);
        return rvRuleGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvRuleGroup createUpdatedEntity(EntityManager em) {
        RvRuleGroup rvRuleGroup = new RvRuleGroup()
            .ruleGroupName(UPDATED_RULE_GROUP_NAME);
        return rvRuleGroup;
    }

    @BeforeEach
    public void initTest() {
        rvRuleGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createRvRuleGroup() throws Exception {
        int databaseSizeBeforeCreate = rvRuleGroupRepository.findAll().size();
        // Create the RvRuleGroup
        RvRuleGroupDTO rvRuleGroupDTO = rvRuleGroupMapper.toDto(rvRuleGroup);
        restRvRuleGroupMockMvc.perform(post("/api/rv-rule-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the RvRuleGroup in the database
        List<RvRuleGroup> rvRuleGroupList = rvRuleGroupRepository.findAll();
        assertThat(rvRuleGroupList).hasSize(databaseSizeBeforeCreate + 1);
        RvRuleGroup testRvRuleGroup = rvRuleGroupList.get(rvRuleGroupList.size() - 1);
        assertThat(testRvRuleGroup.getRuleGroupName()).isEqualTo(DEFAULT_RULE_GROUP_NAME);
    }

    @Test
    @Transactional
    public void createRvRuleGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rvRuleGroupRepository.findAll().size();

        // Create the RvRuleGroup with an existing ID
        rvRuleGroup.setId(1L);
        RvRuleGroupDTO rvRuleGroupDTO = rvRuleGroupMapper.toDto(rvRuleGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvRuleGroupMockMvc.perform(post("/api/rv-rule-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvRuleGroup in the database
        List<RvRuleGroup> rvRuleGroupList = rvRuleGroupRepository.findAll();
        assertThat(rvRuleGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRuleGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvRuleGroupRepository.findAll().size();
        // set the field null
        rvRuleGroup.setRuleGroupName(null);

        // Create the RvRuleGroup, which fails.
        RvRuleGroupDTO rvRuleGroupDTO = rvRuleGroupMapper.toDto(rvRuleGroup);


        restRvRuleGroupMockMvc.perform(post("/api/rv-rule-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleGroupDTO)))
            .andExpect(status().isBadRequest());

        List<RvRuleGroup> rvRuleGroupList = rvRuleGroupRepository.findAll();
        assertThat(rvRuleGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRvRuleGroups() throws Exception {
        // Initialize the database
        rvRuleGroupRepository.saveAndFlush(rvRuleGroup);

        // Get all the rvRuleGroupList
        restRvRuleGroupMockMvc.perform(get("/api/rv-rule-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvRuleGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleGroupName").value(hasItem(DEFAULT_RULE_GROUP_NAME)));
    }
    
    @Test
    @Transactional
    public void getRvRuleGroup() throws Exception {
        // Initialize the database
        rvRuleGroupRepository.saveAndFlush(rvRuleGroup);

        // Get the rvRuleGroup
        restRvRuleGroupMockMvc.perform(get("/api/rv-rule-groups/{id}", rvRuleGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvRuleGroup.getId().intValue()))
            .andExpect(jsonPath("$.ruleGroupName").value(DEFAULT_RULE_GROUP_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingRvRuleGroup() throws Exception {
        // Get the rvRuleGroup
        restRvRuleGroupMockMvc.perform(get("/api/rv-rule-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRvRuleGroup() throws Exception {
        // Initialize the database
        rvRuleGroupRepository.saveAndFlush(rvRuleGroup);

        int databaseSizeBeforeUpdate = rvRuleGroupRepository.findAll().size();

        // Update the rvRuleGroup
        RvRuleGroup updatedRvRuleGroup = rvRuleGroupRepository.findById(rvRuleGroup.getId()).get();
        // Disconnect from session so that the updates on updatedRvRuleGroup are not directly saved in db
        em.detach(updatedRvRuleGroup);
        updatedRvRuleGroup
            .ruleGroupName(UPDATED_RULE_GROUP_NAME);
        RvRuleGroupDTO rvRuleGroupDTO = rvRuleGroupMapper.toDto(updatedRvRuleGroup);

        restRvRuleGroupMockMvc.perform(put("/api/rv-rule-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleGroupDTO)))
            .andExpect(status().isOk());

        // Validate the RvRuleGroup in the database
        List<RvRuleGroup> rvRuleGroupList = rvRuleGroupRepository.findAll();
        assertThat(rvRuleGroupList).hasSize(databaseSizeBeforeUpdate);
        RvRuleGroup testRvRuleGroup = rvRuleGroupList.get(rvRuleGroupList.size() - 1);
        assertThat(testRvRuleGroup.getRuleGroupName()).isEqualTo(UPDATED_RULE_GROUP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRvRuleGroup() throws Exception {
        int databaseSizeBeforeUpdate = rvRuleGroupRepository.findAll().size();

        // Create the RvRuleGroup
        RvRuleGroupDTO rvRuleGroupDTO = rvRuleGroupMapper.toDto(rvRuleGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvRuleGroupMockMvc.perform(put("/api/rv-rule-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvRuleGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvRuleGroup in the database
        List<RvRuleGroup> rvRuleGroupList = rvRuleGroupRepository.findAll();
        assertThat(rvRuleGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRvRuleGroup() throws Exception {
        // Initialize the database
        rvRuleGroupRepository.saveAndFlush(rvRuleGroup);

        int databaseSizeBeforeDelete = rvRuleGroupRepository.findAll().size();

        // Delete the rvRuleGroup
        restRvRuleGroupMockMvc.perform(delete("/api/rv-rule-groups/{id}", rvRuleGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RvRuleGroup> rvRuleGroupList = rvRuleGroupRepository.findAll();
        assertThat(rvRuleGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
