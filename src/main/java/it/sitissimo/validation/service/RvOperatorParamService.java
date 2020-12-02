package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.RvOperatorParamDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.RvOperatorParam}.
 */
public interface RvOperatorParamService {

    /**
     * Save a rvOperatorParam.
     *
     * @param rvOperatorParamDTO the entity to save.
     * @return the persisted entity.
     */
    RvOperatorParamDTO save(RvOperatorParamDTO rvOperatorParamDTO);

    /**
     * Get all the rvOperatorParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RvOperatorParamDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rvOperatorParam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RvOperatorParamDTO> findOne(Long id);

    /**
     * Delete the "id" rvOperatorParam.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
