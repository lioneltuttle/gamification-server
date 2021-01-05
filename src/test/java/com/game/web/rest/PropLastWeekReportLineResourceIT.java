package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.PropLastWeekReportLine;
import com.game.repository.PropLastWeekReportLineRepository;
import com.game.service.PropLastWeekReportLineService;
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
 * Integration tests for the {@Link PropLastWeekReportLineResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class PropLastWeekReportLineResourceIT {

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
    private PropLastWeekReportLineRepository propLastWeekReportLineRepository;

    @Autowired
    private PropLastWeekReportLineService propLastWeekReportLineService;

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

    private MockMvc restPropLastWeekReportLineMockMvc;

    private PropLastWeekReportLine propLastWeekReportLine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropLastWeekReportLineResource propLastWeekReportLineResource = new PropLastWeekReportLineResource(propLastWeekReportLineService);
        this.restPropLastWeekReportLineMockMvc = MockMvcBuilders.standaloneSetup(propLastWeekReportLineResource)
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
    public static PropLastWeekReportLine createEntity(EntityManager em) {
        PropLastWeekReportLine propLastWeekReportLine = new PropLastWeekReportLine()
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
        return propLastWeekReportLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropLastWeekReportLine createUpdatedEntity(EntityManager em) {
        PropLastWeekReportLine propLastWeekReportLine = new PropLastWeekReportLine()
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
        return propLastWeekReportLine;
    }

    @BeforeEach
    public void initTest() {
        propLastWeekReportLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropLastWeekReportLine() throws Exception {
        int databaseSizeBeforeCreate = propLastWeekReportLineRepository.findAll().size();

        // Create the PropLastWeekReportLine
        restPropLastWeekReportLineMockMvc.perform(post("/api/prop-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propLastWeekReportLine)))
            .andExpect(status().isCreated());

        // Validate the PropLastWeekReportLine in the database
        List<PropLastWeekReportLine> propLastWeekReportLineList = propLastWeekReportLineRepository.findAll();
        assertThat(propLastWeekReportLineList).hasSize(databaseSizeBeforeCreate + 1);
        PropLastWeekReportLine testPropLastWeekReportLine = propLastWeekReportLineList.get(propLastWeekReportLineList.size() - 1);
        assertThat(testPropLastWeekReportLine.getNomComplet()).isEqualTo(DEFAULT_NOM_COMPLET);
        assertThat(testPropLastWeekReportLine.getRv1()).isEqualTo(DEFAULT_RV_1);
        assertThat(testPropLastWeekReportLine.getDateDispo()).isEqualTo(DEFAULT_DATE_DISPO);
        assertThat(testPropLastWeekReportLine.getCompetencePrincipale()).isEqualTo(DEFAULT_COMPETENCE_PRINCIPALE);
        assertThat(testPropLastWeekReportLine.getExp()).isEqualTo(DEFAULT_EXP);
        assertThat(testPropLastWeekReportLine.getTrancheExp()).isEqualTo(DEFAULT_TRANCHE_EXP);
        assertThat(testPropLastWeekReportLine.getEcole()).isEqualTo(DEFAULT_ECOLE);
        assertThat(testPropLastWeekReportLine.getClasseEcole()).isEqualTo(DEFAULT_CLASSE_ECOLE);
        assertThat(testPropLastWeekReportLine.getFonctions()).isEqualTo(DEFAULT_FONCTIONS);
        assertThat(testPropLastWeekReportLine.getDateRV1()).isEqualTo(DEFAULT_DATE_RV_1);
        assertThat(testPropLastWeekReportLine.getRv2()).isEqualTo(DEFAULT_RV_2);
        assertThat(testPropLastWeekReportLine.getMetiers()).isEqualTo(DEFAULT_METIERS);
        assertThat(testPropLastWeekReportLine.getOriginePiste()).isEqualTo(DEFAULT_ORIGINE_PISTE);
    }

    @Test
    @Transactional
    public void createPropLastWeekReportLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propLastWeekReportLineRepository.findAll().size();

        // Create the PropLastWeekReportLine with an existing ID
        propLastWeekReportLine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropLastWeekReportLineMockMvc.perform(post("/api/prop-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propLastWeekReportLine)))
            .andExpect(status().isBadRequest());

        // Validate the PropLastWeekReportLine in the database
        List<PropLastWeekReportLine> propLastWeekReportLineList = propLastWeekReportLineRepository.findAll();
        assertThat(propLastWeekReportLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPropLastWeekReportLines() throws Exception {
        // Initialize the database
        propLastWeekReportLineRepository.saveAndFlush(propLastWeekReportLine);

        // Get all the propLastWeekReportLineList
        restPropLastWeekReportLineMockMvc.perform(get("/api/prop-last-week-report-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propLastWeekReportLine.getId().intValue())))
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
    public void getPropLastWeekReportLine() throws Exception {
        // Initialize the database
        propLastWeekReportLineRepository.saveAndFlush(propLastWeekReportLine);

        // Get the propLastWeekReportLine
        restPropLastWeekReportLineMockMvc.perform(get("/api/prop-last-week-report-lines/{id}", propLastWeekReportLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propLastWeekReportLine.getId().intValue()))
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
    public void getNonExistingPropLastWeekReportLine() throws Exception {
        // Get the propLastWeekReportLine
        restPropLastWeekReportLineMockMvc.perform(get("/api/prop-last-week-report-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropLastWeekReportLine() throws Exception {
        // Initialize the database
        propLastWeekReportLineService.save(propLastWeekReportLine);

        int databaseSizeBeforeUpdate = propLastWeekReportLineRepository.findAll().size();

        // Update the propLastWeekReportLine
        PropLastWeekReportLine updatedPropLastWeekReportLine = propLastWeekReportLineRepository.findById(propLastWeekReportLine.getId()).get();
        // Disconnect from session so that the updates on updatedPropLastWeekReportLine are not directly saved in db
        em.detach(updatedPropLastWeekReportLine);
        updatedPropLastWeekReportLine
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

        restPropLastWeekReportLineMockMvc.perform(put("/api/prop-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPropLastWeekReportLine)))
            .andExpect(status().isOk());

        // Validate the PropLastWeekReportLine in the database
        List<PropLastWeekReportLine> propLastWeekReportLineList = propLastWeekReportLineRepository.findAll();
        assertThat(propLastWeekReportLineList).hasSize(databaseSizeBeforeUpdate);
        PropLastWeekReportLine testPropLastWeekReportLine = propLastWeekReportLineList.get(propLastWeekReportLineList.size() - 1);
        assertThat(testPropLastWeekReportLine.getNomComplet()).isEqualTo(UPDATED_NOM_COMPLET);
        assertThat(testPropLastWeekReportLine.getRv1()).isEqualTo(UPDATED_RV_1);
        assertThat(testPropLastWeekReportLine.getDateDispo()).isEqualTo(UPDATED_DATE_DISPO);
        assertThat(testPropLastWeekReportLine.getCompetencePrincipale()).isEqualTo(UPDATED_COMPETENCE_PRINCIPALE);
        assertThat(testPropLastWeekReportLine.getExp()).isEqualTo(UPDATED_EXP);
        assertThat(testPropLastWeekReportLine.getTrancheExp()).isEqualTo(UPDATED_TRANCHE_EXP);
        assertThat(testPropLastWeekReportLine.getEcole()).isEqualTo(UPDATED_ECOLE);
        assertThat(testPropLastWeekReportLine.getClasseEcole()).isEqualTo(UPDATED_CLASSE_ECOLE);
        assertThat(testPropLastWeekReportLine.getFonctions()).isEqualTo(UPDATED_FONCTIONS);
        assertThat(testPropLastWeekReportLine.getDateRV1()).isEqualTo(UPDATED_DATE_RV_1);
        assertThat(testPropLastWeekReportLine.getRv2()).isEqualTo(UPDATED_RV_2);
        assertThat(testPropLastWeekReportLine.getMetiers()).isEqualTo(UPDATED_METIERS);
        assertThat(testPropLastWeekReportLine.getOriginePiste()).isEqualTo(UPDATED_ORIGINE_PISTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPropLastWeekReportLine() throws Exception {
        int databaseSizeBeforeUpdate = propLastWeekReportLineRepository.findAll().size();

        // Create the PropLastWeekReportLine

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropLastWeekReportLineMockMvc.perform(put("/api/prop-last-week-report-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propLastWeekReportLine)))
            .andExpect(status().isBadRequest());

        // Validate the PropLastWeekReportLine in the database
        List<PropLastWeekReportLine> propLastWeekReportLineList = propLastWeekReportLineRepository.findAll();
        assertThat(propLastWeekReportLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropLastWeekReportLine() throws Exception {
        // Initialize the database
        propLastWeekReportLineService.save(propLastWeekReportLine);

        int databaseSizeBeforeDelete = propLastWeekReportLineRepository.findAll().size();

        // Delete the propLastWeekReportLine
        restPropLastWeekReportLineMockMvc.perform(delete("/api/prop-last-week-report-lines/{id}", propLastWeekReportLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PropLastWeekReportLine> propLastWeekReportLineList = propLastWeekReportLineRepository.findAll();
        assertThat(propLastWeekReportLineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropLastWeekReportLine.class);
        PropLastWeekReportLine propLastWeekReportLine1 = new PropLastWeekReportLine();
        propLastWeekReportLine1.setId(1L);
        PropLastWeekReportLine propLastWeekReportLine2 = new PropLastWeekReportLine();
        propLastWeekReportLine2.setId(propLastWeekReportLine1.getId());
        assertThat(propLastWeekReportLine1).isEqualTo(propLastWeekReportLine2);
        propLastWeekReportLine2.setId(2L);
        assertThat(propLastWeekReportLine1).isNotEqualTo(propLastWeekReportLine2);
        propLastWeekReportLine1.setId(null);
        assertThat(propLastWeekReportLine1).isNotEqualTo(propLastWeekReportLine2);
    }
}
