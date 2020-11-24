package org.example.meteorites.microscope.repository;

import org.example.meteorites.microscope.domain.MicroscopePicture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MicroscopePicture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MicroscopePictureRepository extends JpaRepository<MicroscopePicture, Long> {
}
