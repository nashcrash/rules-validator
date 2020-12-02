package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.RvConverterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.RvConverter}.
 */
public interface RvConverterService {

    /**
     * Save a rvConverter.
     *
     * @param rvConverterDTO the entity to save.
     * @return the persisted entity.
     */
    RvConverterDTO save(RvConverterDTO rvConverterDTO);

    /**
     * Get all the rvConverters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RvConverterDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rvConverter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RvConverterDTO> findOne(Long id);

    /**
     * Delete the "id" rvConverter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
