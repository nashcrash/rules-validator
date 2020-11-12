package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.ParameterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.Parameter}.
 */
public interface ParameterService {

    /**
     * Save a parameter.
     *
     * @param parameterDTO the entity to save.
     * @return the persisted entity.
     */
    ParameterDTO save(ParameterDTO parameterDTO);

    /**
     * Get all the parameters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ParameterDTO> findAll(Pageable pageable);

    /**
     * Get all the parameters with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ParameterDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" parameter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParameterDTO> findOne(Long id);

    /**
     * Delete the "id" parameter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
