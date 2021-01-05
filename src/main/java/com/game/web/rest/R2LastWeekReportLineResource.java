package com.game.web.rest;

import com.game.biz.model.R2LastWeekReportLine;
import com.game.service.R2LastWeekReportLineService;
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
 * REST controller for managing {@link R2LastWeekReportLine}.
 */
@RestController
@RequestMapping("/api")
public class R2LastWeekReportLineResource {

    private final Logger log = LoggerFactory.getLogger(R2LastWeekReportLineResource.class);

    private static final String ENTITY_NAME = "r2LastWeekReportLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final R2LastWeekReportLineService r2LastWeekReportLineService;

    public R2LastWeekReportLineResource(R2LastWeekReportLineService r2LastWeekReportLineService) {
        this.r2LastWeekReportLineService = r2LastWeekReportLineService;
    }

    /**
     * {@code POST  /r-2-last-week-report-lines} : Create a new r2LastWeekReportLine.
     *
     * @param r2LastWeekReportLine the r2LastWeekReportLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new r2LastWeekReportLine, or with status {@code 400 (Bad Request)} if the r2LastWeekReportLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/r-2-last-week-report-lines")
    public ResponseEntity<R2LastWeekReportLine> createR2LastWeekReportLine(@RequestBody R2LastWeekReportLine r2LastWeekReportLine) throws URISyntaxException {
        log.debug("REST request to save R2LastWeekReportLine : {}", r2LastWeekReportLine);
        if (r2LastWeekReportLine.getId() != null) {
            throw new BadRequestAlertException("A new r2LastWeekReportLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        R2LastWeekReportLine result = r2LastWeekReportLineService.save(r2LastWeekReportLine);
        return ResponseEntity.created(new URI("/api/r-2-last-week-report-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /r-2-last-week-report-lines} : Updates an existing r2LastWeekReportLine.
     *
     * @param r2LastWeekReportLine the r2LastWeekReportLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated r2LastWeekReportLine,
     * or with status {@code 400 (Bad Request)} if the r2LastWeekReportLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the r2LastWeekReportLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/r-2-last-week-report-lines")
    public ResponseEntity<R2LastWeekReportLine> updateR2LastWeekReportLine(@RequestBody R2LastWeekReportLine r2LastWeekReportLine) throws URISyntaxException {
        log.debug("REST request to update R2LastWeekReportLine : {}", r2LastWeekReportLine);
        if (r2LastWeekReportLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        R2LastWeekReportLine result = r2LastWeekReportLineService.save(r2LastWeekReportLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, r2LastWeekReportLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /r-2-last-week-report-lines} : get all the r2LastWeekReportLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of r2LastWeekReportLines in body.
     */
    @GetMapping("/r-2-last-week-report-lines")
    public List<R2LastWeekReportLine> getAllR2LastWeekReportLines() {
        log.debug("REST request to get all R2LastWeekReportLines");
        return r2LastWeekReportLineService.findAll();
    }

    /**
     * {@code GET  /r-2-last-week-report-lines/:id} : get the "id" r2LastWeekReportLine.
     *
     * @param id the id of the r2LastWeekReportLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the r2LastWeekReportLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/r-2-last-week-report-lines/{id}")
    public ResponseEntity<R2LastWeekReportLine> getR2LastWeekReportLine(@PathVariable Long id) {
        log.debug("REST request to get R2LastWeekReportLine : {}", id);
        Optional<R2LastWeekReportLine> r2LastWeekReportLine = r2LastWeekReportLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(r2LastWeekReportLine);
    }

    /**
     * {@code DELETE  /r-2-last-week-report-lines/:id} : delete the "id" r2LastWeekReportLine.
     *
     * @param id the id of the r2LastWeekReportLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/r-2-last-week-report-lines/{id}")
    public ResponseEntity<Void> deleteR2LastWeekReportLine(@PathVariable Long id) {
        log.debug("REST request to delete R2LastWeekReportLine : {}", id);
        r2LastWeekReportLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
