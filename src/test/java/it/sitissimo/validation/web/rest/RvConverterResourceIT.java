package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.RvConverter;
import it.sitissimo.validation.repository.RvConverterRepository;
import it.sitissimo.validation.service.RvConverterService;
import it.sitissimo.validation.service.dto.RvConverterDTO;
import it.sitissimo.validation.service.mapper.RvConverterMapper;

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
 * Integration tests for the {@link RvConverterResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RvConverterResourceIT {

    private static final String DEFAULT_CONVERTER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONVERTER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BEAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BEAN_NAME = "BBBBBBBBBB";

    @Autowired
    private RvConverterRepository rvConverterRepository;

    @Autowired
    private RvConverterMapper rvConverterMapper;

    @Autowired
    private RvConverterService rvConverterService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRvConverterMockMvc;

    private RvConverter rvConverter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvConverter createEntity(EntityManager em) {
        RvConverter rvConverter = new RvConverter()
            .converterCode(DEFAULT_CONVERTER_CODE)
            .description(DEFAULT_DESCRIPTION)
            .beanName(DEFAULT_BEAN_NAME);
        return rvConverter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvConverter createUpdatedEntity(EntityManager em) {
        RvConverter rvConverter = new RvConverter()
            .converterCode(UPDATED_CONVERTER_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME);
        return rvConverter;
    }

    @BeforeEach
    public void initTest() {
        rvConverter = createEntity(em);
    }

    @Test
    @Transactional
    public void createRvConverter() throws Exception {
        int databaseSizeBeforeCreate = rvConverterRepository.findAll().size();
        // Create the RvConverter
        RvConverterDTO rvConverterDTO = rvConverterMapper.toDto(rvConverter);
        restRvConverterMockMvc.perform(post("/api/rv-converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvConverterDTO)))
            .andExpect(status().isCreated());

        // Validate the RvConverter in the database
        List<RvConverter> rvConverterList = rvConverterRepository.findAll();
        assertThat(rvConverterList).hasSize(databaseSizeBeforeCreate + 1);
        RvConverter testRvConverter = rvConverterList.get(rvConverterList.size() - 1);
        assertThat(testRvConverter.getConverterCode()).isEqualTo(DEFAULT_CONVERTER_CODE);
        assertThat(testRvConverter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRvConverter.getBeanName()).isEqualTo(DEFAULT_BEAN_NAME);
    }

    @Test
    @Transactional
    public void createRvConverterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rvConverterRepository.findAll().size();

        // Create the RvConverter with an existing ID
        rvConverter.setId(1L);
        RvConverterDTO rvConverterDTO = rvConverterMapper.toDto(rvConverter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvConverterMockMvc.perform(post("/api/rv-converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvConverterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvConverter in the database
        List<RvConverter> rvConverterList = rvConverterRepository.findAll();
        assertThat(rvConverterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkConverterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvConverterRepository.findAll().size();
        // set the field null
        rvConverter.setConverterCode(null);

        // Create the RvConverter, which fails.
        RvConverterDTO rvConverterDTO = rvConverterMapper.toDto(rvConverter);


        restRvConverterMockMvc.perform(post("/api/rv-converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvConverterDTO)))
            .andExpect(status().isBadRequest());

        List<RvConverter> rvConverterList = rvConverterRepository.findAll();
        assertThat(rvConverterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeanNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = rvConverterRepository.findAll().size();
        // set the field null
        rvConverter.setBeanName(null);

        // Create the RvConverter, which fails.
        RvConverterDTO rvConverterDTO = rvConverterMapper.toDto(rvConverter);


        restRvConverterMockMvc.perform(post("/api/rv-converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvConverterDTO)))
            .andExpect(status().isBadRequest());

        List<RvConverter> rvConverterList = rvConverterRepository.findAll();
        assertThat(rvConverterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRvConverters() throws Exception {
        // Initialize the database
        rvConverterRepository.saveAndFlush(rvConverter);

        // Get all the rvConverterList
        restRvConverterMockMvc.perform(get("/api/rv-converters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvConverter.getId().intValue())))
            .andExpect(jsonPath("$.[*].converterCode").value(hasItem(DEFAULT_CONVERTER_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].beanName").value(hasItem(DEFAULT_BEAN_NAME)));
    }
    
    @Test
    @Transactional
    public void getRvConverter() throws Exception {
        // Initialize the database
        rvConverterRepository.saveAndFlush(rvConverter);

        // Get the rvConverter
        restRvConverterMockMvc.perform(get("/api/rv-converters/{id}", rvConverter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvConverter.getId().intValue()))
            .andExpect(jsonPath("$.converterCode").value(DEFAULT_CONVERTER_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.beanName").value(DEFAULT_BEAN_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingRvConverter() throws Exception {
        // Get the rvConverter
        restRvConverterMockMvc.perform(get("/api/rv-converters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRvConverter() throws Exception {
        // Initialize the database
        rvConverterRepository.saveAndFlush(rvConverter);

        int databaseSizeBeforeUpdate = rvConverterRepository.findAll().size();

        // Update the rvConverter
        RvConverter updatedRvConverter = rvConverterRepository.findById(rvConverter.getId()).get();
        // Disconnect from session so that the updates on updatedRvConverter are not directly saved in db
        em.detach(updatedRvConverter);
        updatedRvConverter
            .converterCode(UPDATED_CONVERTER_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME);
        RvConverterDTO rvConverterDTO = rvConverterMapper.toDto(updatedRvConverter);

        restRvConverterMockMvc.perform(put("/api/rv-converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvConverterDTO)))
            .andExpect(status().isOk());

        // Validate the RvConverter in the database
        List<RvConverter> rvConverterList = rvConverterRepository.findAll();
        assertThat(rvConverterList).hasSize(databaseSizeBeforeUpdate);
        RvConverter testRvConverter = rvConverterList.get(rvConverterList.size() - 1);
        assertThat(testRvConverter.getConverterCode()).isEqualTo(UPDATED_CONVERTER_CODE);
        assertThat(testRvConverter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRvConverter.getBeanName()).isEqualTo(UPDATED_BEAN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRvConverter() throws Exception {
        int databaseSizeBeforeUpdate = rvConverterRepository.findAll().size();

        // Create the RvConverter
        RvConverterDTO rvConverterDTO = rvConverterMapper.toDto(rvConverter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvConverterMockMvc.perform(put("/api/rv-converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvConverterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvConverter in the database
        List<RvConverter> rvConverterList = rvConverterRepository.findAll();
        assertThat(rvConverterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRvConverter() throws Exception {
        // Initialize the database
        rvConverterRepository.saveAndFlush(rvConverter);

        int databaseSizeBeforeDelete = rvConverterRepository.findAll().size();

        // Delete the rvConverter
        restRvConverterMockMvc.perform(delete("/api/rv-converters/{id}", rvConverter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RvConverter> rvConverterList = rvConverterRepository.findAll();
        assertThat(rvConverterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
