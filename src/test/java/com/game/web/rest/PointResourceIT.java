package com.game.web.rest;

import com.game.GamejhipsterApp;
import com.game.biz.model.Point;
import com.game.biz.model.enumeration.BadgeType;
import com.game.biz.rest.PointResource;
import com.game.biz.service.PointService;
import com.game.repository.biz.PointRepository;
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
 * Integration tests for the {@Link PointResource} REST controller.
 */
@SpringBootTest(classes = GamejhipsterApp.class)
public class PointResourceIT {

    private static final Long DEFAULT_USER_ID = 1l;
    private static final Long UPDATED_USER_ID = 2l;

    private static final BadgeType DEFAULT_CATEGORIE = BadgeType.R2;
    private static final BadgeType UPDATED_CATEGORIE = BadgeType.PROP;

    private static final Integer DEFAULT_NB_POINTS = 1;
    private static final Integer UPDATED_NB_POINTS = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PointService pointService;

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

    private MockMvc restPointMockMvc;

    private Point point;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PointResource pointResource = new PointResource(pointService);
        this.restPointMockMvc = MockMvcBuilders.standaloneSetup(pointResource)
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
    public static Point createEntity(EntityManager em) {
        return new Point()
            .userId(DEFAULT_USER_ID)
            .categorie(DEFAULT_CATEGORIE)
            .nbPoints(DEFAULT_NB_POINTS)
            .date(DEFAULT_DATE);
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Point createUpdatedEntity(EntityManager em) {
        return new Point()
            .userId(UPDATED_USER_ID)
            .categorie(UPDATED_CATEGORIE)
            .nbPoints(UPDATED_NB_POINTS)
            .date(UPDATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        point = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoint() throws Exception {
        int databaseSizeBeforeCreate = pointRepository.findAll().size();

        // Create the Point
        restPointMockMvc.perform(post("/api/points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(point)))
            .andExpect(status().isCreated());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeCreate + 1);
        Point testPoint = pointList.get(pointList.size() - 1);
        assertThat(testPoint.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testPoint.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testPoint.getNbPoints()).isEqualTo(DEFAULT_NB_POINTS);
        assertThat(testPoint.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pointRepository.findAll().size();

        // Create the Point with an existing ID
        point.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointMockMvc.perform(post("/api/points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(point)))
            .andExpect(status().isBadRequest());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPoints() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get all the pointList
        restPointMockMvc.perform(get("/api/points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(point.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].nbPoints").value(hasItem(DEFAULT_NB_POINTS)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPoint() throws Exception {
        // Initialize the database
        pointRepository.saveAndFlush(point);

        // Get the point
        restPointMockMvc.perform(get("/api/points/{id}", point.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(point.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.doubleValue()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.nbPoints").value(DEFAULT_NB_POINTS))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPoint() throws Exception {
        // Get the point
        restPointMockMvc.perform(get("/api/points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoint() throws Exception {
        // Initialize the database
        pointService.save(point);

        int databaseSizeBeforeUpdate = pointRepository.findAll().size();

        // Update the point
        Point updatedPoint = pointRepository.findById(point.getId()).get();
        // Disconnect from session so that the updates on updatedPoint are not directly saved in db
        em.detach(updatedPoint);
        updatedPoint
            .userId(UPDATED_USER_ID)
            .categorie(UPDATED_CATEGORIE)
            .nbPoints(UPDATED_NB_POINTS)
            .date(UPDATED_DATE);

        restPointMockMvc.perform(put("/api/points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoint)))
            .andExpect(status().isOk());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
        Point testPoint = pointList.get(pointList.size() - 1);
        assertThat(testPoint.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testPoint.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testPoint.getNbPoints()).isEqualTo(UPDATED_NB_POINTS);
        assertThat(testPoint.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPoint() throws Exception {
        int databaseSizeBeforeUpdate = pointRepository.findAll().size();

        // Create the Point

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointMockMvc.perform(put("/api/points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(point)))
            .andExpect(status().isBadRequest());

        // Validate the Point in the database
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoint() throws Exception {
        // Initialize the database
        pointService.save(point);

        int databaseSizeBeforeDelete = pointRepository.findAll().size();

        // Delete the point
        restPointMockMvc.perform(delete("/api/points/{id}", point.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Point> pointList = pointRepository.findAll();
        assertThat(pointList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Point.class);
        Point point1 = new Point();
        point1.setId(1L);
        Point point2 = new Point();
        point2.setId(point1.getId());
        assertThat(point1).isEqualTo(point2);
        point2.setId(2L);
        assertThat(point1).isNotEqualTo(point2);
        point1.setId(null);
        assertThat(point1).isNotEqualTo(point2);
    }
}
