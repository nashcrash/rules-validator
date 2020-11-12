package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.Param;
import it.sitissimo.validation.repository.ParamRepository;
import it.sitissimo.validation.service.ParamService;
import it.sitissimo.validation.service.dto.ParamDTO;
import it.sitissimo.validation.service.mapper.ParamMapper;

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

/**
 * Integration tests for the {@link ParamResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParamResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ParamRepository paramRepository;

    @Mock
    private ParamRepository paramRepositoryMock;

    @Autowired
    private ParamMapper paramMapper;

    @Mock
    private ParamService paramServiceMock;

    @Autowired
    private ParamService paramService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParamMockMvc;

    private Param param;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Param createEntity(EntityManager em) {
        Param param = new Param()
            .value(DEFAULT_VALUE);
        return param;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Param createUpdatedEntity(EntityManager em) {
        Param param = new Param()
            .value(UPDATED_VALUE);
        return param;
    }

    @BeforeEach
    public void initTest() {
        param = createEntity(em);
    }

    @Test
    @Transactional
    public void createParam() throws Exception {
        int databaseSizeBeforeCreate = paramRepository.findAll().size();
        // Create the Param
        ParamDTO paramDTO = paramMapper.toDto(param);
        restParamMockMvc.perform(post("/api/params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramDTO)))
            .andExpect(status().isCreated());

        // Validate the Param in the database
        List<Param> paramList = paramRepository.findAll();
        assertThat(paramList).hasSize(databaseSizeBeforeCreate + 1);
        Param testParam = paramList.get(paramList.size() - 1);
        assertThat(testParam.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramRepository.findAll().size();

        // Create the Param with an existing ID
        param.setId(1L);
        ParamDTO paramDTO = paramMapper.toDto(param);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamMockMvc.perform(post("/api/params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Param in the database
        List<Param> paramList = paramRepository.findAll();
        assertThat(paramList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParams() throws Exception {
        // Initialize the database
        paramRepository.saveAndFlush(param);

        // Get all the paramList
        restParamMockMvc.perform(get("/api/params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(param.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllParamsWithEagerRelationshipsIsEnabled() throws Exception {
        when(paramServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParamMockMvc.perform(get("/api/params?eagerload=true"))
            .andExpect(status().isOk());

        verify(paramServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllParamsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paramServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restParamMockMvc.perform(get("/api/params?eagerload=true"))
            .andExpect(status().isOk());

        verify(paramServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getParam() throws Exception {
        // Initialize the database
        paramRepository.saveAndFlush(param);

        // Get the param
        restParamMockMvc.perform(get("/api/params/{id}", param.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(param.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingParam() throws Exception {
        // Get the param
        restParamMockMvc.perform(get("/api/params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParam() throws Exception {
        // Initialize the database
        paramRepository.saveAndFlush(param);

        int databaseSizeBeforeUpdate = paramRepository.findAll().size();

        // Update the param
        Param updatedParam = paramRepository.findById(param.getId()).get();
        // Disconnect from session so that the updates on updatedParam are not directly saved in db
        em.detach(updatedParam);
        updatedParam
            .value(UPDATED_VALUE);
        ParamDTO paramDTO = paramMapper.toDto(updatedParam);

        restParamMockMvc.perform(put("/api/params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramDTO)))
            .andExpect(status().isOk());

        // Validate the Param in the database
        List<Param> paramList = paramRepository.findAll();
        assertThat(paramList).hasSize(databaseSizeBeforeUpdate);
        Param testParam = paramList.get(paramList.size() - 1);
        assertThat(testParam.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingParam() throws Exception {
        int databaseSizeBeforeUpdate = paramRepository.findAll().size();

        // Create the Param
        ParamDTO paramDTO = paramMapper.toDto(param);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParamMockMvc.perform(put("/api/params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Param in the database
        List<Param> paramList = paramRepository.findAll();
        assertThat(paramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParam() throws Exception {
        // Initialize the database
        paramRepository.saveAndFlush(param);

        int databaseSizeBeforeDelete = paramRepository.findAll().size();

        // Delete the param
        restParamMockMvc.perform(delete("/api/params/{id}", param.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Param> paramList = paramRepository.findAll();
        assertThat(paramList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
