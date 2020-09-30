package fr.sparks.fx.kanban.web.rest;

import fr.sparks.fx.kanban.KanBanApp;
import fr.sparks.fx.kanban.domain.Developpeur;
import fr.sparks.fx.kanban.repository.DeveloppeurRepository;

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
 * Integration tests for the {@link DeveloppeurResource} REST controller.
 */
@SpringBootTest(classes = KanBanApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeveloppeurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_NB_TACHES_EN_COURS = 1L;
    private static final Long UPDATED_NB_TACHES_EN_COURS = 2L;

    private static final String DEFAULT_NUMERO_CARTE_BLEUE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CARTE_BLEUE = "BBBBBBBBBB";

    @Autowired
    private DeveloppeurRepository developpeurRepository;

    @Mock
    private DeveloppeurRepository developpeurRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeveloppeurMockMvc;

    private Developpeur developpeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developpeur createEntity(EntityManager em) {
        Developpeur developpeur = new Developpeur()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dtNaissance(DEFAULT_DT_NAISSANCE)
            .email(DEFAULT_EMAIL)
            .nbTachesEnCours(DEFAULT_NB_TACHES_EN_COURS)
            .numeroCarteBleue(DEFAULT_NUMERO_CARTE_BLEUE);
        return developpeur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Developpeur createUpdatedEntity(EntityManager em) {
        Developpeur developpeur = new Developpeur()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dtNaissance(UPDATED_DT_NAISSANCE)
            .email(UPDATED_EMAIL)
            .nbTachesEnCours(UPDATED_NB_TACHES_EN_COURS)
            .numeroCarteBleue(UPDATED_NUMERO_CARTE_BLEUE);
        return developpeur;
    }

    @BeforeEach
    public void initTest() {
        developpeur = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeveloppeur() throws Exception {
        int databaseSizeBeforeCreate = developpeurRepository.findAll().size();
        // Create the Developpeur
        restDeveloppeurMockMvc.perform(post("/api/developpeurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developpeur)))
            .andExpect(status().isCreated());

        // Validate the Developpeur in the database
        List<Developpeur> developpeurList = developpeurRepository.findAll();
        assertThat(developpeurList).hasSize(databaseSizeBeforeCreate + 1);
        Developpeur testDeveloppeur = developpeurList.get(developpeurList.size() - 1);
        assertThat(testDeveloppeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDeveloppeur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDeveloppeur.getDtNaissance()).isEqualTo(DEFAULT_DT_NAISSANCE);
        assertThat(testDeveloppeur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDeveloppeur.getNbTachesEnCours()).isEqualTo(DEFAULT_NB_TACHES_EN_COURS);
        assertThat(testDeveloppeur.getNumeroCarteBleue()).isEqualTo(DEFAULT_NUMERO_CARTE_BLEUE);
    }

    @Test
    @Transactional
    public void createDeveloppeurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = developpeurRepository.findAll().size();

        // Create the Developpeur with an existing ID
        developpeur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeveloppeurMockMvc.perform(post("/api/developpeurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developpeur)))
            .andExpect(status().isBadRequest());

        // Validate the Developpeur in the database
        List<Developpeur> developpeurList = developpeurRepository.findAll();
        assertThat(developpeurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = developpeurRepository.findAll().size();
        // set the field null
        developpeur.setNom(null);

        // Create the Developpeur, which fails.


        restDeveloppeurMockMvc.perform(post("/api/developpeurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developpeur)))
            .andExpect(status().isBadRequest());

        List<Developpeur> developpeurList = developpeurRepository.findAll();
        assertThat(developpeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeveloppeurs() throws Exception {
        // Initialize the database
        developpeurRepository.saveAndFlush(developpeur);

        // Get all the developpeurList
        restDeveloppeurMockMvc.perform(get("/api/developpeurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(developpeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].dtNaissance").value(hasItem(DEFAULT_DT_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].nbTachesEnCours").value(hasItem(DEFAULT_NB_TACHES_EN_COURS.intValue())))
            .andExpect(jsonPath("$.[*].numeroCarteBleue").value(hasItem(DEFAULT_NUMERO_CARTE_BLEUE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDeveloppeursWithEagerRelationshipsIsEnabled() throws Exception {
        when(developpeurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeveloppeurMockMvc.perform(get("/api/developpeurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(developpeurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDeveloppeursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(developpeurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDeveloppeurMockMvc.perform(get("/api/developpeurs?eagerload=true"))
            .andExpect(status().isOk());

        verify(developpeurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDeveloppeur() throws Exception {
        // Initialize the database
        developpeurRepository.saveAndFlush(developpeur);

        // Get the developpeur
        restDeveloppeurMockMvc.perform(get("/api/developpeurs/{id}", developpeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(developpeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.dtNaissance").value(DEFAULT_DT_NAISSANCE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.nbTachesEnCours").value(DEFAULT_NB_TACHES_EN_COURS.intValue()))
            .andExpect(jsonPath("$.numeroCarteBleue").value(DEFAULT_NUMERO_CARTE_BLEUE));
    }
    @Test
    @Transactional
    public void getNonExistingDeveloppeur() throws Exception {
        // Get the developpeur
        restDeveloppeurMockMvc.perform(get("/api/developpeurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeveloppeur() throws Exception {
        // Initialize the database
        developpeurRepository.saveAndFlush(developpeur);

        int databaseSizeBeforeUpdate = developpeurRepository.findAll().size();

        // Update the developpeur
        Developpeur updatedDeveloppeur = developpeurRepository.findById(developpeur.getId()).get();
        // Disconnect from session so that the updates on updatedDeveloppeur are not directly saved in db
        em.detach(updatedDeveloppeur);
        updatedDeveloppeur
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dtNaissance(UPDATED_DT_NAISSANCE)
            .email(UPDATED_EMAIL)
            .nbTachesEnCours(UPDATED_NB_TACHES_EN_COURS)
            .numeroCarteBleue(UPDATED_NUMERO_CARTE_BLEUE);

        restDeveloppeurMockMvc.perform(put("/api/developpeurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeveloppeur)))
            .andExpect(status().isOk());

        // Validate the Developpeur in the database
        List<Developpeur> developpeurList = developpeurRepository.findAll();
        assertThat(developpeurList).hasSize(databaseSizeBeforeUpdate);
        Developpeur testDeveloppeur = developpeurList.get(developpeurList.size() - 1);
        assertThat(testDeveloppeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDeveloppeur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDeveloppeur.getDtNaissance()).isEqualTo(UPDATED_DT_NAISSANCE);
        assertThat(testDeveloppeur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDeveloppeur.getNbTachesEnCours()).isEqualTo(UPDATED_NB_TACHES_EN_COURS);
        assertThat(testDeveloppeur.getNumeroCarteBleue()).isEqualTo(UPDATED_NUMERO_CARTE_BLEUE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeveloppeur() throws Exception {
        int databaseSizeBeforeUpdate = developpeurRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeveloppeurMockMvc.perform(put("/api/developpeurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(developpeur)))
            .andExpect(status().isBadRequest());

        // Validate the Developpeur in the database
        List<Developpeur> developpeurList = developpeurRepository.findAll();
        assertThat(developpeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeveloppeur() throws Exception {
        // Initialize the database
        developpeurRepository.saveAndFlush(developpeur);

        int databaseSizeBeforeDelete = developpeurRepository.findAll().size();

        // Delete the developpeur
        restDeveloppeurMockMvc.perform(delete("/api/developpeurs/{id}", developpeur.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Developpeur> developpeurList = developpeurRepository.findAll();
        assertThat(developpeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
