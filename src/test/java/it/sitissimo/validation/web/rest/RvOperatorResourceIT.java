package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.RvOperator;
import it.sitissimo.validation.repository.RvOperatorRepository;
import it.sitissimo.validation.service.RvOperatorService;
import it.sitissimo.validation.service.dto.RvOperatorDTO;
import it.sitissimo.validation.service.mapper.RvOperatorMapper;

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
 * Integration tests for the {@link RvOperatorResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RvOperatorResourceIT {

    private static final String DEFAULT_OPERATOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BEAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BEAN_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_PARAMS = 1;
    private static final Integer UPDATED_NUMBER_OF_PARAMS = 2;

    @Autowired
    private RvOperatorRepository rvOperatorRepository;

    @Autowired
    private RvOperatorMapper rvOperatorMapper;

    @Autowired
    private RvOperatorService rvOperatorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRvOperatorMockMvc;

    private RvOperator rvOperator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvOperator createEntity(EntityManager em) {
        RvOperator rvOperator = new RvOperator()
            .operatorCode(DEFAULT_OPERATOR_CODE)
            .description(DEFAULT_DESCRIPTION)
            .beanName(DEFAULT_BEAN_NAME)
            .numberOfParams(DEFAULT_NUMBER_OF_PARAMS);
        return rvOperator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvOperator createUpdatedEntity(EntityManager em) {
        RvOperator rvOperator = new RvOperator()
            .operatorCode(UPDATED_OPERATOR_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME)
            .numberOfParams(UPDATED_NUMBER_OF_PARAMS);
        return rvOperator;
    }

    @BeforeEach
    public void initTest() {
        rvOperator = createEntity(em);
    }

    @Test
    @Transactional
    public void createRvOperator() throws Exception {
        int databaseSizeBeforeCreate = rvOperatorRepository.findAll().size();
        // Create the RvOperator
        RvOperatorDTO rvOperatorDTO = rvOperatorMapper.toDto(rvOperator);
        restRvOperatorMockMvc.perform(post("/api/rv-operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorDTO)))
            .andExpect(status().isCreated());

        // Validate the RvOperator in the database
        List<RvOperator> rvOperatorList = rvOperatorRepository.findAll();
        assertThat(rvOperatorList).hasSize(databaseSizeBeforeCreate + 1);
        RvOperator testRvOperator = rvOperatorList.get(rvOperatorList.size() - 1);
        assertThat(testRvOperator.getOperatorCode()).isEqualTo(DEFAULT_OPERATOR_CODE);
        assertThat(testRvOperator.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRvOperator.getBeanName()).isEqualTo(DEFAULT_BEAN_NAME);
        assertThat(testRvOperator.getNumberOfParams()).isEqualTo(DEFAULT_NUMBER_OF_PARAMS);
    }

    @Test
    @Transactional
    public void createRvOperatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rvOperatorRepository.findAll().size();

        // Create the RvOperator with an existing ID
        rvOperator.setId(1L);
        RvOperatorDTO rvOperatorDTO = rvOperatorMapper.toDto(rvOperator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvOperatorMockMvc.perform(post("/api/rv-operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvOperator in the database
        List<RvOperator> rvOperatorList = rvOperatorRepository.findAll();
        assertThat(rvOperatorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOperatorCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvOperatorRepository.findAll().size();
        // set the field null
        rvOperator.setOperatorCode(null);

        // Create the RvOperator, which fails.
        RvOperatorDTO rvOperatorDTO = rvOperatorMapper.toDto(rvOperator);


        restRvOperatorMockMvc.perform(post("/api/rv-operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorDTO)))
            .andExpect(status().isBadRequest());

        List<RvOperator> rvOperatorList = rvOperatorRepository.findAll();
        assertThat(rvOperatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeanNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvOperatorRepository.findAll().size();
        // set the field null
        rvOperator.setBeanName(null);

        // Create the RvOperator, which fails.
        RvOperatorDTO rvOperatorDTO = rvOperatorMapper.toDto(rvOperator);


        restRvOperatorMockMvc.perform(post("/api/rv-operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorDTO)))
            .andExpect(status().isBadRequest());

        List<RvOperator> rvOperatorList = rvOperatorRepository.findAll();
        assertThat(rvOperatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRvOperators() throws Exception {
        // Initialize the database
        rvOperatorRepository.saveAndFlush(rvOperator);

        // Get all the rvOperatorList
        restRvOperatorMockMvc.perform(get("/api/rv-operators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvOperator.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatorCode").value(hasItem(DEFAULT_OPERATOR_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].beanName").value(hasItem(DEFAULT_BEAN_NAME)))
            .andExpect(jsonPath("$.[*].numberOfParams").value(hasItem(DEFAULT_NUMBER_OF_PARAMS)));
    }
    
    @Test
    @Transactional
    public void getRvOperator() throws Exception {
        // Initialize the database
        rvOperatorRepository.saveAndFlush(rvOperator);

        // Get the rvOperator
        restRvOperatorMockMvc.perform(get("/api/rv-operators/{id}", rvOperator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvOperator.getId().intValue()))
            .andExpect(jsonPath("$.operatorCode").value(DEFAULT_OPERATOR_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.beanName").value(DEFAULT_BEAN_NAME))
            .andExpect(jsonPath("$.numberOfParams").value(DEFAULT_NUMBER_OF_PARAMS));
    }
    @Test
    @Transactional
    public void getNonExistingRvOperator() throws Exception {
        // Get the rvOperator
        restRvOperatorMockMvc.perform(get("/api/rv-operators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRvOperator() throws Exception {
        // Initialize the database
        rvOperatorRepository.saveAndFlush(rvOperator);

        int databaseSizeBeforeUpdate = rvOperatorRepository.findAll().size();

        // Update the rvOperator
        RvOperator updatedRvOperator = rvOperatorRepository.findById(rvOperator.getId()).get();
        // Disconnect from session so that the updates on updatedRvOperator are not directly saved in db
        em.detach(updatedRvOperator);
        updatedRvOperator
            .operatorCode(UPDATED_OPERATOR_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME)
            .numberOfParams(UPDATED_NUMBER_OF_PARAMS);
        RvOperatorDTO rvOperatorDTO = rvOperatorMapper.toDto(updatedRvOperator);

        restRvOperatorMockMvc.perform(put("/api/rv-operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorDTO)))
            .andExpect(status().isOk());

        // Validate the RvOperator in the database
        List<RvOperator> rvOperatorList = rvOperatorRepository.findAll();
        assertThat(rvOperatorList).hasSize(databaseSizeBeforeUpdate);
        RvOperator testRvOperator = rvOperatorList.get(rvOperatorList.size() - 1);
        assertThat(testRvOperator.getOperatorCode()).isEqualTo(UPDATED_OPERATOR_CODE);
        assertThat(testRvOperator.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRvOperator.getBeanName()).isEqualTo(UPDATED_BEAN_NAME);
        assertThat(testRvOperator.getNumberOfParams()).isEqualTo(UPDATED_NUMBER_OF_PARAMS);
    }

    @Test
    @Transactional
    public void updateNonExistingRvOperator() throws Exception {
        int databaseSizeBeforeUpdate = rvOperatorRepository.findAll().size();

        // Create the RvOperator
        RvOperatorDTO rvOperatorDTO = rvOperatorMapper.toDto(rvOperator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvOperatorMockMvc.perform(put("/api/rv-operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvOperator in the database
        List<RvOperator> rvOperatorList = rvOperatorRepository.findAll();
        assertThat(rvOperatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRvOperator() throws Exception {
        // Initialize the database
        rvOperatorRepository.saveAndFlush(rvOperator);

        int databaseSizeBeforeDelete = rvOperatorRepository.findAll().size();

        // Delete the rvOperator
        restRvOperatorMockMvc.perform(delete("/api/rv-operators/{id}", rvOperator.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RvOperator> rvOperatorList = rvOperatorRepository.findAll();
        assertThat(rvOperatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
