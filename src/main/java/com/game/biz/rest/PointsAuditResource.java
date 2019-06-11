package com.game.biz.rest;

import com.game.biz.model.PointsAudit;
import com.game.biz.service.PointsAuditService;
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
 * REST controller for managing {@link PointsAudit}.
 */
@RestController
@RequestMapping("/api")
public class PointsAuditResource {

    private final Logger log = LoggerFactory.getLogger(PointsAuditResource.class);

    private static final String ENTITY_NAME = "pointsAudit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointsAuditService pointsAuditService;

    public PointsAuditResource(PointsAuditService pointsAuditService) {
        this.pointsAuditService = pointsAuditService;
    }

    /**
     * {@code POST  /points-audits} : Create a new pointsAudit.
     *
     * @param pointsAudit the pointsAudit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointsAudit, or with status {@code 400 (Bad Request)} if the pointsAudit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/points-audits")
    public ResponseEntity<PointsAudit> createPointsAudit(@RequestBody PointsAudit pointsAudit) throws URISyntaxException {
        log.debug("REST request to save PointsAudit : {}", pointsAudit);
        if (pointsAudit.getId() != null) {
            throw new BadRequestAlertException("A new pointsAudit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointsAudit result = pointsAuditService.save(pointsAudit);
        return ResponseEntity.created(new URI("/api/points-audits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /points-audits} : Updates an existing pointsAudit.
     *
     * @param pointsAudit the pointsAudit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointsAudit,
     * or with status {@code 400 (Bad Request)} if the pointsAudit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointsAudit couldn't be updated.
     */
    @PutMapping("/points-audits")
    public ResponseEntity<PointsAudit> updatePointsAudit(@RequestBody PointsAudit pointsAudit) {
        log.debug("REST request to update PointsAudit : {}", pointsAudit);
        if (pointsAudit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PointsAudit result = pointsAuditService.save(pointsAudit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pointsAudit.getId().toString()))
            .body(result);
    }


    @GetMapping("/points-audits-for/{userId}")
    public List<PointsAudit> findAllUnseen(@PathVariable Long userId) {
        return pointsAuditService.findAllUnseen(userId);
    }

    /**
     * {@code GET  /points-audits/:id} : get the "id" pointsAudit.
     *
     * @param id the id of the pointsAudit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointsAudit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/points-audits/{id}")
    public ResponseEntity<PointsAudit> getPointsAudit(@PathVariable Long id) {
        log.debug("REST request to get PointsAudit : {}", id);
        Optional<PointsAudit> pointsAudit = pointsAuditService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointsAudit);
    }

    /**
     * {@code DELETE  /points-audits/:id} : delete the "id" pointsAudit.
     *
     * @param id the id of the pointsAudit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/points-audits/{id}")
    public ResponseEntity<Void> deletePointsAudit(@PathVariable Long id) {
        log.debug("REST request to delete PointsAudit : {}", id);
        pointsAuditService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
