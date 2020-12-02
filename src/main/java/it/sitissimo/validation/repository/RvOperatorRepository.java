package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.RvOperator;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RvOperator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvOperatorRepository extends JpaRepository<RvOperator, Long> {
}
