package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.R2LastWeekReportLine;
import com.game.repository.R2LastWeekReportLineRepository;
import com.game.service.R2LastWeekReportLineService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.game.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link R2LastWeekReportLineResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class R2LastWeekReportLineResourceIT {

    private static final String DEFAULT_NOM_COMPLET = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMPLET = "BBBBBBBBBB";

    private static final String DEFAULT_RV_1 = "AAAAAAAAAA";
    private static final String UPDATED_RV_1 = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DISPO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DISPO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMPETENCE_PRINCIPALE = "AAAAAAAAAA";
    private static final String UPDATED_COMPETENCE_PRINCIPALE = "BBBBBBBBBB";

    private static final Double DEFAULT_EXP = 1D;
    private static final Double UPDATED_EXP = 2D;

    private static final String DEFAULT_TRANCHE_EXP = "AAAAAAAAAA";
    private static final String UPDATED_TRANCHE_EXP = "BBBBBBBBBB";

    private static final String DEFAULT_ECOLE = "AAAAAAAAAA";
    private static final String UPDATED_ECOLE = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSE_ECOLE = "AAAAAAAAAA";
    private static final String UPDATED_CLASSE_ECOLE = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_FONCTIONS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RV_1 = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RV_1 = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RV_2 = "AAAAAAAAAA";
    private static final String UPDATED_RV_2 = "BBBBBBBBBB";

    private static final String DEFAULT_METIERS = "AAAAAAAAAA";
    private static final String UPDATED_METIERS = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINE_PISTE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINE_PISTE = "BBBBBBBBBB";

    @Autowired
    private R2LastWeekReportLineRepository r2LastWeekReportLineRepository;

    @Autowired
    private R2LastWeekReportLineService r2LastWeekReportLineService;

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

    private MockMvc restR2LastWeekReportLineMockMvc;

    private R2LastWeekReportLine r2LastWeekReportLine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final R2LastWeekReportLineResource r2LastWeekReportLineResource = new R2LastWeekReportLineResource(r2LastWeekReportLineService);
        this.restR2LastWeekReportLineMockMvc = MockMvcBuilders.standaloneSetup(r2LastWeekReportLineResource)
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
    public static R2LastWeekReportLine createEntity(EntityManager em) {
        R2LastWeekReportLine r2LastWeekReportLine = new R2LastWeekReportLine()
            .nomComplet(DEFAULT_NOM_COMPLET)
            .rv1(DEFAULT_RV_1)
            .dateDispo(DEFAULT_DATE_DISPO)
            .competencePrincipale(DEFAULT_COMPETENCE_PRINCIPALE)
            .exp(DEFAULT_EXP)
            .trancheExp(DEFAULT_TRANCHE_EXP)
            .ecole(DEFAULT_ECOLE)
            .classeEcole(DEFAULT_CLASSE_ECOLE)
            .fonctions(DEFAULT_FONCTIONS)
            .dateRV1(DEFAULT_DATE_RV_1)
            .rv2(DEFAULT_RV_2)
            .metiers(DEFAULT_METIERS)
            .originePiste(DEFAULT_ORIGINE_PISTE);
        return r2LastWeekReportLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static R2LastWeekReportLine createUpdatedEntity(EntityManager em) {
        R2LastWeekReportLine r2LastWeekReportLine = new R2LastWeekReportLine()
            .nomComplet(UPDATED_NOM_COMPLET)
            .rv1(UPDATED_RV_1)
            .dateDispo(UPDATED_DATE_DISPO)
            .competencePrincipale(UPDATED_COMPETENCE_PRINCIPALE)
            .exp(UPDATED_EXP)
            .trancheExp(UPDATED_TRANCHE_EXP)
            .ecole(UPDATED_ECOLE)
            .classeEcole(UPDATED_CLASSE_ECOLE)
            .fonctions(UPDATED_FONCTIONS)
            .dateRV1(UPDATED_DATE_RV_1)
            .rv2(UPDATED_RV_2)
            .metiers(UPDATED_METIERS)
            .originePiste(UPDATED_ORIGINE_PISTE);
        return r2LastWeekReportLine;
    }

    @BeforeEach
    public void initTest() {
        r2LastWeekReportLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createR2LastWeekReportLine() throws Exception {
        int databaseSizeBeforeCreate = r2LastWeekReportLineRepository.findAll().size();

        // Create the R2LastWeekReportLine
        restR2LastWeekReportLineMockMvc.perform(post("/api/r-2-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(r2LastWeekReportLine)))
            .andExpect(status().isCreated());

        // Validate the R2LastWeekReportLine in the database
        List<R2LastWeekReportLine> r2LastWeekReportLineList = r2LastWeekReportLineRepository.findAll();
        assertThat(r2LastWeekReportLineList).hasSize(databaseSizeBeforeCreate + 1);
        R2LastWeekReportLine testR2LastWeekReportLine = r2LastWeekReportLineList.get(r2LastWeekReportLineList.size() - 1);
        assertThat(testR2LastWeekReportLine.getNomComplet()).isEqualTo(DEFAULT_NOM_COMPLET);
        assertThat(testR2LastWeekReportLine.getRv1()).isEqualTo(DEFAULT_RV_1);
        assertThat(testR2LastWeekReportLine.getDateDispo()).isEqualTo(DEFAULT_DATE_DISPO);
        assertThat(testR2LastWeekReportLine.getCompetencePrincipale()).isEqualTo(DEFAULT_COMPETENCE_PRINCIPALE);
        assertThat(testR2LastWeekReportLine.getExp()).isEqualTo(DEFAULT_EXP);
        assertThat(testR2LastWeekReportLine.getTrancheExp()).isEqualTo(DEFAULT_TRANCHE_EXP);
        assertThat(testR2LastWeekReportLine.getEcole()).isEqualTo(DEFAULT_ECOLE);
        assertThat(testR2LastWeekReportLine.getClasseEcole()).isEqualTo(DEFAULT_CLASSE_ECOLE);
        assertThat(testR2LastWeekReportLine.getFonctions()).isEqualTo(DEFAULT_FONCTIONS);
        assertThat(testR2LastWeekReportLine.getDateRV1()).isEqualTo(DEFAULT_DATE_RV_1);
        assertThat(testR2LastWeekReportLine.getRv2()).isEqualTo(DEFAULT_RV_2);
        assertThat(testR2LastWeekReportLine.getMetiers()).isEqualTo(DEFAULT_METIERS);
        assertThat(testR2LastWeekReportLine.getOriginePiste()).isEqualTo(DEFAULT_ORIGINE_PISTE);
    }

    @Test
    @Transactional
    public void createR2LastWeekReportLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = r2LastWeekReportLineRepository.findAll().size();

        // Create the R2LastWeekReportLine with an existing ID
        r2LastWeekReportLine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restR2LastWeekReportLineMockMvc.perform(post("/api/r-2-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(r2LastWeekReportLine)))
            .andExpect(status().isBadRequest());

        // Validate the R2LastWeekReportLine in the database
        List<R2LastWeekReportLine> r2LastWeekReportLineList = r2LastWeekReportLineRepository.findAll();
        assertThat(r2LastWeekReportLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllR2LastWeekReportLines() throws Exception {
        // Initialize the database
        r2LastWeekReportLineRepository.saveAndFlush(r2LastWeekReportLine);

        // Get all the r2LastWeekReportLineList
        restR2LastWeekReportLineMockMvc.perform(get("/api/r-2-last-week-report-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(r2LastWeekReportLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomComplet").value(hasItem(DEFAULT_NOM_COMPLET.toString())))
            .andExpect(jsonPath("$.[*].rv1").value(hasItem(DEFAULT_RV_1.toString())))
            .andExpect(jsonPath("$.[*].dateDispo").value(hasItem(DEFAULT_DATE_DISPO.toString())))
            .andExpect(jsonPath("$.[*].competencePrincipale").value(hasItem(DEFAULT_COMPETENCE_PRINCIPALE.toString())))
            .andExpect(jsonPath("$.[*].exp").value(hasItem(DEFAULT_EXP.doubleValue())))
            .andExpect(jsonPath("$.[*].trancheExp").value(hasItem(DEFAULT_TRANCHE_EXP.toString())))
            .andExpect(jsonPath("$.[*].ecole").value(hasItem(DEFAULT_ECOLE.toString())))
            .andExpect(jsonPath("$.[*].classeEcole").value(hasItem(DEFAULT_CLASSE_ECOLE.toString())))
            .andExpect(jsonPath("$.[*].fonctions").value(hasItem(DEFAULT_FONCTIONS.toString())))
            .andExpect(jsonPath("$.[*].dateRV1").value(hasItem(DEFAULT_DATE_RV_1.toString())))
            .andExpect(jsonPath("$.[*].rv2").value(hasItem(DEFAULT_RV_2.toString())))
            .andExpect(jsonPath("$.[*].metiers").value(hasItem(DEFAULT_METIERS.toString())))
            .andExpect(jsonPath("$.[*].originePiste").value(hasItem(DEFAULT_ORIGINE_PISTE.toString())));
    }

    @Test
    @Transactional
    public void getR2LastWeekReportLine() throws Exception {
        // Initialize the database
        r2LastWeekReportLineRepository.saveAndFlush(r2LastWeekReportLine);

        // Get the r2LastWeekReportLine
        restR2LastWeekReportLineMockMvc.perform(get("/api/r-2-last-week-report-lines/{id}", r2LastWeekReportLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(r2LastWeekReportLine.getId().intValue()))
            .andExpect(jsonPath("$.nomComplet").value(DEFAULT_NOM_COMPLET.toString()))
            .andExpect(jsonPath("$.rv1").value(DEFAULT_RV_1.toString()))
            .andExpect(jsonPath("$.dateDispo").value(DEFAULT_DATE_DISPO.toString()))
            .andExpect(jsonPath("$.competencePrincipale").value(DEFAULT_COMPETENCE_PRINCIPALE.toString()))
            .andExpect(jsonPath("$.exp").value(DEFAULT_EXP.doubleValue()))
            .andExpect(jsonPath("$.trancheExp").value(DEFAULT_TRANCHE_EXP.toString()))
            .andExpect(jsonPath("$.ecole").value(DEFAULT_ECOLE.toString()))
            .andExpect(jsonPath("$.classeEcole").value(DEFAULT_CLASSE_ECOLE.toString()))
            .andExpect(jsonPath("$.fonctions").value(DEFAULT_FONCTIONS.toString()))
            .andExpect(jsonPath("$.dateRV1").value(DEFAULT_DATE_RV_1.toString()))
            .andExpect(jsonPath("$.rv2").value(DEFAULT_RV_2.toString()))
            .andExpect(jsonPath("$.metiers").value(DEFAULT_METIERS.toString()))
            .andExpect(jsonPath("$.originePiste").value(DEFAULT_ORIGINE_PISTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR2LastWeekReportLine() throws Exception {
        // Get the r2LastWeekReportLine
        restR2LastWeekReportLineMockMvc.perform(get("/api/r-2-last-week-report-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR2LastWeekReportLine() throws Exception {
        // Initialize the database
        r2LastWeekReportLineService.save(r2LastWeekReportLine);

        int databaseSizeBeforeUpdate = r2LastWeekReportLineRepository.findAll().size();

        // Update the r2LastWeekReportLine
        R2LastWeekReportLine updatedR2LastWeekReportLine = r2LastWeekReportLineRepository.findById(r2LastWeekReportLine.getId()).get();
        // Disconnect from session so that the updates on updatedR2LastWeekReportLine are not directly saved in db
        em.detach(updatedR2LastWeekReportLine);
        updatedR2LastWeekReportLine
            .nomComplet(UPDATED_NOM_COMPLET)
            .rv1(UPDATED_RV_1)
            .dateDispo(UPDATED_DATE_DISPO)
            .competencePrincipale(UPDATED_COMPETENCE_PRINCIPALE)
            .exp(UPDATED_EXP)
            .trancheExp(UPDATED_TRANCHE_EXP)
            .ecole(UPDATED_ECOLE)
            .classeEcole(UPDATED_CLASSE_ECOLE)
            .fonctions(UPDATED_FONCTIONS)
            .dateRV1(UPDATED_DATE_RV_1)
            .rv2(UPDATED_RV_2)
            .metiers(UPDATED_METIERS)
            .originePiste(UPDATED_ORIGINE_PISTE);

        restR2LastWeekReportLineMockMvc.perform(put("/api/r-2-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedR2LastWeekReportLine)))
            .andExpect(status().isOk());

        // Validate the R2LastWeekReportLine in the database
        List<R2LastWeekReportLine> r2LastWeekReportLineList = r2LastWeekReportLineRepository.findAll();
        assertThat(r2LastWeekReportLineList).hasSize(databaseSizeBeforeUpdate);
        R2LastWeekReportLine testR2LastWeekReportLine = r2LastWeekReportLineList.get(r2LastWeekReportLineList.size() - 1);
        assertThat(testR2LastWeekReportLine.getNomComplet()).isEqualTo(UPDATED_NOM_COMPLET);
        assertThat(testR2LastWeekReportLine.getRv1()).isEqualTo(UPDATED_RV_1);
        assertThat(testR2LastWeekReportLine.getDateDispo()).isEqualTo(UPDATED_DATE_DISPO);
        assertThat(testR2LastWeekReportLine.getCompetencePrincipale()).isEqualTo(UPDATED_COMPETENCE_PRINCIPALE);
        assertThat(testR2LastWeekReportLine.getExp()).isEqualTo(UPDATED_EXP);
        assertThat(testR2LastWeekReportLine.getTrancheExp()).isEqualTo(UPDATED_TRANCHE_EXP);
        assertThat(testR2LastWeekReportLine.getEcole()).isEqualTo(UPDATED_ECOLE);
        assertThat(testR2LastWeekReportLine.getClasseEcole()).isEqualTo(UPDATED_CLASSE_ECOLE);
        assertThat(testR2LastWeekReportLine.getFonctions()).isEqualTo(UPDATED_FONCTIONS);
        assertThat(testR2LastWeekReportLine.getDateRV1()).isEqualTo(UPDATED_DATE_RV_1);
        assertThat(testR2LastWeekReportLine.getRv2()).isEqualTo(UPDATED_RV_2);
        assertThat(testR2LastWeekReportLine.getMetiers()).isEqualTo(UPDATED_METIERS);
        assertThat(testR2LastWeekReportLine.getOriginePiste()).isEqualTo(UPDATED_ORIGINE_PISTE);
    }

    @Test
    @Transactional
    public void updateNonExistingR2LastWeekReportLine() throws Exception {
        int databaseSizeBeforeUpdate = r2LastWeekReportLineRepository.findAll().size();

        // Create the R2LastWeekReportLine

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restR2LastWeekReportLineMockMvc.perform(put("/api/r-2-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(r2LastWeekReportLine)))
            .andExpect(status().isBadRequest());

        // Validate the R2LastWeekReportLine in the database
        List<R2LastWeekReportLine> r2LastWeekReportLineList = r2LastWeekReportLineRepository.findAll();
        assertThat(r2LastWeekReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteR2LastWeekReportLine() throws Exception {
        // Initialize the database
        r2LastWeekReportLineService.save(r2LastWeekReportLine);

        int databaseSizeBeforeDelete = r2LastWeekReportLineRepository.findAll().size();

        // Delete the r2LastWeekReportLine
        restR2LastWeekReportLineMockMvc.perform(delete("/api/r-2-last-week-report-lines/{id}", r2LastWeekReportLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<R2LastWeekReportLine> r2LastWeekReportLineList = r2LastWeekReportLineRepository.findAll();
        assertThat(r2LastWeekReportLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(R2LastWeekReportLine.class);
        R2LastWeekReportLine r2LastWeekReportLine1 = new R2LastWeekReportLine();
        r2LastWeekReportLine1.setId(1L);
        R2LastWeekReportLine r2LastWeekReportLine2 = new R2LastWeekReportLine();
        r2LastWeekReportLine2.setId(r2LastWeekReportLine1.getId());
        assertThat(r2LastWeekReportLine1).isEqualTo(r2LastWeekReportLine2);
        r2LastWeekReportLine2.setId(2L);
        assertThat(r2LastWeekReportLine1).isNotEqualTo(r2LastWeekReportLine2);
        r2LastWeekReportLine1.setId(null);
        assertThat(r2LastWeekReportLine1).isNotEqualTo(r2LastWeekReportLine2);
    }
}
