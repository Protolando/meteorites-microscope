package org.example.meteorites.microscope.web.rest;

import org.example.meteorites.microscope.domain.Rock;
import org.example.meteorites.microscope.repository.RockRepository;
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
 * REST controller for managing {@link org.example.meteorites.microscope.domain.Rock}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RockResource {

    private final Logger log = LoggerFactory.getLogger(RockResource.class);

    private static final String ENTITY_NAME = "rock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RockRepository rockRepository;

    public RockResource(RockRepository rockRepository) {
        this.rockRepository = rockRepository;
    }

    /**
     * {@code POST  /rocks} : Create a new rock.
     *
     * @param rock the rock to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rock, or with status {@code 400 (Bad Request)} if the rock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rocks")
    public ResponseEntity<Rock> createRock(@RequestBody Rock rock) throws URISyntaxException {
        log.debug("REST request to save Rock : {}", rock);
        if (rock.getId() != null) {
            throw new BadRequestAlertException("A new rock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rock result = rockRepository.save(rock);
        return ResponseEntity.created(new URI("/api/rocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rocks} : Updates an existing rock.
     *
     * @param rock the rock to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rock,
     * or with status {@code 400 (Bad Request)} if the rock is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rock couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rocks")
    public ResponseEntity<Rock> updateRock(@RequestBody Rock rock) throws URISyntaxException {
        log.debug("REST request to update Rock : {}", rock);
        if (rock.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rock result = rockRepository.save(rock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rock.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rocks} : get all the rocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rocks in body.
     */
    @GetMapping("/rocks")
    public ResponseEntity<List<Rock>> getAllRocks(Pageable pageable) {
        log.debug("REST request to get a page of Rocks");
        Page<Rock> page = rockRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rocks/:id} : get the "id" rock.
     *
     * @param id the id of the rock to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rock, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rocks/{id}")
    public ResponseEntity<Rock> getRock(@PathVariable Long id) {
        log.debug("REST request to get Rock : {}", id);
        Optional<Rock> rock = rockRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rock);
    }

    /**
     * {@code DELETE  /rocks/:id} : delete the "id" rock.
     *
     * @param id the id of the rock to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rocks/{id}")
    public ResponseEntity<Void> deleteRock(@PathVariable Long id) {
        log.debug("REST request to delete Rock : {}", id);
        rockRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
