package org.example.meteorites.microscope.web.rest;

import org.example.meteorites.microscope.domain.MicroscopePicture;
import org.example.meteorites.microscope.repository.MicroscopePictureRepository;
import org.example.meteorites.microscope.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.example.meteorites.microscope.domain.MicroscopePicture}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MicroscopePictureResource {

    private final Logger log = LoggerFactory.getLogger(MicroscopePictureResource.class);

    private static final String ENTITY_NAME = "microscopePicture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MicroscopePictureRepository microscopePictureRepository;

    public MicroscopePictureResource(MicroscopePictureRepository microscopePictureRepository) {
        this.microscopePictureRepository = microscopePictureRepository;
    }

    /**
     * {@code POST  /microscope-pictures} : Create a new microscopePicture.
     *
     * @param microscopePicture the microscopePicture to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new microscopePicture, or with status {@code 400 (Bad Request)} if the microscopePicture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/microscope-pictures")
    public ResponseEntity<MicroscopePicture> createMicroscopePicture(@RequestBody MicroscopePicture microscopePicture) throws URISyntaxException {
        log.debug("REST request to save MicroscopePicture : {}", microscopePicture);
        if (microscopePicture.getId() != null) {
            throw new BadRequestAlertException("A new microscopePicture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MicroscopePicture result = microscopePictureRepository.save(microscopePicture);
        return ResponseEntity.created(new URI("/api/microscope-pictures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /microscope-pictures} : Updates an existing microscopePicture.
     *
     * @param microscopePicture the microscopePicture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated microscopePicture,
     * or with status {@code 400 (Bad Request)} if the microscopePicture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the microscopePicture couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/microscope-pictures")
    public ResponseEntity<MicroscopePicture> updateMicroscopePicture(@RequestBody MicroscopePicture microscopePicture) throws URISyntaxException {
        log.debug("REST request to update MicroscopePicture : {}", microscopePicture);
        if (microscopePicture.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MicroscopePicture result = microscopePictureRepository.save(microscopePicture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, microscopePicture.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /microscope-pictures} : get all the microscopePictures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of microscopePictures in body.
     */
    @GetMapping("/microscope-pictures")
    public ResponseEntity<List<MicroscopePicture>> getAllMicroscopePictures(Pageable pageable) {
        log.debug("REST request to get a page of MicroscopePictures");
        Page<MicroscopePicture> page = microscopePictureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /microscope-pictures/:id} : get the "id" microscopePicture.
     *
     * @param id the id of the microscopePicture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the microscopePicture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/microscope-pictures/{id}")
    public ResponseEntity<MicroscopePicture> getMicroscopePicture(@PathVariable Long id) {
        log.debug("REST request to get MicroscopePicture : {}", id);
        Optional<MicroscopePicture> microscopePicture = microscopePictureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(microscopePicture);
    }

    /**
     * {@code DELETE  /microscope-pictures/:id} : delete the "id" microscopePicture.
     *
     * @param id the id of the microscopePicture to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/microscope-pictures/{id}")
    public ResponseEntity<Void> deleteMicroscopePicture(@PathVariable Long id) {
        log.debug("REST request to delete MicroscopePicture : {}", id);
        microscopePictureRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
