package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.BadgeType;
import com.game.biz.rest.ResultatResource;
import com.game.biz.service.ResultatService;
import com.game.repository.biz.ResultatRepository;
import com.game.service.UserService;
import com.game.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.game.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@Link ResultatResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class ResultatResourceIT {

    private static final Long DEFAULT_USER_ID = 1l;
    private static final Long UPDATED_USER_ID = 2l;

    private static final BadgeType DEFAULT_CATEGORIE = BadgeType.R2;
    private static final BadgeType UPDATED_CATEGORIE = BadgeType.PROP;

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final Integer DEFAULT_NB_BADGES = 1;
    private static final Integer UPDATED_NB_BADGES = 2;

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private ResultatService resultatService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restResultatMockMvc;

    private Resultat resultat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultatResource resultatResource = new ResultatResource(resultatService, userService);
        this.restResultatMockMvc = MockMvcBuilders.standaloneSetup(resultatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultat createEntity(EntityManager em) {
        Resultat resultat = new Resultat()
            .userId(DEFAULT_USER_ID)
            .categorie(DEFAULT_CATEGORIE)
            .points(DEFAULT_POINTS)
            .nbBadges(DEFAULT_NB_BADGES);
        return resultat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultat createUpdatedEntity(EntityManager em) {
        return new Resultat()
            .userId(UPDATED_USER_ID)
            .categorie(UPDATED_CATEGORIE)
            .points(UPDATED_POINTS)
            .nbBadges(UPDATED_NB_BADGES);
    }

    @BeforeEach
    public void initTest() {
        resultat = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultat() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();

        // Create the Resultat
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultat)))
            .andExpect(status().isCreated());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate + 1);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testResultat.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testResultat.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testResultat.getNbBadges()).isEqualTo(DEFAULT_NB_BADGES);
    }

    @Test
    @Transactional
    public void createResultatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();

        // Create the Resultat with an existing ID
        resultat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultat)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResultats() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
            .andExpect(jsonPath("$.[*].nbBadges").value(hasItem(DEFAULT_NB_BADGES)));
    }

    @Test
    @Transactional
    public void getResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", resultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultat.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.doubleValue()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.nbBadges").value(DEFAULT_NB_BADGES));
    }

    @Test
    @Transactional
    public void getNonExistingResultat() throws Exception {
        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultat() throws Exception {
        // Initialize the database
        resultatService.save(resultat);

        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Update the resultat
        Resultat updatedResultat = resultatRepository.findById(resultat.getId()).get();
        // Disconnect from session so that the updates on updatedResultat are not directly saved in db
        em.detach(updatedResultat);
        updatedResultat
            .userId(UPDATED_USER_ID)
            .categorie(UPDATED_CATEGORIE)
            .points(UPDATED_POINTS)
            .nbBadges(UPDATED_NB_BADGES);

        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultat)))
            .andExpect(status().isOk());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testResultat.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testResultat.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testResultat.getNbBadges()).isEqualTo(UPDATED_NB_BADGES);
    }

    @Test
    @Transactional
    public void updateNonExistingResultat() throws Exception {
        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Create the Resultat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultat)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultat() throws Exception {
        // Initialize the database
        resultatService.save(resultat);

        int databaseSizeBeforeDelete = resultatRepository.findAll().size();

        // Delete the resultat
        restResultatMockMvc.perform(delete("/api/resultats/{id}", resultat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resultat.class);
        Resultat resultat1 = new Resultat();
        resultat1.setId(1L);
        Resultat resultat2 = new Resultat();
        resultat2.setId(resultat1.getId());
        assertThat(resultat1).isEqualTo(resultat2);
        resultat2.setId(2L);
        assertThat(resultat1).isNotEqualTo(resultat2);
        resultat1.setId(null);
        assertThat(resultat1).isNotEqualTo(resultat2);
    }
}
