package org.example.meteorites.microscope.web.rest;

import org.example.meteorites.microscope.MeteoritesMicroscopeApp;
import org.example.meteorites.microscope.domain.Microscope;
import org.example.meteorites.microscope.repository.MicroscopeRepository;

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
 * Integration tests for the {@link MicroscopeResource} REST controller.
 */
@SpringBootTest(classes = MeteoritesMicroscopeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MicroscopeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MicroscopeRepository microscopeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMicroscopeMockMvc;

    private Microscope microscope;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Microscope createEntity(EntityManager em) {
        Microscope microscope = new Microscope()
            .name(DEFAULT_NAME);
        return microscope;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Microscope createUpdatedEntity(EntityManager em) {
        Microscope microscope = new Microscope()
            .name(UPDATED_NAME);
        return microscope;
    }

    @BeforeEach
    public void initTest() {
        microscope = createEntity(em);
    }

    @Test
    @Transactional
    public void createMicroscope() throws Exception {
        int databaseSizeBeforeCreate = microscopeRepository.findAll().size();
        // Create the Microscope
        restMicroscopeMockMvc.perform(post("/api/microscopes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microscope)))
            .andExpect(status().isCreated());

        // Validate the Microscope in the database
        List<Microscope> microscopeList = microscopeRepository.findAll();
        assertThat(microscopeList).hasSize(databaseSizeBeforeCreate + 1);
        Microscope testMicroscope = microscopeList.get(microscopeList.size() - 1);
        assertThat(testMicroscope.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createMicroscopeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = microscopeRepository.findAll().size();

        // Create the Microscope with an existing ID
        microscope.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMicroscopeMockMvc.perform(post("/api/microscopes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microscope)))
            .andExpect(status().isBadRequest());

        // Validate the Microscope in the database
        List<Microscope> microscopeList = microscopeRepository.findAll();
        assertThat(microscopeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMicroscopes() throws Exception {
        // Initialize the database
        microscopeRepository.saveAndFlush(microscope);

        // Get all the microscopeList
        restMicroscopeMockMvc.perform(get("/api/microscopes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(microscope.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMicroscope() throws Exception {
        // Initialize the database
        microscopeRepository.saveAndFlush(microscope);

        // Get the microscope
        restMicroscopeMockMvc.perform(get("/api/microscopes/{id}", microscope.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(microscope.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingMicroscope() throws Exception {
        // Get the microscope
        restMicroscopeMockMvc.perform(get("/api/microscopes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMicroscope() throws Exception {
        // Initialize the database
        microscopeRepository.saveAndFlush(microscope);

        int databaseSizeBeforeUpdate = microscopeRepository.findAll().size();

        // Update the microscope
        Microscope updatedMicroscope = microscopeRepository.findById(microscope.getId()).get();
        // Disconnect from session so that the updates on updatedMicroscope are not directly saved in db
        em.detach(updatedMicroscope);
        updatedMicroscope
            .name(UPDATED_NAME);

        restMicroscopeMockMvc.perform(put("/api/microscopes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMicroscope)))
            .andExpect(status().isOk());

        // Validate the Microscope in the database
        List<Microscope> microscopeList = microscopeRepository.findAll();
        assertThat(microscopeList).hasSize(databaseSizeBeforeUpdate);
        Microscope testMicroscope = microscopeList.get(microscopeList.size() - 1);
        assertThat(testMicroscope.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMicroscope() throws Exception {
        int databaseSizeBeforeUpdate = microscopeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMicroscopeMockMvc.perform(put("/api/microscopes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microscope)))
            .andExpect(status().isBadRequest());

        // Validate the Microscope in the database
        List<Microscope> microscopeList = microscopeRepository.findAll();
        assertThat(microscopeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMicroscope() throws Exception {
        // Initialize the database
        microscopeRepository.saveAndFlush(microscope);

        int databaseSizeBeforeDelete = microscopeRepository.findAll().size();

        // Delete the microscope
        restMicroscopeMockMvc.perform(delete("/api/microscopes/{id}", microscope.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Microscope> microscopeList = microscopeRepository.findAll();
        assertThat(microscopeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
