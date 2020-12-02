package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.RvRuleGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RvRuleGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvRuleGroupRepository extends JpaRepository<RvRuleGroup, Long> {
}
