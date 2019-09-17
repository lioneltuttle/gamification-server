package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.PointsAudit;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.rest.PointsAuditResource;
import com.game.biz.service.PointsAuditService;
import com.game.repository.biz.PointsAuditRepository;
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
 * Integration tests for the {@Link PointsAuditResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class PointsAuditResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final EventType DEFAULT_SUBJECT = EventType.BADGE_PRO;
    private static final EventType UPDATED_SUBJECT = EventType.BADGE_MASTER_AVAILABLE;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEEN = false;
    private static final Boolean UPDATED_SEEN = true;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PointsAuditRepository pointsAuditRepository;

    @Autowired
    private PointsAuditService pointsAuditService;

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

    private MockMvc restPointsAuditMockMvc;

    private PointsAudit pointsAudit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PointsAuditResource pointsAuditResource = new PointsAuditResource(pointsAuditService);
        this.restPointsAuditMockMvc = MockMvcBuilders.standaloneSetup(pointsAuditResource)
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
    public static PointsAudit createEntity(EntityManager em) {
        PointsAudit pointsAudit = new PointsAudit()
            .userId(DEFAULT_USER_ID)
            .subject(DEFAULT_SUBJECT)
            .value(DEFAULT_VALUE)
            .seen(DEFAULT_SEEN)
            .date(DEFAULT_DATE);
        return pointsAudit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointsAudit createUpdatedEntity(EntityManager em) {
        PointsAudit pointsAudit = new PointsAudit()
            .userId(UPDATED_USER_ID)
            .subject(UPDATED_SUBJECT)
            .value(UPDATED_VALUE)
            .seen(UPDATED_SEEN)
            .date(UPDATED_DATE);
        return pointsAudit;
    }

    @BeforeEach
    public void initTest() {
        pointsAudit = createEntity(em);
    }

    @Test
    @Transactional
    public void createPointsAudit() throws Exception {
        int databaseSizeBeforeCreate = pointsAuditRepository.findAll().size();

        // Create the PointsAudit
        restPointsAuditMockMvc.perform(post("/api/points-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointsAudit)))
            .andExpect(status().isCreated());

        // Validate the PointsAudit in the database
        List<PointsAudit> pointsAuditList = pointsAuditRepository.findAll();
        assertThat(pointsAuditList).hasSize(databaseSizeBeforeCreate + 1);
        PointsAudit testPointsAudit = pointsAuditList.get(pointsAuditList.size() - 1);
        assertThat(testPointsAudit.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPointsAudit.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testPointsAudit.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPointsAudit.isSeen()).isEqualTo(DEFAULT_SEEN);
        assertThat(testPointsAudit.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createPointsAuditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pointsAuditRepository.findAll().size();

        // Create the PointsAudit with an existing ID
        pointsAudit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointsAuditMockMvc.perform(post("/api/points-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointsAudit)))
            .andExpect(status().isBadRequest());

        // Validate the PointsAudit in the database
        List<PointsAudit> pointsAuditList = pointsAuditRepository.findAll();
        assertThat(pointsAuditList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPointsAudits() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList
        restPointsAuditMockMvc.perform(get("/api/points-audits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointsAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].seen").value(hasItem(DEFAULT_SEEN.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPointsAudit() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get the pointsAudit
        restPointsAuditMockMvc.perform(get("/api/points-audits/{id}", pointsAudit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pointsAudit.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.seen").value(DEFAULT_SEEN.booleanValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where userId equals to DEFAULT_USER_ID
        defaultPointsAuditShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the pointsAuditList where userId equals to UPDATED_USER_ID
        defaultPointsAuditShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultPointsAuditShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the pointsAuditList where userId equals to UPDATED_USER_ID
        defaultPointsAuditShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where userId is not null
        defaultPointsAuditShouldBeFound("userId.specified=true");

        // Get all the pointsAuditList where userId is null
        defaultPointsAuditShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where userId greater than or equals to DEFAULT_USER_ID
        defaultPointsAuditShouldBeFound("userId.greaterOrEqualThan=" + DEFAULT_USER_ID);

        // Get all the pointsAuditList where userId greater than or equals to UPDATED_USER_ID
        defaultPointsAuditShouldNotBeFound("userId.greaterOrEqualThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where userId less than or equals to DEFAULT_USER_ID
        defaultPointsAuditShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the pointsAuditList where userId less than or equals to UPDATED_USER_ID
        defaultPointsAuditShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }


    @Test
    @Transactional
    public void getAllPointsAuditsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where subject equals to DEFAULT_SUBJECT
        defaultPointsAuditShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the pointsAuditList where subject equals to UPDATED_SUBJECT
        defaultPointsAuditShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultPointsAuditShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the pointsAuditList where subject equals to UPDATED_SUBJECT
        defaultPointsAuditShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where subject is not null
        defaultPointsAuditShouldBeFound("subject.specified=true");

        // Get all the pointsAuditList where subject is null
        defaultPointsAuditShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where value equals to DEFAULT_VALUE
        defaultPointsAuditShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the pointsAuditList where value equals to UPDATED_VALUE
        defaultPointsAuditShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPointsAuditShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the pointsAuditList where value equals to UPDATED_VALUE
        defaultPointsAuditShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where value is not null
        defaultPointsAuditShouldBeFound("value.specified=true");

        // Get all the pointsAuditList where value is null
        defaultPointsAuditShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllPointsAuditsBySeenIsEqualToSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where seen equals to DEFAULT_SEEN
        defaultPointsAuditShouldBeFound("seen.equals=" + DEFAULT_SEEN);

        // Get all the pointsAuditList where seen equals to UPDATED_SEEN
        defaultPointsAuditShouldNotBeFound("seen.equals=" + UPDATED_SEEN);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsBySeenIsInShouldWork() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where seen in DEFAULT_SEEN or UPDATED_SEEN
        defaultPointsAuditShouldBeFound("seen.in=" + DEFAULT_SEEN + "," + UPDATED_SEEN);

        // Get all the pointsAuditList where seen equals to UPDATED_SEEN
        defaultPointsAuditShouldNotBeFound("seen.in=" + UPDATED_SEEN);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsBySeenIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where seen is not null
        defaultPointsAuditShouldBeFound("seen.specified=true");

        // Get all the pointsAuditList where seen is null
        defaultPointsAuditShouldNotBeFound("seen.specified=false");
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where date equals to DEFAULT_DATE
        defaultPointsAuditShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the pointsAuditList where date equals to UPDATED_DATE
        defaultPointsAuditShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where date in DEFAULT_DATE or UPDATED_DATE
        defaultPointsAuditShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the pointsAuditList where date equals to UPDATED_DATE
        defaultPointsAuditShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where date is not null
        defaultPointsAuditShouldBeFound("date.specified=true");

        // Get all the pointsAuditList where date is null
        defaultPointsAuditShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where date greater than or equals to DEFAULT_DATE
        defaultPointsAuditShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the pointsAuditList where date greater than or equals to UPDATED_DATE
        defaultPointsAuditShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPointsAuditsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        pointsAuditRepository.saveAndFlush(pointsAudit);

        // Get all the pointsAuditList where date less than or equals to DEFAULT_DATE
        defaultPointsAuditShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the pointsAuditList where date less than or equals to UPDATED_DATE
        defaultPointsAuditShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPointsAuditShouldBeFound(String filter) throws Exception {
        restPointsAuditMockMvc.perform(get("/api/points-audits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointsAudit.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].seen").value(hasItem(DEFAULT_SEEN.booleanValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restPointsAuditMockMvc.perform(get("/api/points-audits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPointsAuditShouldNotBeFound(String filter) throws Exception {
        restPointsAuditMockMvc.perform(get("/api/points-audits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPointsAuditMockMvc.perform(get("/api/points-audits/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPointsAudit() throws Exception {
        // Get the pointsAudit
        restPointsAuditMockMvc.perform(get("/api/points-audits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePointsAudit() throws Exception {
        // Initialize the database
        pointsAuditService.save(pointsAudit);

        int databaseSizeBeforeUpdate = pointsAuditRepository.findAll().size();

        // Update the pointsAudit
        PointsAudit updatedPointsAudit = pointsAuditRepository.findById(pointsAudit.getId()).get();
        // Disconnect from session so that the updates on updatedPointsAudit are not directly saved in db
        em.detach(updatedPointsAudit);
        updatedPointsAudit
            .userId(UPDATED_USER_ID)
            .subject(UPDATED_SUBJECT)
            .value(UPDATED_VALUE)
            .seen(UPDATED_SEEN)
            .date(UPDATED_DATE);

        restPointsAuditMockMvc.perform(put("/api/points-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPointsAudit)))
            .andExpect(status().isOk());

        // Validate the PointsAudit in the database
        List<PointsAudit> pointsAuditList = pointsAuditRepository.findAll();
        assertThat(pointsAuditList).hasSize(databaseSizeBeforeUpdate);
        PointsAudit testPointsAudit = pointsAuditList.get(pointsAuditList.size() - 1);
        assertThat(testPointsAudit.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPointsAudit.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testPointsAudit.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPointsAudit.isSeen()).isEqualTo(UPDATED_SEEN);
        assertThat(testPointsAudit.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPointsAudit() throws Exception {
        int databaseSizeBeforeUpdate = pointsAuditRepository.findAll().size();

        // Create the PointsAudit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointsAuditMockMvc.perform(put("/api/points-audits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pointsAudit)))
            .andExpect(status().isBadRequest());

        // Validate the PointsAudit in the database
        List<PointsAudit> pointsAuditList = pointsAuditRepository.findAll();
        assertThat(pointsAuditList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePointsAudit() throws Exception {
        // Initialize the database
        pointsAuditService.save(pointsAudit);

        int databaseSizeBeforeDelete = pointsAuditRepository.findAll().size();

        // Delete the pointsAudit
        restPointsAuditMockMvc.perform(delete("/api/points-audits/{id}", pointsAudit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PointsAudit> pointsAuditList = pointsAuditRepository.findAll();
        assertThat(pointsAuditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointsAudit.class);
        PointsAudit pointsAudit1 = new PointsAudit();
        pointsAudit1.setId(1L);
        PointsAudit pointsAudit2 = new PointsAudit();
        pointsAudit2.setId(pointsAudit1.getId());
        assertThat(pointsAudit1).isEqualTo(pointsAudit2);
        pointsAudit2.setId(2L);
        assertThat(pointsAudit1).isNotEqualTo(pointsAudit2);
        pointsAudit1.setId(null);
        assertThat(pointsAudit1).isNotEqualTo(pointsAudit2);
    }
}
