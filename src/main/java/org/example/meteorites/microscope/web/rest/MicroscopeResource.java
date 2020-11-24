package org.example.meteorites.microscope.web.rest;

import org.example.meteorites.microscope.domain.Microscope;
import org.example.meteorites.microscope.repository.MicroscopeRepository;
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
 * REST controller for managing {@link org.example.meteorites.microscope.domain.Microscope}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MicroscopeResource {

    private final Logger log = LoggerFactory.getLogger(MicroscopeResource.class);

    private static final String ENTITY_NAME = "microscope";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MicroscopeRepository microscopeRepository;

    public MicroscopeResource(MicroscopeRepository microscopeRepository) {
        this.microscopeRepository = microscopeRepository;
    }

    /**
     * {@code POST  /microscopes} : Create a new microscope.
     *
     * @param microscope the microscope to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new microscope, or with status {@code 400 (Bad Request)} if the microscope has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/microscopes")
    public ResponseEntity<Microscope> createMicroscope(@RequestBody Microscope microscope) throws URISyntaxException {
        log.debug("REST request to save Microscope : {}", microscope);
        if (microscope.getId() != null) {
            throw new BadRequestAlertException("A new microscope cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Microscope result = microscopeRepository.save(microscope);
        return ResponseEntity.created(new URI("/api/microscopes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /microscopes} : Updates an existing microscope.
     *
     * @param microscope the microscope to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated microscope,
     * or with status {@code 400 (Bad Request)} if the microscope is not valid,
     * or with status {@code 500 (Internal Server Error)} if the microscope couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/microscopes")
    public ResponseEntity<Microscope> updateMicroscope(@RequestBody Microscope microscope) throws URISyntaxException {
        log.debug("REST request to update Microscope : {}", microscope);
        if (microscope.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Microscope result = microscopeRepository.save(microscope);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, microscope.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /microscopes} : get all the microscopes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of microscopes in body.
     */
    @GetMapping("/microscopes")
    public ResponseEntity<List<Microscope>> getAllMicroscopes(Pageable pageable) {
        log.debug("REST request to get a page of Microscopes");
        Page<Microscope> page = microscopeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /microscopes/:id} : get the "id" microscope.
     *
     * @param id the id of the microscope to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the microscope, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/microscopes/{id}")
    public ResponseEntity<Microscope> getMicroscope(@PathVariable Long id) {
        log.debug("REST request to get Microscope : {}", id);
        Optional<Microscope> microscope = microscopeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(microscope);
    }

    /**
     * {@code DELETE  /microscopes/:id} : delete the "id" microscope.
     *
     * @param id the id of the microscope to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/microscopes/{id}")
    public ResponseEntity<Void> deleteMicroscope(@PathVariable Long id) {
        log.debug("REST request to delete Microscope : {}", id);
        microscopeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
