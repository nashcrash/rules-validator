package it.sitissimo.validation.repository;

import it.sitissimo.validation.domain.Converter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Converter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConverterRepository extends JpaRepository<Converter, Long> {}
