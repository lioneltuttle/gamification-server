package com.game.biz.rest;

import com.game.biz.model.Point;
import com.game.biz.service.PointService;
import com.game.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link Point}.
 */
@RestController
@RequestMapping("/api")
public class PointResource {

    private final Logger log = LoggerFactory.getLogger(PointResource.class);

    private static final String ENTITY_NAME = "point";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointService pointService;

    public PointResource(PointService pointService) {
        this.pointService = pointService;
    }

    /**
     * {@code POST  /points} : Create a new point.
     *
     * @param point the point to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new point, or with status {@code 400 (Bad Request)} if the point has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/points")
    public ResponseEntity<Point> createPoint(@RequestBody Point point) throws URISyntaxException {
        log.debug("REST request to save Point : {}", point);
        if (point.getId() != null) {
            throw new BadRequestAlertException("A new point cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Point result = pointService.save(point);
        return ResponseEntity.created(new URI("/api/points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/pointsSave")
    public void createPoint(@RequestBody List<Point> points) {
        log.debug("REST request to save all Points : {}", points);

        pointService.saveAll(points);
    }

    /**
     * {@code PUT  /points} : Updates an existing point.
     *
     * @param point the point to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated point,
     * or with status {@code 400 (Bad Request)} if the point is not valid,
     * or with status {@code 500 (Internal Server Error)} if the point couldn't be updated.
     */
    @PutMapping("/points")
    public ResponseEntity<Point> updatePoint(@RequestBody Point point) {
        log.debug("REST request to update Point : {}", point);
        if (point.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Point result = pointService.save(point);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, point.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /points} : get all the points.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of points in body.
     */
    @GetMapping("/points")
    public List<Point> getAllPoints() {
        log.debug("REST request to get all Points");
        return pointService.findAll();
    }

    /**
     * {@code GET  /points/:id} : get the "id" point.
     *
     * @param id the id of the point to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the point, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/points/{id}")
    public ResponseEntity<Point> getPoint(@PathVariable Long id) {
        log.debug("REST request to get Point : {}", id);
        Optional<Point> point = pointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(point);
    }

    /**
     * {@code DELETE  /points/:id} : delete the "id" point.
     *
     * @param id the id of the point to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/points/{id}")
    public ResponseEntity<Void> deletePoint(@PathVariable Long id) {
        log.debug("REST request to delete Point : {}", id);
        pointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/AllPoints")
    public List<Point> getLast2WPointsByUser(@RequestBody Long userId){
        return pointService.findLast2WByUserId(userId);
    }
}
