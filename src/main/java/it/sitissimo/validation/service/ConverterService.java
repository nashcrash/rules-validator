package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.ConverterDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.Converter}.
 */
public interface ConverterService {

    /**
     * Save a converter.
     *
     * @param converterDTO the entity to save.
     * @return the persisted entity.
     */
    ConverterDTO save(ConverterDTO converterDTO);

    /**
     * Get all the converters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConverterDTO> findAll(Pageable pageable);


    /**
     * Get the "id" converter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConverterDTO> findOne(Long id);

    /**
     * Delete the "id" converter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
