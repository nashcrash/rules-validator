package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.Rule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Rule entity.
 */
@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    @Query(
        value = "select distinct rule from Rule rule left join fetch rule.parameters",
        countQuery = "select count(distinct rule) from Rule rule"
    )
    Page<Rule> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct rule from Rule rule left join fetch rule.parameters")
    List<Rule> findAllWithEagerRelationships();

    @Query("select rule from Rule rule left join fetch rule.parameters where rule.id =:id")
    Optional<Rule> findOneWithEagerRelationships(@Param("id") Long id);
}
