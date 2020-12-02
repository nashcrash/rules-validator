package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.RulesValidatorApp;
import it.sitissimo.validation.domain.RvParam;
import it.sitissimo.validation.repository.RvParamRepository;
import it.sitissimo.validation.service.RvParamService;
import it.sitissimo.validation.service.dto.RvParamDTO;
import it.sitissimo.validation.service.mapper.RvParamMapper;

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
 * Integration tests for the {@link RvParamResource} REST controller.
 */
@SpringBootTest(classes = RulesValidatorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RvParamResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private RvParamRepository rvParamRepository;

    @Mock
    private RvParamRepository rvParamRepositoryMock;

    @Autowired
    private RvParamMapper rvParamMapper;

    @Mock
    private RvParamService rvParamServiceMock;

    @Autowired
    private RvParamService rvParamService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRvParamMockMvc;

    private RvParam rvParam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvParam createEntity(EntityManager em) {
        RvParam rvParam = new RvParam()
            .value(DEFAULT_VALUE);
        return rvParam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RvParam createUpdatedEntity(EntityManager em) {
        RvParam rvParam = new RvParam()
            .value(UPDATED_VALUE);
        return rvParam;
    }

    @BeforeEach
    public void initTest() {
        rvParam = createEntity(em);
    }

    @Test
    @Transactional
    public void createRvParam() throws Exception {
        int databaseSizeBeforeCreate = rvParamRepository.findAll().size();
        // Create the RvParam
        RvParamDTO rvParamDTO = rvParamMapper.toDto(rvParam);
        restRvParamMockMvc.perform(post("/api/rv-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvParamDTO)))
            .andExpect(status().isCreated());

        // Validate the RvParam in the database
        List<RvParam> rvParamList = rvParamRepository.findAll();
        assertThat(rvParamList).hasSize(databaseSizeBeforeCreate + 1);
        RvParam testRvParam = rvParamList.get(rvParamList.size() - 1);
        assertThat(testRvParam.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createRvParamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rvParamRepository.findAll().size();

        // Create the RvParam with an existing ID
        rvParam.setId(1L);
        RvParamDTO rvParamDTO = rvParamMapper.toDto(rvParam);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRvParamMockMvc.perform(post("/api/rv-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvParam in the database
        List<RvParam> rvParamList = rvParamRepository.findAll();
        assertThat(rvParamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRvParams() throws Exception {
        // Initialize the database
        rvParamRepository.saveAndFlush(rvParam);

        // Get all the rvParamList
        restRvParamMockMvc.perform(get("/api/rv-params?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rvParam.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllRvParamsWithEagerRelationshipsIsEnabled() throws Exception {
        when(rvParamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRvParamMockMvc.perform(get("/api/rv-params?eagerload=true"))
            .andExpect(status().isOk());

        verify(rvParamServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllRvParamsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(rvParamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRvParamMockMvc.perform(get("/api/rv-params?eagerload=true"))
            .andExpect(status().isOk());

        verify(rvParamServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRvParam() throws Exception {
        // Initialize the database
        rvParamRepository.saveAndFlush(rvParam);

        // Get the rvParam
        restRvParamMockMvc.perform(get("/api/rv-params/{id}", rvParam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rvParam.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingRvParam() throws Exception {
        // Get the rvParam
        restRvParamMockMvc.perform(get("/api/rv-params/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRvParam() throws Exception {
        // Initialize the database
        rvParamRepository.saveAndFlush(rvParam);

        int databaseSizeBeforeUpdate = rvParamRepository.findAll().size();

        // Update the rvParam
        RvParam updatedRvParam = rvParamRepository.findById(rvParam.getId()).get();
        // Disconnect from session so that the updates on updatedRvParam are not directly saved in db
        em.detach(updatedRvParam);
        updatedRvParam
            .value(UPDATED_VALUE);
        RvParamDTO rvParamDTO = rvParamMapper.toDto(updatedRvParam);

        restRvParamMockMvc.perform(put("/api/rv-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvParamDTO)))
            .andExpect(status().isOk());

        // Validate the RvParam in the database
        List<RvParam> rvParamList = rvParamRepository.findAll();
        assertThat(rvParamList).hasSize(databaseSizeBeforeUpdate);
        RvParam testRvParam = rvParamList.get(rvParamList.size() - 1);
        assertThat(testRvParam.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingRvParam() throws Exception {
        int databaseSizeBeforeUpdate = rvParamRepository.findAll().size();

        // Create the RvParam
        RvParamDTO rvParamDTO = rvParamMapper.toDto(rvParam);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRvParamMockMvc.perform(put("/api/rv-params")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rvParamDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RvParam in the database
        List<RvParam> rvParamList = rvParamRepository.findAll();
        assertThat(rvParamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRvParam() throws Exception {
        // Initialize the database
        rvParamRepository.saveAndFlush(rvParam);

        int databaseSizeBeforeDelete = rvParamRepository.findAll().size();

        // Delete the rvParam
        restRvParamMockMvc.perform(delete("/api/rv-params/{id}", rvParam.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RvParam> rvParamList = rvParamRepository.findAll();
        assertThat(rvParamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
