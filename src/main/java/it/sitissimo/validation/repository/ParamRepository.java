package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Param entity.
 */
@Repository
public interface ParamRepository extends JpaRepository<Param, Long> {

    @Query(value = "select distinct param from Param param left join fetch param.converters",
        countQuery = "select count(distinct param) from Param param")
    Page<Param> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct param from Param param left join fetch param.converters")
    List<Param> findAllWithEagerRelationships();

    @Query("select param from Param param left join fetch param.converters where param.id =:id")
    Optional<Param> findOneWithEagerRelationships(@Param("id") Long id);
}
