package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.PropLastWeekReport;
import com.game.repository.PropLastWeekReportRepository;
import com.game.service.PropLastWeekReportService;
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
 * Integration tests for the {@Link PropLastWeekReportResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class PropLastWeekReportResourceIT {

    private static final Double DEFAULT_REPORT_ID = 1D;
    private static final Double UPDATED_REPORT_ID = 2D;

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PropLastWeekReportRepository propLastWeekReportRepository;

    @Autowired
    private PropLastWeekReportService propLastWeekReportService;

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

    private MockMvc restPropLastWeekReportMockMvc;

    private PropLastWeekReport propLastWeekReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropLastWeekReportResource propLastWeekReportResource = new PropLastWeekReportResource(propLastWeekReportService);
        this.restPropLastWeekReportMockMvc = MockMvcBuilders.standaloneSetup(propLastWeekReportResource)
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
    public static PropLastWeekReport createEntity(EntityManager em) {
        PropLastWeekReport propLastWeekReport = new PropLastWeekReport()
            .reportId(DEFAULT_REPORT_ID)
            .reportDate(DEFAULT_REPORT_DATE);
        return propLastWeekReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PropLastWeekReport createUpdatedEntity(EntityManager em) {
        PropLastWeekReport propLastWeekReport = new PropLastWeekReport()
            .reportId(UPDATED_REPORT_ID)
            .reportDate(UPDATED_REPORT_DATE);
        return propLastWeekReport;
    }

    @BeforeEach
    public void initTest() {
        propLastWeekReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createPropLastWeekReport() throws Exception {
        int databaseSizeBeforeCreate = propLastWeekReportRepository.findAll().size();

        // Create the PropLastWeekReport
        restPropLastWeekReportMockMvc.perform(post("/api/prop-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propLastWeekReport)))
            .andExpect(status().isCreated());

        // Validate the PropLastWeekReport in the database
        List<PropLastWeekReport> propLastWeekReportList = propLastWeekReportRepository.findAll();
        assertThat(propLastWeekReportList).hasSize(databaseSizeBeforeCreate + 1);
        PropLastWeekReport testPropLastWeekReport = propLastWeekReportList.get(propLastWeekReportList.size() - 1);
        assertThat(testPropLastWeekReport.getReportId()).isEqualTo(DEFAULT_REPORT_ID);
        assertThat(testPropLastWeekReport.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
    }

    @Test
    @Transactional
    public void createPropLastWeekReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propLastWeekReportRepository.findAll().size();

        // Create the PropLastWeekReport with an existing ID
        propLastWeekReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropLastWeekReportMockMvc.perform(post("/api/prop-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propLastWeekReport)))
            .andExpect(status().isBadRequest());

        // Validate the PropLastWeekReport in the database
        List<PropLastWeekReport> propLastWeekReportList = propLastWeekReportRepository.findAll();
        assertThat(propLastWeekReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPropLastWeekReports() throws Exception {
        // Initialize the database
        propLastWeekReportRepository.saveAndFlush(propLastWeekReport);

        // Get all the propLastWeekReportList
        restPropLastWeekReportMockMvc.perform(get("/api/prop-last-week-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(propLastWeekReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportId").value(hasItem(DEFAULT_REPORT_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPropLastWeekReport() throws Exception {
        // Initialize the database
        propLastWeekReportRepository.saveAndFlush(propLastWeekReport);

        // Get the propLastWeekReport
        restPropLastWeekReportMockMvc.perform(get("/api/prop-last-week-reports/{id}", propLastWeekReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(propLastWeekReport.getId().intValue()))
            .andExpect(jsonPath("$.reportId").value(DEFAULT_REPORT_ID.doubleValue()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPropLastWeekReport() throws Exception {
        // Get the propLastWeekReport
        restPropLastWeekReportMockMvc.perform(get("/api/prop-last-week-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePropLastWeekReport() throws Exception {
        // Initialize the database
        propLastWeekReportService.save(propLastWeekReport);

        int databaseSizeBeforeUpdate = propLastWeekReportRepository.findAll().size();

        // Update the propLastWeekReport
        PropLastWeekReport updatedPropLastWeekReport = propLastWeekReportRepository.findById(propLastWeekReport.getId()).get();
        // Disconnect from session so that the updates on updatedPropLastWeekReport are not directly saved in db
        em.detach(updatedPropLastWeekReport);
        updatedPropLastWeekReport
            .reportId(UPDATED_REPORT_ID)
            .reportDate(UPDATED_REPORT_DATE);

        restPropLastWeekReportMockMvc.perform(put("/api/prop-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPropLastWeekReport)))
            .andExpect(status().isOk());

        // Validate the PropLastWeekReport in the database
        List<PropLastWeekReport> propLastWeekReportList = propLastWeekReportRepository.findAll();
        assertThat(propLastWeekReportList).hasSize(databaseSizeBeforeUpdate);
        PropLastWeekReport testPropLastWeekReport = propLastWeekReportList.get(propLastWeekReportList.size() - 1);
        assertThat(testPropLastWeekReport.getReportId()).isEqualTo(UPDATED_REPORT_ID);
        assertThat(testPropLastWeekReport.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPropLastWeekReport() throws Exception {
        int databaseSizeBeforeUpdate = propLastWeekReportRepository.findAll().size();

        // Create the PropLastWeekReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropLastWeekReportMockMvc.perform(put("/api/prop-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propLastWeekReport)))
            .andExpect(status().isBadRequest());

        // Validate the PropLastWeekReport in the database
        List<PropLastWeekReport> propLastWeekReportList = propLastWeekReportRepository.findAll();
        assertThat(propLastWeekReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePropLastWeekReport() throws Exception {
        // Initialize the database
        propLastWeekReportService.save(propLastWeekReport);

        int databaseSizeBeforeDelete = propLastWeekReportRepository.findAll().size();

        // Delete the propLastWeekReport
        restPropLastWeekReportMockMvc.perform(delete("/api/prop-last-week-reports/{id}", propLastWeekReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PropLastWeekReport> propLastWeekReportList = propLastWeekReportRepository.findAll();
        assertThat(propLastWeekReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropLastWeekReport.class);
        PropLastWeekReport propLastWeekReport1 = new PropLastWeekReport();
        propLastWeekReport1.setId(1L);
        PropLastWeekReport propLastWeekReport2 = new PropLastWeekReport();
        propLastWeekReport2.setId(propLastWeekReport1.getId());
        assertThat(propLastWeekReport1).isEqualTo(propLastWeekReport2);
        propLastWeekReport2.setId(2L);
        assertThat(propLastWeekReport1).isNotEqualTo(propLastWeekReport2);
        propLastWeekReport1.setId(null);
        assertThat(propLastWeekReport1).isNotEqualTo(propLastWeekReport2);
    }
}
