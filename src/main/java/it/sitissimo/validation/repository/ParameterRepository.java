package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.Parameter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Parameter entity.
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query(value = "select distinct parameter from Parameter parameter left join fetch parameter.converters",
        countQuery = "select count(distinct parameter) from Parameter parameter")
    Page<Parameter> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct parameter from Parameter parameter left join fetch parameter.converters")
    List<Parameter> findAllWithEagerRelationships();

    @Query("select parameter from Parameter parameter left join fetch parameter.converters where parameter.id =:id")
    Optional<Parameter> findOneWithEagerRelationships(@Param("id") Long id);
}
