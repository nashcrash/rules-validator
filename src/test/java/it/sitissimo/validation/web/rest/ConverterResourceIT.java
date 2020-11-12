package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.Converter;
import it.sitissimo.validation.repository.ConverterRepository;
import it.sitissimo.validation.service.ConverterService;
import it.sitissimo.validation.service.dto.ConverterDTO;
import it.sitissimo.validation.service.mapper.ConverterMapper;

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
 * Integration tests for the {@link ConverterResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConverterResourceIT {

    private static final String DEFAULT_CONVERTER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CONVERTER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BEAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BEAN_NAME = "BBBBBBBBBB";

    @Autowired
    private ConverterRepository converterRepository;

    @Autowired
    private ConverterMapper converterMapper;

    @Autowired
    private ConverterService converterService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConverterMockMvc;

    private Converter converter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Converter createEntity(EntityManager em) {
        Converter converter = new Converter()
            .converterCode(DEFAULT_CONVERTER_CODE)
            .description(DEFAULT_DESCRIPTION)
            .beanName(DEFAULT_BEAN_NAME);
        return converter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Converter createUpdatedEntity(EntityManager em) {
        Converter converter = new Converter()
            .converterCode(UPDATED_CONVERTER_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME);
        return converter;
    }

    @BeforeEach
    public void initTest() {
        converter = createEntity(em);
    }

    @Test
    @Transactional
    public void createConverter() throws Exception {
        int databaseSizeBeforeCreate = converterRepository.findAll().size();
        // Create the Converter
        ConverterDTO converterDTO = converterMapper.toDto(converter);
        restConverterMockMvc.perform(post("/api/converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(converterDTO)))
            .andExpect(status().isCreated());

        // Validate the Converter in the database
        List<Converter> converterList = converterRepository.findAll();
        assertThat(converterList).hasSize(databaseSizeBeforeCreate + 1);
        Converter testConverter = converterList.get(converterList.size() - 1);
        assertThat(testConverter.getConverterCode()).isEqualTo(DEFAULT_CONVERTER_CODE);
        assertThat(testConverter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConverter.getBeanName()).isEqualTo(DEFAULT_BEAN_NAME);
    }

    @Test
    @Transactional
    public void createConverterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = converterRepository.findAll().size();

        // Create the Converter with an existing ID
        converter.setId(1L);
        ConverterDTO converterDTO = converterMapper.toDto(converter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConverterMockMvc.perform(post("/api/converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(converterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Converter in the database
        List<Converter> converterList = converterRepository.findAll();
        assertThat(converterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkConverterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = converterRepository.findAll().size();
        // set the field null
        converter.setConverterCode(null);

        // Create the Converter, which fails.
        ConverterDTO converterDTO = converterMapper.toDto(converter);


        restConverterMockMvc.perform(post("/api/converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(converterDTO)))
            .andExpect(status().isBadRequest());

        List<Converter> converterList = converterRepository.findAll();
        assertThat(converterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeanNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = converterRepository.findAll().size();
        // set the field null
        converter.setBeanName(null);

        // Create the Converter, which fails.
        ConverterDTO converterDTO = converterMapper.toDto(converter);


        restConverterMockMvc.perform(post("/api/converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(converterDTO)))
            .andExpect(status().isBadRequest());

        List<Converter> converterList = converterRepository.findAll();
        assertThat(converterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConverters() throws Exception {
        // Initialize the database
        converterRepository.saveAndFlush(converter);

        // Get all the converterList
        restConverterMockMvc.perform(get("/api/converters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(converter.getId().intValue())))
            .andExpect(jsonPath("$.[*].converterCode").value(hasItem(DEFAULT_CONVERTER_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].beanName").value(hasItem(DEFAULT_BEAN_NAME)));
    }
    
    @Test
    @Transactional
    public void getConverter() throws Exception {
        // Initialize the database
        converterRepository.saveAndFlush(converter);

        // Get the converter
        restConverterMockMvc.perform(get("/api/converters/{id}", converter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(converter.getId().intValue()))
            .andExpect(jsonPath("$.converterCode").value(DEFAULT_CONVERTER_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.beanName").value(DEFAULT_BEAN_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingConverter() throws Exception {
        // Get the converter
        restConverterMockMvc.perform(get("/api/converters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConverter() throws Exception {
        // Initialize the database
        converterRepository.saveAndFlush(converter);

        int databaseSizeBeforeUpdate = converterRepository.findAll().size();

        // Update the converter
        Converter updatedConverter = converterRepository.findById(converter.getId()).get();
        // Disconnect from session so that the updates on updatedConverter are not directly saved in db
        em.detach(updatedConverter);
        updatedConverter
            .converterCode(UPDATED_CONVERTER_CODE)
            .description(UPDATED_DESCRIPTION)
            .beanName(UPDATED_BEAN_NAME);
        ConverterDTO converterDTO = converterMapper.toDto(updatedConverter);

        restConverterMockMvc.perform(put("/api/converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(converterDTO)))
            .andExpect(status().isOk());

        // Validate the Converter in the database
        List<Converter> converterList = converterRepository.findAll();
        assertThat(converterList).hasSize(databaseSizeBeforeUpdate);
        Converter testConverter = converterList.get(converterList.size() - 1);
        assertThat(testConverter.getConverterCode()).isEqualTo(UPDATED_CONVERTER_CODE);
        assertThat(testConverter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConverter.getBeanName()).isEqualTo(UPDATED_BEAN_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingConverter() throws Exception {
        int databaseSizeBeforeUpdate = converterRepository.findAll().size();

        // Create the Converter
        ConverterDTO converterDTO = converterMapper.toDto(converter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConverterMockMvc.perform(put("/api/converters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(converterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Converter in the database
        List<Converter> converterList = converterRepository.findAll();
        assertThat(converterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConverter() throws Exception {
        // Initialize the database
        converterRepository.saveAndFlush(converter);

        int databaseSizeBeforeDelete = converterRepository.findAll().size();

        // Delete the converter
        restConverterMockMvc.perform(delete("/api/converters/{id}", converter.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Converter> converterList = converterRepository.findAll();
        assertThat(converterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
