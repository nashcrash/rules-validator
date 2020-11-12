package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.ParamDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.Param}.
 */
public interface ParamService {

    /**
     * Save a param.
     *
     * @param paramDTO the entity to save.
     * @return the persisted entity.
     */
    ParamDTO save(ParamDTO paramDTO);

    /**
     * Get all the params.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParamDTO> findAll(Pageable pageable);

    /**
     * Get all the params with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ParamDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" param.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParamDTO> findOne(Long id);

    /**
     * Delete the "id" param.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
