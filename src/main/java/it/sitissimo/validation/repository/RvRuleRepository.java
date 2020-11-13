package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.RvRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RvRule entity.
 */
@Repository
public interface RvRuleRepository extends JpaRepository<RvRule, Long> {

    @Query(value = "select distinct rvRule from RvRule rvRule left join fetch rvRule.rvParams",
        countQuery = "select count(distinct rvRule) from RvRule rvRule")
    Page<RvRule> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct rvRule from RvRule rvRule left join fetch rvRule.rvParams")
    List<RvRule> findAllWithEagerRelationships();

    @Query("select rvRule from RvRule rvRule left join fetch rvRule.rvParams where rvRule.id =:id")
    Optional<RvRule> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select rvRule from RvRule rvRule left join fetch rvRule.rvParams where rvRule.ruleCode =:ruleCode")
    Optional<RvRule> findOneWithEagerRelationships(@Param("ruleCode") String ruleCode);
}
