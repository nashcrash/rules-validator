package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.RvOperatorParam;
import it.sitissimo.validation.repository.RvOperatorParamRepository;
import it.sitissimo.validation.service.RvOperatorParamService;
import it.sitissimo.validation.service.dto.RvOperatorParamDTO;
import it.sitissimo.validation.service.mapper.RvOperatorParamMapper;

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

import it.sitissimo.validation.domain.enumeration.RvParamType;
/**
 * Integration tests for the {@link RvOperatorParamResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RvOperatorParamResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final RvParamType DEFAULT_TYPE = RvParamType.STRING;
    private static final RvParamType UPDATED_TYPE = RvParamType.INTEGER;

    @Autowired
    private RvOperatorParamRepository rvOperatorParamRepository;

    @Autowired
    private RvOperatorParamMapper rvOperatorParamMapper;

    @Autowired
    private RvOperatorParamService rvOperatorParamService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRvOperatorParamMockMvc;

    private RvOperatorParam rvOperatorParam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvOperatorParam createEntity(EntityManager em) {
        RvOperatorParam rvOperatorParam = new RvOperatorParam()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE);
        return rvOperatorParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvOperatorParam createUpdatedEntity(EntityManager em) {
        RvOperatorParam rvOperatorParam = new RvOperatorParam()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        return rvOperatorParam;
    }

    @BeforeEach
    public void initTest() {
        rvOperatorParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createRvOperatorParam() throws Exception {
        int databaseSizeBeforeCreate = rvOperatorParamRepository.findAll().size();
        // Create the RvOperatorParam
        RvOperatorParamDTO rvOperatorParamDTO = rvOperatorParamMapper.toDto(rvOperatorParam);
        restRvOperatorParamMockMvc.perform(post("/api/rv-operator-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorParamDTO)))
            .andExpect(status().isCreated());

        // Validate the RvOperatorParam in the database
        List<RvOperatorParam> rvOperatorParamList = rvOperatorParamRepository.findAll();
        assertThat(rvOperatorParamList).hasSize(databaseSizeBeforeCreate + 1);
        RvOperatorParam testRvOperatorParam = rvOperatorParamList.get(rvOperatorParamList.size() - 1);
        assertThat(testRvOperatorParam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRvOperatorParam.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRvOperatorParam.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createRvOperatorParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rvOperatorParamRepository.findAll().size();

        // Create the RvOperatorParam with an existing ID
        rvOperatorParam.setId(1L);
        RvOperatorParamDTO rvOperatorParamDTO = rvOperatorParamMapper.toDto(rvOperatorParam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvOperatorParamMockMvc.perform(post("/api/rv-operator-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvOperatorParam in the database
        List<RvOperatorParam> rvOperatorParamList = rvOperatorParamRepository.findAll();
        assertThat(rvOperatorParamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvOperatorParamRepository.findAll().size();
        // set the field null
        rvOperatorParam.setName(null);

        // Create the RvOperatorParam, which fails.
        RvOperatorParamDTO rvOperatorParamDTO = rvOperatorParamMapper.toDto(rvOperatorParam);


        restRvOperatorParamMockMvc.perform(post("/api/rv-operator-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorParamDTO)))
            .andExpect(status().isBadRequest());

        List<RvOperatorParam> rvOperatorParamList = rvOperatorParamRepository.findAll();
        assertThat(rvOperatorParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvOperatorParamRepository.findAll().size();
        // set the field null
        rvOperatorParam.setType(null);

        // Create the RvOperatorParam, which fails.
        RvOperatorParamDTO rvOperatorParamDTO = rvOperatorParamMapper.toDto(rvOperatorParam);


        restRvOperatorParamMockMvc.perform(post("/api/rv-operator-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorParamDTO)))
            .andExpect(status().isBadRequest());

        List<RvOperatorParam> rvOperatorParamList = rvOperatorParamRepository.findAll();
        assertThat(rvOperatorParamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRvOperatorParams() throws Exception {
        // Initialize the database
        rvOperatorParamRepository.saveAndFlush(rvOperatorParam);

        // Get all the rvOperatorParamList
        restRvOperatorParamMockMvc.perform(get("/api/rv-operator-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvOperatorParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getRvOperatorParam() throws Exception {
        // Initialize the database
        rvOperatorParamRepository.saveAndFlush(rvOperatorParam);

        // Get the rvOperatorParam
        restRvOperatorParamMockMvc.perform(get("/api/rv-operator-params/{id}", rvOperatorParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvOperatorParam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRvOperatorParam() throws Exception {
        // Get the rvOperatorParam
        restRvOperatorParamMockMvc.perform(get("/api/rv-operator-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRvOperatorParam() throws Exception {
        // Initialize the database
        rvOperatorParamRepository.saveAndFlush(rvOperatorParam);

        int databaseSizeBeforeUpdate = rvOperatorParamRepository.findAll().size();

        // Update the rvOperatorParam
        RvOperatorParam updatedRvOperatorParam = rvOperatorParamRepository.findById(rvOperatorParam.getId()).get();
        // Disconnect from session so that the updates on updatedRvOperatorParam are not directly saved in db
        em.detach(updatedRvOperatorParam);
        updatedRvOperatorParam
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE);
        RvOperatorParamDTO rvOperatorParamDTO = rvOperatorParamMapper.toDto(updatedRvOperatorParam);

        restRvOperatorParamMockMvc.perform(put("/api/rv-operator-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorParamDTO)))
            .andExpect(status().isOk());

        // Validate the RvOperatorParam in the database
        List<RvOperatorParam> rvOperatorParamList = rvOperatorParamRepository.findAll();
        assertThat(rvOperatorParamList).hasSize(databaseSizeBeforeUpdate);
        RvOperatorParam testRvOperatorParam = rvOperatorParamList.get(rvOperatorParamList.size() - 1);
        assertThat(testRvOperatorParam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRvOperatorParam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRvOperatorParam.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRvOperatorParam() throws Exception {
        int databaseSizeBeforeUpdate = rvOperatorParamRepository.findAll().size();

        // Create the RvOperatorParam
        RvOperatorParamDTO rvOperatorParamDTO = rvOperatorParamMapper.toDto(rvOperatorParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvOperatorParamMockMvc.perform(put("/api/rv-operator-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvOperatorParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvOperatorParam in the database
        List<RvOperatorParam> rvOperatorParamList = rvOperatorParamRepository.findAll();
        assertThat(rvOperatorParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRvOperatorParam() throws Exception {
        // Initialize the database
        rvOperatorParamRepository.saveAndFlush(rvOperatorParam);

        int databaseSizeBeforeDelete = rvOperatorParamRepository.findAll().size();

        // Delete the rvOperatorParam
        restRvOperatorParamMockMvc.perform(delete("/api/rv-operator-params/{id}", rvOperatorParam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RvOperatorParam> rvOperatorParamList = rvOperatorParamRepository.findAll();
        assertThat(rvOperatorParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
