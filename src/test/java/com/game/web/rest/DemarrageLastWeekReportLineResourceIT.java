package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.DemarrageLastWeekReportLine;
import com.game.repository.DemarrageLastWeekReportLineRepository;
import com.game.service.DemarrageLastWeekReportLineService;
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
 * Integration tests for the {@Link DemarrageLastWeekReportLineResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class DemarrageLastWeekReportLineResourceIT {

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

    private static final LocalDate DEFAULT_DATE_ENTREE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENTREE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DemarrageLastWeekReportLineRepository demarrageLastWeekReportLineRepository;

    @Autowired
    private DemarrageLastWeekReportLineService demarrageLastWeekReportLineService;

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

    private MockMvc restDemarrageLastWeekReportLineMockMvc;

    private DemarrageLastWeekReportLine demarrageLastWeekReportLine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DemarrageLastWeekReportLineResource demarrageLastWeekReportLineResource = new DemarrageLastWeekReportLineResource(demarrageLastWeekReportLineService);
        this.restDemarrageLastWeekReportLineMockMvc = MockMvcBuilders.standaloneSetup(demarrageLastWeekReportLineResource)
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
    public static DemarrageLastWeekReportLine createEntity(EntityManager em) {
        DemarrageLastWeekReportLine demarrageLastWeekReportLine = new DemarrageLastWeekReportLine()
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
            .originePiste(DEFAULT_ORIGINE_PISTE)
            .dateEntree(DEFAULT_DATE_ENTREE)
            .dateDebut(DEFAULT_DATE_DEBUT);
        return demarrageLastWeekReportLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemarrageLastWeekReportLine createUpdatedEntity(EntityManager em) {
        DemarrageLastWeekReportLine demarrageLastWeekReportLine = new DemarrageLastWeekReportLine()
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
            .originePiste(UPDATED_ORIGINE_PISTE)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateDebut(UPDATED_DATE_DEBUT);
        return demarrageLastWeekReportLine;
    }

    @BeforeEach
    public void initTest() {
        demarrageLastWeekReportLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemarrageLastWeekReportLine() throws Exception {
        int databaseSizeBeforeCreate = demarrageLastWeekReportLineRepository.findAll().size();

        // Create the DemarrageLastWeekReportLine
        restDemarrageLastWeekReportLineMockMvc.perform(post("/api/demarrage-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demarrageLastWeekReportLine)))
            .andExpect(status().isCreated());

        // Validate the DemarrageLastWeekReportLine in the database
        List<DemarrageLastWeekReportLine> demarrageLastWeekReportLineList = demarrageLastWeekReportLineRepository.findAll();
        assertThat(demarrageLastWeekReportLineList).hasSize(databaseSizeBeforeCreate + 1);
        DemarrageLastWeekReportLine testDemarrageLastWeekReportLine = demarrageLastWeekReportLineList.get(demarrageLastWeekReportLineList.size() - 1);
        assertThat(testDemarrageLastWeekReportLine.getNomComplet()).isEqualTo(DEFAULT_NOM_COMPLET);
        assertThat(testDemarrageLastWeekReportLine.getRv1()).isEqualTo(DEFAULT_RV_1);
        assertThat(testDemarrageLastWeekReportLine.getDateDispo()).isEqualTo(DEFAULT_DATE_DISPO);
        assertThat(testDemarrageLastWeekReportLine.getCompetencePrincipale()).isEqualTo(DEFAULT_COMPETENCE_PRINCIPALE);
        assertThat(testDemarrageLastWeekReportLine.getExp()).isEqualTo(DEFAULT_EXP);
        assertThat(testDemarrageLastWeekReportLine.getTrancheExp()).isEqualTo(DEFAULT_TRANCHE_EXP);
        assertThat(testDemarrageLastWeekReportLine.getEcole()).isEqualTo(DEFAULT_ECOLE);
        assertThat(testDemarrageLastWeekReportLine.getClasseEcole()).isEqualTo(DEFAULT_CLASSE_ECOLE);
        assertThat(testDemarrageLastWeekReportLine.getFonctions()).isEqualTo(DEFAULT_FONCTIONS);
        assertThat(testDemarrageLastWeekReportLine.getDateRV1()).isEqualTo(DEFAULT_DATE_RV_1);
        assertThat(testDemarrageLastWeekReportLine.getRv2()).isEqualTo(DEFAULT_RV_2);
        assertThat(testDemarrageLastWeekReportLine.getMetiers()).isEqualTo(DEFAULT_METIERS);
        assertThat(testDemarrageLastWeekReportLine.getOriginePiste()).isEqualTo(DEFAULT_ORIGINE_PISTE);
        assertThat(testDemarrageLastWeekReportLine.getDateEntree()).isEqualTo(DEFAULT_DATE_ENTREE);
        assertThat(testDemarrageLastWeekReportLine.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void createDemarrageLastWeekReportLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demarrageLastWeekReportLineRepository.findAll().size();

        // Create the DemarrageLastWeekReportLine with an existing ID
        demarrageLastWeekReportLine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemarrageLastWeekReportLineMockMvc.perform(post("/api/demarrage-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demarrageLastWeekReportLine)))
            .andExpect(status().isBadRequest());

        // Validate the DemarrageLastWeekReportLine in the database
        List<DemarrageLastWeekReportLine> demarrageLastWeekReportLineList = demarrageLastWeekReportLineRepository.findAll();
        assertThat(demarrageLastWeekReportLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDemarrageLastWeekReportLines() throws Exception {
        // Initialize the database
        demarrageLastWeekReportLineRepository.saveAndFlush(demarrageLastWeekReportLine);

        // Get all the demarrageLastWeekReportLineList
        restDemarrageLastWeekReportLineMockMvc.perform(get("/api/demarrage-last-week-report-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demarrageLastWeekReportLine.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].originePiste").value(hasItem(DEFAULT_ORIGINE_PISTE.toString())))
            .andExpect(jsonPath("$.[*].dateEntree").value(hasItem(DEFAULT_DATE_ENTREE.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())));
    }

    @Test
    @Transactional
    public void getDemarrageLastWeekReportLine() throws Exception {
        // Initialize the database
        demarrageLastWeekReportLineRepository.saveAndFlush(demarrageLastWeekReportLine);

        // Get the demarrageLastWeekReportLine
        restDemarrageLastWeekReportLineMockMvc.perform(get("/api/demarrage-last-week-report-lines/{id}", demarrageLastWeekReportLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demarrageLastWeekReportLine.getId().intValue()))
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
            .andExpect(jsonPath("$.originePiste").value(DEFAULT_ORIGINE_PISTE.toString()))
            .andExpect(jsonPath("$.dateEntree").value(DEFAULT_DATE_ENTREE.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDemarrageLastWeekReportLine() throws Exception {
        // Get the demarrageLastWeekReportLine
        restDemarrageLastWeekReportLineMockMvc.perform(get("/api/demarrage-last-week-report-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemarrageLastWeekReportLine() throws Exception {
        // Initialize the database
        demarrageLastWeekReportLineService.save(demarrageLastWeekReportLine);

        int databaseSizeBeforeUpdate = demarrageLastWeekReportLineRepository.findAll().size();

        // Update the demarrageLastWeekReportLine
        DemarrageLastWeekReportLine updatedDemarrageLastWeekReportLine = demarrageLastWeekReportLineRepository.findById(demarrageLastWeekReportLine.getId()).get();
        // Disconnect from session so that the updates on updatedDemarrageLastWeekReportLine are not directly saved in db
        em.detach(updatedDemarrageLastWeekReportLine);
        updatedDemarrageLastWeekReportLine
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
            .originePiste(UPDATED_ORIGINE_PISTE)
            .dateEntree(UPDATED_DATE_ENTREE)
            .dateDebut(UPDATED_DATE_DEBUT);

        restDemarrageLastWeekReportLineMockMvc.perform(put("/api/demarrage-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemarrageLastWeekReportLine)))
            .andExpect(status().isOk());

        // Validate the DemarrageLastWeekReportLine in the database
        List<DemarrageLastWeekReportLine> demarrageLastWeekReportLineList = demarrageLastWeekReportLineRepository.findAll();
        assertThat(demarrageLastWeekReportLineList).hasSize(databaseSizeBeforeUpdate);
        DemarrageLastWeekReportLine testDemarrageLastWeekReportLine = demarrageLastWeekReportLineList.get(demarrageLastWeekReportLineList.size() - 1);
        assertThat(testDemarrageLastWeekReportLine.getNomComplet()).isEqualTo(UPDATED_NOM_COMPLET);
        assertThat(testDemarrageLastWeekReportLine.getRv1()).isEqualTo(UPDATED_RV_1);
        assertThat(testDemarrageLastWeekReportLine.getDateDispo()).isEqualTo(UPDATED_DATE_DISPO);
        assertThat(testDemarrageLastWeekReportLine.getCompetencePrincipale()).isEqualTo(UPDATED_COMPETENCE_PRINCIPALE);
        assertThat(testDemarrageLastWeekReportLine.getExp()).isEqualTo(UPDATED_EXP);
        assertThat(testDemarrageLastWeekReportLine.getTrancheExp()).isEqualTo(UPDATED_TRANCHE_EXP);
        assertThat(testDemarrageLastWeekReportLine.getEcole()).isEqualTo(UPDATED_ECOLE);
        assertThat(testDemarrageLastWeekReportLine.getClasseEcole()).isEqualTo(UPDATED_CLASSE_ECOLE);
        assertThat(testDemarrageLastWeekReportLine.getFonctions()).isEqualTo(UPDATED_FONCTIONS);
        assertThat(testDemarrageLastWeekReportLine.getDateRV1()).isEqualTo(UPDATED_DATE_RV_1);
        assertThat(testDemarrageLastWeekReportLine.getRv2()).isEqualTo(UPDATED_RV_2);
        assertThat(testDemarrageLastWeekReportLine.getMetiers()).isEqualTo(UPDATED_METIERS);
        assertThat(testDemarrageLastWeekReportLine.getOriginePiste()).isEqualTo(UPDATED_ORIGINE_PISTE);
        assertThat(testDemarrageLastWeekReportLine.getDateEntree()).isEqualTo(UPDATED_DATE_ENTREE);
        assertThat(testDemarrageLastWeekReportLine.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void updateNonExistingDemarrageLastWeekReportLine() throws Exception {
        int databaseSizeBeforeUpdate = demarrageLastWeekReportLineRepository.findAll().size();

        // Create the DemarrageLastWeekReportLine

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemarrageLastWeekReportLineMockMvc.perform(put("/api/demarrage-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demarrageLastWeekReportLine)))
            .andExpect(status().isBadRequest());

        // Validate the DemarrageLastWeekReportLine in the database
        List<DemarrageLastWeekReportLine> demarrageLastWeekReportLineList = demarrageLastWeekReportLineRepository.findAll();
        assertThat(demarrageLastWeekReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDemarrageLastWeekReportLine() throws Exception {
        // Initialize the database
        demarrageLastWeekReportLineService.save(demarrageLastWeekReportLine);

        int databaseSizeBeforeDelete = demarrageLastWeekReportLineRepository.findAll().size();

        // Delete the demarrageLastWeekReportLine
        restDemarrageLastWeekReportLineMockMvc.perform(delete("/api/demarrage-last-week-report-lines/{id}", demarrageLastWeekReportLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DemarrageLastWeekReportLine> demarrageLastWeekReportLineList = demarrageLastWeekReportLineRepository.findAll();
        assertThat(demarrageLastWeekReportLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemarrageLastWeekReportLine.class);
        DemarrageLastWeekReportLine demarrageLastWeekReportLine1 = new DemarrageLastWeekReportLine();
        demarrageLastWeekReportLine1.setId(1L);
        DemarrageLastWeekReportLine demarrageLastWeekReportLine2 = new DemarrageLastWeekReportLine();
        demarrageLastWeekReportLine2.setId(demarrageLastWeekReportLine1.getId());
        assertThat(demarrageLastWeekReportLine1).isEqualTo(demarrageLastWeekReportLine2);
        demarrageLastWeekReportLine2.setId(2L);
        assertThat(demarrageLastWeekReportLine1).isNotEqualTo(demarrageLastWeekReportLine2);
        demarrageLastWeekReportLine1.setId(null);
        assertThat(demarrageLastWeekReportLine1).isNotEqualTo(demarrageLastWeekReportLine2);
    }
}
