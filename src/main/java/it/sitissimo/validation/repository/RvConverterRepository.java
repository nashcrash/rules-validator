package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.RvConverter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RvConverter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RvConverterRepository extends JpaRepository<RvConverter, Long> {
}
