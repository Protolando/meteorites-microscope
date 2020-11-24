package org.example.meteorites.microscope.repository;

import org.example.meteorites.microscope.domain.Microscope;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Microscope entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MicroscopeRepository extends JpaRepository<Microscope, Long> {
}
