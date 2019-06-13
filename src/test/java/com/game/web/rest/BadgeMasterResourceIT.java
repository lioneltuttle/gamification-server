package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.BadgeMaster;
import com.game.biz.rest.BadgeMasterResource;
import com.game.biz.service.BadgeMasterService;
import com.game.repository.biz.BadgeMasterRepository;
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
 * Integration tests for the {@Link BadgeMasterResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class BadgeMasterResourceIT {

    private static final Long DEFAULT_USER_ID = 1l;
    private static final Long UPDATED_USER_ID = 2l;

    private static final Integer DEFAULT_NB_BADGES = 1;
    private static final Integer UPDATED_NB_BADGES = 2;

    private static final LocalDate DEFAULT_VALIDITY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDITY_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BadgeMasterRepository badgeMasterRepository;

    @Autowired
    private BadgeMasterService badgeMasterService;

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

    private MockMvc restBadgeMasterMockMvc;

    private BadgeMaster badgeMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BadgeMasterResource badgeMasterResource = new BadgeMasterResource(badgeMasterService);
        this.restBadgeMasterMockMvc = MockMvcBuilders.standaloneSetup(badgeMasterResource)
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
    public static BadgeMaster createEntity(EntityManager em) {
        BadgeMaster badgeMaster = new BadgeMaster()
            .userId(DEFAULT_USER_ID)
            .nbBadges(DEFAULT_NB_BADGES)
            .validityDate(DEFAULT_VALIDITY_DATE);
        return badgeMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BadgeMaster createUpdatedEntity(EntityManager em) {
        return new BadgeMaster()
            .userId(UPDATED_USER_ID)
            .nbBadges(UPDATED_NB_BADGES)
            .validityDate(UPDATED_VALIDITY_DATE);
    }

    @BeforeEach
    public void initTest() {
        badgeMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadgeMaster() throws Exception {
        int databaseSizeBeforeCreate = badgeMasterRepository.findAll().size();

        // Create the BadgeMaster
        restBadgeMasterMockMvc.perform(post("/api/badge-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeMaster)))
            .andExpect(status().isCreated());

        // Validate the BadgeMaster in the database
        List<BadgeMaster> badgeMasterList = badgeMasterRepository.findAll();
        assertThat(badgeMasterList).hasSize(databaseSizeBeforeCreate + 1);
        BadgeMaster testBadgeMaster = badgeMasterList.get(badgeMasterList.size() - 1);
        assertThat(testBadgeMaster.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBadgeMaster.getNbBadges()).isEqualTo(DEFAULT_NB_BADGES);
        assertThat(testBadgeMaster.getValidityDate()).isEqualTo(DEFAULT_VALIDITY_DATE);
    }

    @Test
    @Transactional
    public void createBadgeMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeMasterRepository.findAll().size();

        // Create the BadgeMaster with an existing ID
        badgeMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeMasterMockMvc.perform(post("/api/badge-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeMaster in the database
        List<BadgeMaster> badgeMasterList = badgeMasterRepository.findAll();
        assertThat(badgeMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBadgeMasters() throws Exception {
        // Initialize the database
        badgeMasterRepository.saveAndFlush(badgeMaster);

        // Get all the badgeMasterList
        restBadgeMasterMockMvc.perform(get("/api/badge-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badgeMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].nbBadges").value(hasItem(DEFAULT_NB_BADGES)))
            .andExpect(jsonPath("$.[*].validityDate").value(hasItem(DEFAULT_VALIDITY_DATE.toString())));
    }

    @Test
    @Transactional
    public void getBadgeMaster() throws Exception {
        // Initialize the database
        badgeMasterRepository.saveAndFlush(badgeMaster);

        // Get the badgeMaster
        restBadgeMasterMockMvc.perform(get("/api/badge-masters/{id}", badgeMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(badgeMaster.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.doubleValue()))
            .andExpect(jsonPath("$.nbBadges").value(DEFAULT_NB_BADGES))
            .andExpect(jsonPath("$.validityDate").value(DEFAULT_VALIDITY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBadgeMaster() throws Exception {
        // Get the badgeMaster
        restBadgeMasterMockMvc.perform(get("/api/badge-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadgeMaster() throws Exception {
        // Initialize the database
        badgeMasterService.save(badgeMaster);

        int databaseSizeBeforeUpdate = badgeMasterRepository.findAll().size();

        // Update the badgeMaster
        BadgeMaster updatedBadgeMaster = badgeMasterRepository.findById(badgeMaster.getId()).get();
        // Disconnect from session so that the updates on updatedBadgeMaster are not directly saved in db
        em.detach(updatedBadgeMaster);
        updatedBadgeMaster
            .userId(UPDATED_USER_ID)
            .nbBadges(UPDATED_NB_BADGES)
            .validityDate(UPDATED_VALIDITY_DATE);

        restBadgeMasterMockMvc.perform(put("/api/badge-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBadgeMaster)))
            .andExpect(status().isOk());

        // Validate the BadgeMaster in the database
        List<BadgeMaster> badgeMasterList = badgeMasterRepository.findAll();
        assertThat(badgeMasterList).hasSize(databaseSizeBeforeUpdate);
        BadgeMaster testBadgeMaster = badgeMasterList.get(badgeMasterList.size() - 1);
        assertThat(testBadgeMaster.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBadgeMaster.getNbBadges()).isEqualTo(UPDATED_NB_BADGES);
        assertThat(testBadgeMaster.getValidityDate()).isEqualTo(UPDATED_VALIDITY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBadgeMaster() throws Exception {
        int databaseSizeBeforeUpdate = badgeMasterRepository.findAll().size();

        // Create the BadgeMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeMasterMockMvc.perform(put("/api/badge-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(badgeMaster)))
            .andExpect(status().isBadRequest());

        // Validate the BadgeMaster in the database
        List<BadgeMaster> badgeMasterList = badgeMasterRepository.findAll();
        assertThat(badgeMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadgeMaster() throws Exception {
        // Initialize the database
        badgeMasterService.save(badgeMaster);

        int databaseSizeBeforeDelete = badgeMasterRepository.findAll().size();

        // Delete the badgeMaster
        restBadgeMasterMockMvc.perform(delete("/api/badge-masters/{id}", badgeMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BadgeMaster> badgeMasterList = badgeMasterRepository.findAll();
        assertThat(badgeMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BadgeMaster.class);
        BadgeMaster badgeMaster1 = new BadgeMaster();
        badgeMaster1.setId(1L);
        BadgeMaster badgeMaster2 = new BadgeMaster();
        badgeMaster2.setId(badgeMaster1.getId());
        assertThat(badgeMaster1).isEqualTo(badgeMaster2);
        badgeMaster2.setId(2L);
        assertThat(badgeMaster1).isNotEqualTo(badgeMaster2);
        badgeMaster1.setId(null);
        assertThat(badgeMaster1).isNotEqualTo(badgeMaster2);
    }
}
