package fr.sparks.fx.kanban.web.rest;

import fr.sparks.fx.kanban.KanBanApp;
import fr.sparks.fx.kanban.domain.Tache;
import fr.sparks.fx.kanban.repository.TacheRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TacheResource} REST controller.
 */
@SpringBootTest(classes = KanBanApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TacheResourceIT {

    private static final String DEFAULT_INTITULE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_CREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_CREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_NB_HEURES_ESTIMEES = 1L;
    private static final Long UPDATED_NB_HEURES_ESTIMEES = 2L;

    private static final Long DEFAULT_NB_HEURES_REELLES = 1L;
    private static final Long UPDATED_NB_HEURES_REELLES = 2L;

    @Autowired
    private TacheRepository tacheRepository;

    @Mock
    private TacheRepository tacheRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTacheMockMvc;

    private Tache tache;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tache createEntity(EntityManager em) {
        Tache tache = new Tache()
            .intitule(DEFAULT_INTITULE)
            .dtCreation(DEFAULT_DT_CREATION)
            .nbHeuresEstimees(DEFAULT_NB_HEURES_ESTIMEES)
            .nbHeuresReelles(DEFAULT_NB_HEURES_REELLES);
        return tache;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tache createUpdatedEntity(EntityManager em) {
        Tache tache = new Tache()
            .intitule(UPDATED_INTITULE)
            .dtCreation(UPDATED_DT_CREATION)
            .nbHeuresEstimees(UPDATED_NB_HEURES_ESTIMEES)
            .nbHeuresReelles(UPDATED_NB_HEURES_REELLES);
        return tache;
    }

    @BeforeEach
    public void initTest() {
        tache = createEntity(em);
    }

    @Test
    @Transactional
    public void createTache() throws Exception {
        int databaseSizeBeforeCreate = tacheRepository.findAll().size();
        // Create the Tache
        restTacheMockMvc.perform(post("/api/taches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isCreated());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate + 1);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getIntitule()).isEqualTo(DEFAULT_INTITULE);
        assertThat(testTache.getDtCreation()).isEqualTo(DEFAULT_DT_CREATION);
        assertThat(testTache.getNbHeuresEstimees()).isEqualTo(DEFAULT_NB_HEURES_ESTIMEES);
        assertThat(testTache.getNbHeuresReelles()).isEqualTo(DEFAULT_NB_HEURES_REELLES);
    }

    @Test
    @Transactional
    public void createTacheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tacheRepository.findAll().size();

        // Create the Tache with an existing ID
        tache.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTacheMockMvc.perform(post("/api/taches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaches() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get all the tacheList
        restTacheMockMvc.perform(get("/api/taches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tache.getId().intValue())))
            .andExpect(jsonPath("$.[*].intitule").value(hasItem(DEFAULT_INTITULE)))
            .andExpect(jsonPath("$.[*].dtCreation").value(hasItem(DEFAULT_DT_CREATION.toString())))
            .andExpect(jsonPath("$.[*].nbHeuresEstimees").value(hasItem(DEFAULT_NB_HEURES_ESTIMEES.intValue())))
            .andExpect(jsonPath("$.[*].nbHeuresReelles").value(hasItem(DEFAULT_NB_HEURES_REELLES.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTachesWithEagerRelationshipsIsEnabled() throws Exception {
        when(tacheRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTacheMockMvc.perform(get("/api/taches?eagerload=true"))
            .andExpect(status().isOk());

        verify(tacheRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTachesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tacheRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTacheMockMvc.perform(get("/api/taches?eagerload=true"))
            .andExpect(status().isOk());

        verify(tacheRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get the tache
        restTacheMockMvc.perform(get("/api/taches/{id}", tache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tache.getId().intValue()))
            .andExpect(jsonPath("$.intitule").value(DEFAULT_INTITULE))
            .andExpect(jsonPath("$.dtCreation").value(DEFAULT_DT_CREATION.toString()))
            .andExpect(jsonPath("$.nbHeuresEstimees").value(DEFAULT_NB_HEURES_ESTIMEES.intValue()))
            .andExpect(jsonPath("$.nbHeuresReelles").value(DEFAULT_NB_HEURES_REELLES.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTache() throws Exception {
        // Get the tache
        restTacheMockMvc.perform(get("/api/taches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Update the tache
        Tache updatedTache = tacheRepository.findById(tache.getId()).get();
        // Disconnect from session so that the updates on updatedTache are not directly saved in db
        em.detach(updatedTache);
        updatedTache
            .intitule(UPDATED_INTITULE)
            .dtCreation(UPDATED_DT_CREATION)
            .nbHeuresEstimees(UPDATED_NB_HEURES_ESTIMEES)
            .nbHeuresReelles(UPDATED_NB_HEURES_REELLES);

        restTacheMockMvc.perform(put("/api/taches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTache)))
            .andExpect(status().isOk());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getIntitule()).isEqualTo(UPDATED_INTITULE);
        assertThat(testTache.getDtCreation()).isEqualTo(UPDATED_DT_CREATION);
        assertThat(testTache.getNbHeuresEstimees()).isEqualTo(UPDATED_NB_HEURES_ESTIMEES);
        assertThat(testTache.getNbHeuresReelles()).isEqualTo(UPDATED_NB_HEURES_REELLES);
    }

    @Test
    @Transactional
    public void updateNonExistingTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTacheMockMvc.perform(put("/api/taches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tache)))
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        int databaseSizeBeforeDelete = tacheRepository.findAll().size();

        // Delete the tache
        restTacheMockMvc.perform(delete("/api/taches/{id}", tache.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
