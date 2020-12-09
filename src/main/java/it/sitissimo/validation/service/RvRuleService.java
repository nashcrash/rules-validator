package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.RvRuleDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.RvRule}.
 */
public interface RvRuleService {
    /**
     * Save a rvRule.
     *
     * @param rvRuleDTO the entity to save.
     * @return the persisted entity.
     */
    RvRuleDTO save(RvRuleDTO rvRuleDTO);

    /**
     * Get all the rvRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RvRuleDTO> findAll(Pageable pageable);

    /**
     * Get all the rvRules with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<RvRuleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * @param groupId
     * @return
     */
    List<RvRuleDTO> findAll(Long groupId);

    /**
     * Get the "id" rvRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RvRuleDTO> findOne(Long id);

    /**
     * Get the "ruleCode" rvRule.
     *
     * @param ruleCode the ruleCode of the entity.
     * @return the entity.
     */
    Optional<RvRuleDTO> findOne(String ruleCode);

    /**
     * Delete the "id" rvRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
