package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.DemarrageLastWeekReport;
import com.game.repository.DemarrageLastWeekReportRepository;
import com.game.service.DemarrageLastWeekReportService;
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
 * Integration tests for the {@Link DemarrageLastWeekReportResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class DemarrageLastWeekReportResourceIT {

    private static final Double DEFAULT_REPORT_ID = 1D;
    private static final Double UPDATED_REPORT_ID = 2D;

    private static final LocalDate DEFAULT_REPORT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DemarrageLastWeekReportRepository demarrageLastWeekReportRepository;

    @Autowired
    private DemarrageLastWeekReportService demarrageLastWeekReportService;

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

    private MockMvc restDemarrageLastWeekReportMockMvc;

    private DemarrageLastWeekReport demarrageLastWeekReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DemarrageLastWeekReportResource demarrageLastWeekReportResource = new DemarrageLastWeekReportResource(demarrageLastWeekReportService);
        this.restDemarrageLastWeekReportMockMvc = MockMvcBuilders.standaloneSetup(demarrageLastWeekReportResource)
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
    public static DemarrageLastWeekReport createEntity(EntityManager em) {
        DemarrageLastWeekReport demarrageLastWeekReport = new DemarrageLastWeekReport()
            .reportId(DEFAULT_REPORT_ID)
            .reportDate(DEFAULT_REPORT_DATE);
        return demarrageLastWeekReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemarrageLastWeekReport createUpdatedEntity(EntityManager em) {
        DemarrageLastWeekReport demarrageLastWeekReport = new DemarrageLastWeekReport()
            .reportId(UPDATED_REPORT_ID)
            .reportDate(UPDATED_REPORT_DATE);
        return demarrageLastWeekReport;
    }

    @BeforeEach
    public void initTest() {
        demarrageLastWeekReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createDemarrageLastWeekReport() throws Exception {
        int databaseSizeBeforeCreate = demarrageLastWeekReportRepository.findAll().size();

        // Create the DemarrageLastWeekReport
        restDemarrageLastWeekReportMockMvc.perform(post("/api/demarrage-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demarrageLastWeekReport)))
            .andExpect(status().isCreated());

        // Validate the DemarrageLastWeekReport in the database
        List<DemarrageLastWeekReport> demarrageLastWeekReportList = demarrageLastWeekReportRepository.findAll();
        assertThat(demarrageLastWeekReportList).hasSize(databaseSizeBeforeCreate + 1);
        DemarrageLastWeekReport testDemarrageLastWeekReport = demarrageLastWeekReportList.get(demarrageLastWeekReportList.size() - 1);
        assertThat(testDemarrageLastWeekReport.getReportId()).isEqualTo(DEFAULT_REPORT_ID);
        assertThat(testDemarrageLastWeekReport.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
    }

    @Test
    @Transactional
    public void createDemarrageLastWeekReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demarrageLastWeekReportRepository.findAll().size();

        // Create the DemarrageLastWeekReport with an existing ID
        demarrageLastWeekReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemarrageLastWeekReportMockMvc.perform(post("/api/demarrage-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demarrageLastWeekReport)))
            .andExpect(status().isBadRequest());

        // Validate the DemarrageLastWeekReport in the database
        List<DemarrageLastWeekReport> demarrageLastWeekReportList = demarrageLastWeekReportRepository.findAll();
        assertThat(demarrageLastWeekReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDemarrageLastWeekReports() throws Exception {
        // Initialize the database
        demarrageLastWeekReportRepository.saveAndFlush(demarrageLastWeekReport);

        // Get all the demarrageLastWeekReportList
        restDemarrageLastWeekReportMockMvc.perform(get("/api/demarrage-last-week-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demarrageLastWeekReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportId").value(hasItem(DEFAULT_REPORT_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDemarrageLastWeekReport() throws Exception {
        // Initialize the database
        demarrageLastWeekReportRepository.saveAndFlush(demarrageLastWeekReport);

        // Get the demarrageLastWeekReport
        restDemarrageLastWeekReportMockMvc.perform(get("/api/demarrage-last-week-reports/{id}", demarrageLastWeekReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demarrageLastWeekReport.getId().intValue()))
            .andExpect(jsonPath("$.reportId").value(DEFAULT_REPORT_ID.doubleValue()))
            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDemarrageLastWeekReport() throws Exception {
        // Get the demarrageLastWeekReport
        restDemarrageLastWeekReportMockMvc.perform(get("/api/demarrage-last-week-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDemarrageLastWeekReport() throws Exception {
        // Initialize the database
        demarrageLastWeekReportService.save(demarrageLastWeekReport);

        int databaseSizeBeforeUpdate = demarrageLastWeekReportRepository.findAll().size();

        // Update the demarrageLastWeekReport
        DemarrageLastWeekReport updatedDemarrageLastWeekReport = demarrageLastWeekReportRepository.findById(demarrageLastWeekReport.getId()).get();
        // Disconnect from session so that the updates on updatedDemarrageLastWeekReport are not directly saved in db
        em.detach(updatedDemarrageLastWeekReport);
        updatedDemarrageLastWeekReport
            .reportId(UPDATED_REPORT_ID)
            .reportDate(UPDATED_REPORT_DATE);

        restDemarrageLastWeekReportMockMvc.perform(put("/api/demarrage-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemarrageLastWeekReport)))
            .andExpect(status().isOk());

        // Validate the DemarrageLastWeekReport in the database
        List<DemarrageLastWeekReport> demarrageLastWeekReportList = demarrageLastWeekReportRepository.findAll();
        assertThat(demarrageLastWeekReportList).hasSize(databaseSizeBeforeUpdate);
        DemarrageLastWeekReport testDemarrageLastWeekReport = demarrageLastWeekReportList.get(demarrageLastWeekReportList.size() - 1);
        assertThat(testDemarrageLastWeekReport.getReportId()).isEqualTo(UPDATED_REPORT_ID);
        assertThat(testDemarrageLastWeekReport.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDemarrageLastWeekReport() throws Exception {
        int databaseSizeBeforeUpdate = demarrageLastWeekReportRepository.findAll().size();

        // Create the DemarrageLastWeekReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemarrageLastWeekReportMockMvc.perform(put("/api/demarrage-last-week-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demarrageLastWeekReport)))
            .andExpect(status().isBadRequest());

        // Validate the DemarrageLastWeekReport in the database
        List<DemarrageLastWeekReport> demarrageLastWeekReportList = demarrageLastWeekReportRepository.findAll();
        assertThat(demarrageLastWeekReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDemarrageLastWeekReport() throws Exception {
        // Initialize the database
        demarrageLastWeekReportService.save(demarrageLastWeekReport);

        int databaseSizeBeforeDelete = demarrageLastWeekReportRepository.findAll().size();

        // Delete the demarrageLastWeekReport
        restDemarrageLastWeekReportMockMvc.perform(delete("/api/demarrage-last-week-reports/{id}", demarrageLastWeekReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DemarrageLastWeekReport> demarrageLastWeekReportList = demarrageLastWeekReportRepository.findAll();
        assertThat(demarrageLastWeekReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemarrageLastWeekReport.class);
        DemarrageLastWeekReport demarrageLastWeekReport1 = new DemarrageLastWeekReport();
        demarrageLastWeekReport1.setId(1L);
        DemarrageLastWeekReport demarrageLastWeekReport2 = new DemarrageLastWeekReport();
        demarrageLastWeekReport2.setId(demarrageLastWeekReport1.getId());
        assertThat(demarrageLastWeekReport1).isEqualTo(demarrageLastWeekReport2);
        demarrageLastWeekReport2.setId(2L);
        assertThat(demarrageLastWeekReport1).isNotEqualTo(demarrageLastWeekReport2);
        demarrageLastWeekReport1.setId(null);
        assertThat(demarrageLastWeekReport1).isNotEqualTo(demarrageLastWeekReport2);
    }
}
