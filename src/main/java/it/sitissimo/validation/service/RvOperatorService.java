package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.RvOperatorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.RvOperator}.
 */
public interface RvOperatorService {

    /**
     * Save a rvOperator.
     *
     * @param rvOperatorDTO the entity to save.
     * @return the persisted entity.
     */
    RvOperatorDTO save(RvOperatorDTO rvOperatorDTO);

    /**
     * Get all the rvOperators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RvOperatorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rvOperator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RvOperatorDTO> findOne(Long id);

    /**
     * Delete the "id" rvOperator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
