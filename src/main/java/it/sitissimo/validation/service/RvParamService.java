package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.RvParamDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.RvParam}.
 */
public interface RvParamService {

    /**
     * Save a rvParam.
     *
     * @param rvParamDTO the entity to save.
     * @return the persisted entity.
     */
    RvParamDTO save(RvParamDTO rvParamDTO);

    /**
     * Get all the rvParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RvParamDTO> findAll(Pageable pageable);

    /**
     * Get all the rvParams with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<RvParamDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" rvParam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RvParamDTO> findOne(Long id);

    /**
     * Delete the "id" rvParam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
