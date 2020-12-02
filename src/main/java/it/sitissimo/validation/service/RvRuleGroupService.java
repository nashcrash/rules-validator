package it.sitissimo.validation.service;

import it.sitissimo.validation.service.dto.RvRuleGroupDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link it.sitissimo.validation.domain.RvRuleGroup}.
 */
public interface RvRuleGroupService {
    /**
     * Save a rvRuleGroup.
     *
     * @param rvRuleGroupDTO the entity to save.
     * @return the persisted entity.
     */
    RvRuleGroupDTO save(RvRuleGroupDTO rvRuleGroupDTO);

    /**
     * Get all the rvRuleGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RvRuleGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" rvRuleGroup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RvRuleGroupDTO> findOne(Long id);

    /**
     * Delete the "id" rvRuleGroup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
