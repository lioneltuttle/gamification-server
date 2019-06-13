package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.BadgeLegend;
import com.game.biz.rest.BadgeLegendResource;
import com.game.biz.service.BadgeLegendService;
import com.game.repository.biz.BadgeLegendRepository;
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
 * Integration tests for the {@Link BadgeLegendResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class BadgeLegendResourceIT {

    private static final Long DEFAULT_USER_ID = 1l;
    private static final Long UPDATED_USER_ID = 2l;

    private static final Integer DEFAULT_NB_BADGES = 1;
    private static final Integer UPDATED_NB_BADGES = 2;

    private static final LocalDate DEFAULT_VALIDITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDITY_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BadgeLegendRepository badgeLegendRepository;

    @Autowired
    private BadgeLegendService badgeLegendService;

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

    private MockMvc restBadgeLegendMockMvc;

    private BadgeLegend badgeLegend;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BadgeLegendResource badgeLegendResource = new BadgeLegendResource(badgeLegendService);
        this.restBadgeLegendMockMvc = MockMvcBuilders.standaloneSetup(badgeLegendResource)
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
    public static BadgeLegend createEntity(EntityManager em) {
        return new BadgeLegend()
            .userId(DEFAULT_USER_ID)
            .nbBadges(DEFAULT_NB_BADGES)
            .validityDate(DEFAULT_VALIDITY_DATE);
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeLegend createUpdatedEntity(EntityManager em) {
        BadgeLegend badgeLegend = new BadgeLegend()
            .userId(UPDATED_USER_ID)
            .nbBadges(UPDATED_NB_BADGES)
            .validityDate(UPDATED_VALIDITY_DATE);
        return badgeLegend;
    }

    @BeforeEach
    public void initTest() {
        badgeLegend = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadgeLegend() throws Exception {
        int databaseSizeBeforeCreate = badgeLegendRepository.findAll().size();

        // Create the BadgeLegend
        restBadgeLegendMockMvc.perform(post("/api/badge-legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeLegend)))
            .andExpect(status().isCreated());

        // Validate the BadgeLegend in the database
        List<BadgeLegend> badgeLegendList = badgeLegendRepository.findAll();
        assertThat(badgeLegendList).hasSize(databaseSizeBeforeCreate + 1);
        BadgeLegend testBadgeLegend = badgeLegendList.get(badgeLegendList.size() - 1);
        assertThat(testBadgeLegend.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBadgeLegend.getNbBadges()).isEqualTo(DEFAULT_NB_BADGES);
        assertThat(testBadgeLegend.getValidityDate()).isEqualTo(DEFAULT_VALIDITY_DATE);
    }

    @Test
    @Transactional
    public void createBadgeLegendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeLegendRepository.findAll().size();

        // Create the BadgeLegend with an existing ID
        badgeLegend.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeLegendMockMvc.perform(post("/api/badge-legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeLegend)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeLegend in the database
        List<BadgeLegend> badgeLegendList = badgeLegendRepository.findAll();
        assertThat(badgeLegendList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBadgeLegends() throws Exception {
        // Initialize the database
        badgeLegendRepository.saveAndFlush(badgeLegend);

        // Get all the badgeLegendList
        restBadgeLegendMockMvc.perform(get("/api/badge-legends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeLegend.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].nbBadges").value(hasItem(DEFAULT_NB_BADGES)))
            .andExpect(jsonPath("$.[*].validityDate").value(hasItem(DEFAULT_VALIDITY_DATE.toString())));
    }

    @Test
    @Transactional
    public void getBadgeLegend() throws Exception {
        // Initialize the database
        badgeLegendRepository.saveAndFlush(badgeLegend);

        // Get the badgeLegend
        restBadgeLegendMockMvc.perform(get("/api/badge-legends/{id}", badgeLegend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(badgeLegend.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.doubleValue()))
            .andExpect(jsonPath("$.nbBadges").value(DEFAULT_NB_BADGES))
            .andExpect(jsonPath("$.validityDate").value(DEFAULT_VALIDITY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBadgeLegend() throws Exception {
        // Get the badgeLegend
        restBadgeLegendMockMvc.perform(get("/api/badge-legends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadgeLegend() throws Exception {
        // Initialize the database
        badgeLegendService.save(badgeLegend);

        int databaseSizeBeforeUpdate = badgeLegendRepository.findAll().size();

        // Update the badgeLegend
        BadgeLegend updatedBadgeLegend = badgeLegendRepository.findById(badgeLegend.getId()).get();
        // Disconnect from session so that the updates on updatedBadgeLegend are not directly saved in db
        em.detach(updatedBadgeLegend);
        updatedBadgeLegend
            .userId(UPDATED_USER_ID)
            .nbBadges(UPDATED_NB_BADGES)
            .validityDate(UPDATED_VALIDITY_DATE);

        restBadgeLegendMockMvc.perform(put("/api/badge-legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBadgeLegend)))
            .andExpect(status().isOk());

        // Validate the BadgeLegend in the database
        List<BadgeLegend> badgeLegendList = badgeLegendRepository.findAll();
        assertThat(badgeLegendList).hasSize(databaseSizeBeforeUpdate);
        BadgeLegend testBadgeLegend = badgeLegendList.get(badgeLegendList.size() - 1);
        assertThat(testBadgeLegend.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBadgeLegend.getNbBadges()).isEqualTo(UPDATED_NB_BADGES);
        assertThat(testBadgeLegend.getValidityDate()).isEqualTo(UPDATED_VALIDITY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBadgeLegend() throws Exception {
        int databaseSizeBeforeUpdate = badgeLegendRepository.findAll().size();

        // Create the BadgeLegend

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeLegendMockMvc.perform(put("/api/badge-legends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeLegend)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeLegend in the database
        List<BadgeLegend> badgeLegendList = badgeLegendRepository.findAll();
        assertThat(badgeLegendList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadgeLegend() throws Exception {
        // Initialize the database
        badgeLegendService.save(badgeLegend);

        int databaseSizeBeforeDelete = badgeLegendRepository.findAll().size();

        // Delete the badgeLegend
        restBadgeLegendMockMvc.perform(delete("/api/badge-legends/{id}", badgeLegend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BadgeLegend> badgeLegendList = badgeLegendRepository.findAll();
        assertThat(badgeLegendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeLegend.class);
        BadgeLegend badgeLegend1 = new BadgeLegend();
        badgeLegend1.setId(1L);
        BadgeLegend badgeLegend2 = new BadgeLegend();
        badgeLegend2.setId(badgeLegend1.getId());
        assertThat(badgeLegend1).isEqualTo(badgeLegend2);
        badgeLegend2.setId(2L);
        assertThat(badgeLegend1).isNotEqualTo(badgeLegend2);
        badgeLegend1.setId(null);
        assertThat(badgeLegend1).isNotEqualTo(badgeLegend2);
    }
}
