package org.example.meteorites.microscope.web.rest;

import org.example.meteorites.microscope.MeteoritesMicroscopeApp;
import org.example.meteorites.microscope.domain.MicroscopePicture;
import org.example.meteorites.microscope.repository.MicroscopePictureRepository;

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
 * Integration tests for the {@link MicroscopePictureResource} REST controller.
 */
@SpringBootTest(classes = MeteoritesMicroscopeApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MicroscopePictureResourceIT {

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private MicroscopePictureRepository microscopePictureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMicroscopePictureMockMvc;

    private MicroscopePicture microscopePicture;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MicroscopePicture createEntity(EntityManager em) {
        MicroscopePicture microscopePicture = new MicroscopePicture()
            .path(DEFAULT_PATH)
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC)
            .order(DEFAULT_ORDER);
        return microscopePicture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MicroscopePicture createUpdatedEntity(EntityManager em) {
        MicroscopePicture microscopePicture = new MicroscopePicture()
            .path(UPDATED_PATH)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .order(UPDATED_ORDER);
        return microscopePicture;
    }

    @BeforeEach
    public void initTest() {
        microscopePicture = createEntity(em);
    }

    @Test
    @Transactional
    public void createMicroscopePicture() throws Exception {
        int databaseSizeBeforeCreate = microscopePictureRepository.findAll().size();
        // Create the MicroscopePicture
        restMicroscopePictureMockMvc.perform(post("/api/microscope-pictures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microscopePicture)))
            .andExpect(status().isCreated());

        // Validate the MicroscopePicture in the database
        List<MicroscopePicture> microscopePictureList = microscopePictureRepository.findAll();
        assertThat(microscopePictureList).hasSize(databaseSizeBeforeCreate + 1);
        MicroscopePicture testMicroscopePicture = microscopePictureList.get(microscopePictureList.size() - 1);
        assertThat(testMicroscopePicture.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testMicroscopePicture.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMicroscopePicture.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testMicroscopePicture.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createMicroscopePictureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = microscopePictureRepository.findAll().size();

        // Create the MicroscopePicture with an existing ID
        microscopePicture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMicroscopePictureMockMvc.perform(post("/api/microscope-pictures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microscopePicture)))
            .andExpect(status().isBadRequest());

        // Validate the MicroscopePicture in the database
        List<MicroscopePicture> microscopePictureList = microscopePictureRepository.findAll();
        assertThat(microscopePictureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMicroscopePictures() throws Exception {
        // Initialize the database
        microscopePictureRepository.saveAndFlush(microscopePicture);

        // Get all the microscopePictureList
        restMicroscopePictureMockMvc.perform(get("/api/microscope-pictures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(microscopePicture.getId().intValue())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getMicroscopePicture() throws Exception {
        // Initialize the database
        microscopePictureRepository.saveAndFlush(microscopePicture);

        // Get the microscopePicture
        restMicroscopePictureMockMvc.perform(get("/api/microscope-pictures/{id}", microscopePicture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(microscopePicture.getId().intValue()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }
    @Test
    @Transactional
    public void getNonExistingMicroscopePicture() throws Exception {
        // Get the microscopePicture
        restMicroscopePictureMockMvc.perform(get("/api/microscope-pictures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMicroscopePicture() throws Exception {
        // Initialize the database
        microscopePictureRepository.saveAndFlush(microscopePicture);

        int databaseSizeBeforeUpdate = microscopePictureRepository.findAll().size();

        // Update the microscopePicture
        MicroscopePicture updatedMicroscopePicture = microscopePictureRepository.findById(microscopePicture.getId()).get();
        // Disconnect from session so that the updates on updatedMicroscopePicture are not directly saved in db
        em.detach(updatedMicroscopePicture);
        updatedMicroscopePicture
            .path(UPDATED_PATH)
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC)
            .order(UPDATED_ORDER);

        restMicroscopePictureMockMvc.perform(put("/api/microscope-pictures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMicroscopePicture)))
            .andExpect(status().isOk());

        // Validate the MicroscopePicture in the database
        List<MicroscopePicture> microscopePictureList = microscopePictureRepository.findAll();
        assertThat(microscopePictureList).hasSize(databaseSizeBeforeUpdate);
        MicroscopePicture testMicroscopePicture = microscopePictureList.get(microscopePictureList.size() - 1);
        assertThat(testMicroscopePicture.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testMicroscopePicture.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMicroscopePicture.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testMicroscopePicture.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingMicroscopePicture() throws Exception {
        int databaseSizeBeforeUpdate = microscopePictureRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMicroscopePictureMockMvc.perform(put("/api/microscope-pictures")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(microscopePicture)))
            .andExpect(status().isBadRequest());

        // Validate the MicroscopePicture in the database
        List<MicroscopePicture> microscopePictureList = microscopePictureRepository.findAll();
        assertThat(microscopePictureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMicroscopePicture() throws Exception {
        // Initialize the database
        microscopePictureRepository.saveAndFlush(microscopePicture);

        int databaseSizeBeforeDelete = microscopePictureRepository.findAll().size();

        // Delete the microscopePicture
        restMicroscopePictureMockMvc.perform(delete("/api/microscope-pictures/{id}", microscopePicture.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MicroscopePicture> microscopePictureList = microscopePictureRepository.findAll();
        assertThat(microscopePictureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
