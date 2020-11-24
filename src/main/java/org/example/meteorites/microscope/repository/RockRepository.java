package org.example.meteorites.microscope.repository;

import org.example.meteorites.microscope.domain.Rock;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Rock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RockRepository extends JpaRepository<Rock, Long> {
}
