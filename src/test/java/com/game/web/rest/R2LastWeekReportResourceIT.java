package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.R2LastWeekReport;
import com.game.repository.R2LastWeekReportRepository;
import com.game.service.R2LastWeekReportService;
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
 * Integration tests for the {@Link R2LastWeekReportResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class R2LastWeekReportResourceIT {

    private static final Double DEFAULT_REPORT_ID = 1D;
    private static final Double UPDATED_REPORT_ID = 2D;

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private R2LastWeekReportRepository r2LastWeekReportRepository;

    @Autowired
    private R2LastWeekReportService r2LastWeekReportService;

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

    private MockMvc restR2LastWeekReportMockMvc;

    private R2LastWeekReport r2LastWeekReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final R2LastWeekReportResource r2LastWeekReportResource = new R2LastWeekReportResource(r2LastWeekReportService);
        this.restR2LastWeekReportMockMvc = MockMvcBuilders.standaloneSetup(r2LastWeekReportResource)
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
    public static R2LastWeekReport createEntity(EntityManager em) {
        R2LastWeekReport r2LastWeekReport = new R2LastWeekReport()
            .reportId(DEFAULT_REPORT_ID)
            .reportDate(DEFAULT_REPORT_DATE);
        return r2LastWeekReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static R2LastWeekReport createUpdatedEntity(EntityManager em) {
        R2LastWeekReport r2LastWeekReport = new R2LastWeekReport()
            .reportId(UPDATED_REPORT_ID)
            .reportDate(UPDATED_REPORT_DATE);
        return r2LastWeekReport;
    }

    @BeforeEach
    public void initTest() {
        r2LastWeekReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createR2LastWeekReport() throws Exception {
        int databaseSizeBeforeCreate = r2LastWeekReportRepository.findAll().size();

        // Create the R2LastWeekReport
        restR2LastWeekReportMockMvc.perform(post("/api/r-2-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(r2LastWeekReport)))
            .andExpect(status().isCreated());

        // Validate the R2LastWeekReport in the database
        List<R2LastWeekReport> r2LastWeekReportList = r2LastWeekReportRepository.findAll();
        assertThat(r2LastWeekReportList).hasSize(databaseSizeBeforeCreate + 1);
        R2LastWeekReport testR2LastWeekReport = r2LastWeekReportList.get(r2LastWeekReportList.size() - 1);
        assertThat(testR2LastWeekReport.getReportId()).isEqualTo(DEFAULT_REPORT_ID);
        assertThat(testR2LastWeekReport.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
    }

    @Test
    @Transactional
    public void createR2LastWeekReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = r2LastWeekReportRepository.findAll().size();

        // Create the R2LastWeekReport with an existing ID
        r2LastWeekReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restR2LastWeekReportMockMvc.perform(post("/api/r-2-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(r2LastWeekReport)))
            .andExpect(status().isBadRequest());

        // Validate the R2LastWeekReport in the database
        List<R2LastWeekReport> r2LastWeekReportList = r2LastWeekReportRepository.findAll();
        assertThat(r2LastWeekReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllR2LastWeekReports() throws Exception {
        // Initialize the database
        r2LastWeekReportRepository.saveAndFlush(r2LastWeekReport);

        // Get all the r2LastWeekReportList
        restR2LastWeekReportMockMvc.perform(get("/api/r-2-last-week-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(r2LastWeekReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportId").value(hasItem(DEFAULT_REPORT_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getR2LastWeekReport() throws Exception {
        // Initialize the database
        r2LastWeekReportRepository.saveAndFlush(r2LastWeekReport);

        // Get the r2LastWeekReport
        restR2LastWeekReportMockMvc.perform(get("/api/r-2-last-week-reports/{id}", r2LastWeekReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(r2LastWeekReport.getId().intValue()))
            .andExpect(jsonPath("$.reportId").value(DEFAULT_REPORT_ID.doubleValue()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingR2LastWeekReport() throws Exception {
        // Get the r2LastWeekReport
        restR2LastWeekReportMockMvc.perform(get("/api/r-2-last-week-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateR2LastWeekReport() throws Exception {
        // Initialize the database
        r2LastWeekReportService.save(r2LastWeekReport);

        int databaseSizeBeforeUpdate = r2LastWeekReportRepository.findAll().size();

        // Update the r2LastWeekReport
        R2LastWeekReport updatedR2LastWeekReport = r2LastWeekReportRepository.findById(r2LastWeekReport.getId()).get();
        // Disconnect from session so that the updates on updatedR2LastWeekReport are not directly saved in db
        em.detach(updatedR2LastWeekReport);
        updatedR2LastWeekReport
            .reportId(UPDATED_REPORT_ID)
            .reportDate(UPDATED_REPORT_DATE);

        restR2LastWeekReportMockMvc.perform(put("/api/r-2-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedR2LastWeekReport)))
            .andExpect(status().isOk());

        // Validate the R2LastWeekReport in the database
        List<R2LastWeekReport> r2LastWeekReportList = r2LastWeekReportRepository.findAll();
        assertThat(r2LastWeekReportList).hasSize(databaseSizeBeforeUpdate);
        R2LastWeekReport testR2LastWeekReport = r2LastWeekReportList.get(r2LastWeekReportList.size() - 1);
        assertThat(testR2LastWeekReport.getReportId()).isEqualTo(UPDATED_REPORT_ID);
        assertThat(testR2LastWeekReport.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingR2LastWeekReport() throws Exception {
        int databaseSizeBeforeUpdate = r2LastWeekReportRepository.findAll().size();

        // Create the R2LastWeekReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restR2LastWeekReportMockMvc.perform(put("/api/r-2-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(r2LastWeekReport)))
            .andExpect(status().isBadRequest());

        // Validate the R2LastWeekReport in the database
        List<R2LastWeekReport> r2LastWeekReportList = r2LastWeekReportRepository.findAll();
        assertThat(r2LastWeekReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteR2LastWeekReport() throws Exception {
        // Initialize the database
        r2LastWeekReportService.save(r2LastWeekReport);

        int databaseSizeBeforeDelete = r2LastWeekReportRepository.findAll().size();

        // Delete the r2LastWeekReport
        restR2LastWeekReportMockMvc.perform(delete("/api/r-2-last-week-reports/{id}", r2LastWeekReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<R2LastWeekReport> r2LastWeekReportList = r2LastWeekReportRepository.findAll();
        assertThat(r2LastWeekReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(R2LastWeekReport.class);
        R2LastWeekReport r2LastWeekReport1 = new R2LastWeekReport();
        r2LastWeekReport1.setId(1L);
        R2LastWeekReport r2LastWeekReport2 = new R2LastWeekReport();
        r2LastWeekReport2.setId(r2LastWeekReport1.getId());
        assertThat(r2LastWeekReport1).isEqualTo(r2LastWeekReport2);
        r2LastWeekReport2.setId(2L);
        assertThat(r2LastWeekReport1).isNotEqualTo(r2LastWeekReport2);
        r2LastWeekReport1.setId(null);
        assertThat(r2LastWeekReport1).isNotEqualTo(r2LastWeekReport2);
    }
}
