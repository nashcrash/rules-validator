package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.RvOperatorParam;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RvOperatorParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvOperatorParamRepository extends JpaRepository<RvOperatorParam, Long> {
}
