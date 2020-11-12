package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.RvParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RvParam entity.
 */
@Repository
public interface RvParamRepository extends JpaRepository<RvParam, Long> {

    @Query(value = "select distinct rvParam from RvParam rvParam left join fetch rvParam.rvConverters",
        countQuery = "select count(distinct rvParam) from RvParam rvParam")
    Page<RvParam> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct rvParam from RvParam rvParam left join fetch rvParam.rvConverters")
    List<RvParam> findAllWithEagerRelationships();

    @Query("select rvParam from RvParam rvParam left join fetch rvParam.rvConverters where rvParam.id =:id")
    Optional<RvParam> findOneWithEagerRelationships(@Param("id") Long id);
}
