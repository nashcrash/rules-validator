package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.Operator;
import it.sitissimo.validation.repository.OperatorRepository;
import it.sitissimo.validation.service.OperatorService;
import it.sitissimo.validation.service.dto.OperatorDTO;
import it.sitissimo.validation.service.mapper.OperatorMapper;

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
 * Integration tests for the {@link OperatorResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OperatorResourceIT {

    private static final String DEFAULT_OPERATOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPERATOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BEAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BEAN_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_PARAMETERS = 1;
    private static final Integer UPDATED_NUMBER_OF_PARAMETERS = 2;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorMapper operatorMapper;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperatorMockMvc;

    private Operator operator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createEntity(EntityManager em) {
        Operator operator = new Operator()
            .operatorCode(DEFAULT_OPERATOR_CODE)
            .description(DEFAULT_DESCRIPTION)
            .beanName(DEFAULT_BEAN_NAME)
            .numberOfParameters(DEFAULT_NUMBER_OF_PARAMETERS);
        return operator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operator createUpdatedEntity(EntityManager em) {
        Operator operator = new Operator()
            .operatorCode(UPDATED_OPERATOR_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME)
            .numberOfParameters(UPDATED_NUMBER_OF_PARAMETERS);
        return operator;
    }

    @BeforeEach
    public void initTest() {
        operator = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperator() throws Exception {
        int databaseSizeBeforeCreate = operatorRepository.findAll().size();
        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);
        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isCreated());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate + 1);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getOperatorCode()).isEqualTo(DEFAULT_OPERATOR_CODE);
        assertThat(testOperator.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOperator.getBeanName()).isEqualTo(DEFAULT_BEAN_NAME);
        assertThat(testOperator.getNumberOfParameters()).isEqualTo(DEFAULT_NUMBER_OF_PARAMETERS);
    }

    @Test
    @Transactional
    public void createOperatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operatorRepository.findAll().size();

        // Create the Operator with an existing ID
        operator.setId(1L);
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOperatorCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatorRepository.findAll().size();
        // set the field null
        operator.setOperatorCode(null);

        // Create the Operator, which fails.
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);


        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeanNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = operatorRepository.findAll().size();
        // set the field null
        operator.setBeanName(null);

        // Create the Operator, which fails.
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);


        restOperatorMockMvc.perform(post("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperators() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get all the operatorList
        restOperatorMockMvc.perform(get("/api/operators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operator.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatorCode").value(hasItem(DEFAULT_OPERATOR_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].beanName").value(hasItem(DEFAULT_BEAN_NAME)))
            .andExpect(jsonPath("$.[*].numberOfParameters").value(hasItem(DEFAULT_NUMBER_OF_PARAMETERS)));
    }
    
    @Test
    @Transactional
    public void getOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        // Get the operator
        restOperatorMockMvc.perform(get("/api/operators/{id}", operator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operator.getId().intValue()))
            .andExpect(jsonPath("$.operatorCode").value(DEFAULT_OPERATOR_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.beanName").value(DEFAULT_BEAN_NAME))
            .andExpect(jsonPath("$.numberOfParameters").value(DEFAULT_NUMBER_OF_PARAMETERS));
    }
    @Test
    @Transactional
    public void getNonExistingOperator() throws Exception {
        // Get the operator
        restOperatorMockMvc.perform(get("/api/operators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Update the operator
        Operator updatedOperator = operatorRepository.findById(operator.getId()).get();
        // Disconnect from session so that the updates on updatedOperator are not directly saved in db
        em.detach(updatedOperator);
        updatedOperator
            .operatorCode(UPDATED_OPERATOR_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME)
            .numberOfParameters(UPDATED_NUMBER_OF_PARAMETERS);
        OperatorDTO operatorDTO = operatorMapper.toDto(updatedOperator);

        restOperatorMockMvc.perform(put("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isOk());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
        Operator testOperator = operatorList.get(operatorList.size() - 1);
        assertThat(testOperator.getOperatorCode()).isEqualTo(UPDATED_OPERATOR_CODE);
        assertThat(testOperator.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOperator.getBeanName()).isEqualTo(UPDATED_BEAN_NAME);
        assertThat(testOperator.getNumberOfParameters()).isEqualTo(UPDATED_NUMBER_OF_PARAMETERS);
    }

    @Test
    @Transactional
    public void updateNonExistingOperator() throws Exception {
        int databaseSizeBeforeUpdate = operatorRepository.findAll().size();

        // Create the Operator
        OperatorDTO operatorDTO = operatorMapper.toDto(operator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperatorMockMvc.perform(put("/api/operators")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operatorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operator in the database
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperator() throws Exception {
        // Initialize the database
        operatorRepository.saveAndFlush(operator);

        int databaseSizeBeforeDelete = operatorRepository.findAll().size();

        // Delete the operator
        restOperatorMockMvc.perform(delete("/api/operators/{id}", operator.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Operator> operatorList = operatorRepository.findAll();
        assertThat(operatorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
