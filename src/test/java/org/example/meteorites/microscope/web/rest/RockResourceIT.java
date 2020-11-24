package org.example.meteorites.microscope.web.rest;

import org.example.meteorites.microscope.MeteoritesMicroscopeApp;
import org.example.meteorites.microscope.domain.Rock;
import org.example.meteorites.microscope.repository.RockRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RockResource} REST controller.
 */
@SpringBootTest(classes = MeteoritesMicroscopeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RockResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_PICTURE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PICTURE_PATH = "BBBBBBBBBB";

    @Autowired
    private RockRepository rockRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRockMockMvc;

    private Rock rock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rock createEntity(EntityManager em) {
        Rock rock = new Rock()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .picturePath(DEFAULT_PICTURE_PATH);
        return rock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rock createUpdatedEntity(EntityManager em) {
        Rock rock = new Rock()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .picturePath(UPDATED_PICTURE_PATH);
        return rock;
    }

    @BeforeEach
    public void initTest() {
        rock = createEntity(em);
    }

    @Test
    @Transactional
    public void createRock() throws Exception {
        int databaseSizeBeforeCreate = rockRepository.findAll().size();
        // Create the Rock
        restRockMockMvc.perform(post("/api/rocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rock)))
            .andExpect(status().isCreated());

        // Validate the Rock in the database
        List<Rock> rockList = rockRepository.findAll();
        assertThat(rockList).hasSize(databaseSizeBeforeCreate + 1);
        Rock testRock = rockList.get(rockList.size() - 1);
        assertThat(testRock.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRock.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testRock.getPicturePath()).isEqualTo(DEFAULT_PICTURE_PATH);
    }

    @Test
    @Transactional
    public void createRockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rockRepository.findAll().size();

        // Create the Rock with an existing ID
        rock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRockMockMvc.perform(post("/api/rocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rock)))
            .andExpect(status().isBadRequest());

        // Validate the Rock in the database
        List<Rock> rockList = rockRepository.findAll();
        assertThat(rockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRocks() throws Exception {
        // Initialize the database
        rockRepository.saveAndFlush(rock);

        // Get all the rockList
        restRockMockMvc.perform(get("/api/rocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rock.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].picturePath").value(hasItem(DEFAULT_PICTURE_PATH)));
    }
    
    @Test
    @Transactional
    public void getRock() throws Exception {
        // Initialize the database
        rockRepository.saveAndFlush(rock);

        // Get the rock
        restRockMockMvc.perform(get("/api/rocks/{id}", rock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rock.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.picturePath").value(DEFAULT_PICTURE_PATH));
    }
    @Test
    @Transactional
    public void getNonExistingRock() throws Exception {
        // Get the rock
        restRockMockMvc.perform(get("/api/rocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRock() throws Exception {
        // Initialize the database
        rockRepository.saveAndFlush(rock);

        int databaseSizeBeforeUpdate = rockRepository.findAll().size();

        // Update the rock
        Rock updatedRock = rockRepository.findById(rock.getId()).get();
        // Disconnect from session so that the updates on updatedRock are not directly saved in db
        em.detach(updatedRock);
        updatedRock
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .picturePath(UPDATED_PICTURE_PATH);

        restRockMockMvc.perform(put("/api/rocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRock)))
            .andExpect(status().isOk());

        // Validate the Rock in the database
        List<Rock> rockList = rockRepository.findAll();
        assertThat(rockList).hasSize(databaseSizeBeforeUpdate);
        Rock testRock = rockList.get(rockList.size() - 1);
        assertThat(testRock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRock.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testRock.getPicturePath()).isEqualTo(UPDATED_PICTURE_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingRock() throws Exception {
        int databaseSizeBeforeUpdate = rockRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRockMockMvc.perform(put("/api/rocks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rock)))
            .andExpect(status().isBadRequest());

        // Validate the Rock in the database
        List<Rock> rockList = rockRepository.findAll();
        assertThat(rockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRock() throws Exception {
        // Initialize the database
        rockRepository.saveAndFlush(rock);

        int databaseSizeBeforeDelete = rockRepository.findAll().size();

        // Delete the rock
        restRockMockMvc.perform(delete("/api/rocks/{id}", rock.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rock> rockList = rockRepository.findAll();
        assertThat(rockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
