package com.game.web.rest;

import com.game.biz.model.R2LastWeekReport;
import com.game.service.R2LastWeekReportService;
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
 * REST controller for managing {@link R2LastWeekReport}.
 */
@RestController
@RequestMapping("/api")
public class R2LastWeekReportResource {

    private final Logger log = LoggerFactory.getLogger(R2LastWeekReportResource.class);

    private static final String ENTITY_NAME = "r2LastWeekReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final R2LastWeekReportService r2LastWeekReportService;

    public R2LastWeekReportResource(R2LastWeekReportService r2LastWeekReportService) {
        this.r2LastWeekReportService = r2LastWeekReportService;
    }

    /**
     * {@code POST  /r-2-last-week-reports} : Create a new r2LastWeekReport.
     *
     * @param r2LastWeekReport the r2LastWeekReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new r2LastWeekReport, or with status {@code 400 (Bad Request)} if the r2LastWeekReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/r-2-last-week-reports")
    public ResponseEntity<R2LastWeekReport> createR2LastWeekReport(@RequestBody R2LastWeekReport r2LastWeekReport) throws URISyntaxException {
        log.debug("REST request to save R2LastWeekReport : {}", r2LastWeekReport);
        if (r2LastWeekReport.getId() != null) {
            throw new BadRequestAlertException("A new r2LastWeekReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        R2LastWeekReport result = r2LastWeekReportService.save(r2LastWeekReport);
        return ResponseEntity.created(new URI("/api/r-2-last-week-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /r-2-last-week-reports} : Updates an existing r2LastWeekReport.
     *
     * @param r2LastWeekReport the r2LastWeekReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated r2LastWeekReport,
     * or with status {@code 400 (Bad Request)} if the r2LastWeekReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the r2LastWeekReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/r-2-last-week-reports")
    public ResponseEntity<R2LastWeekReport> updateR2LastWeekReport(@RequestBody R2LastWeekReport r2LastWeekReport) throws URISyntaxException {
        log.debug("REST request to update R2LastWeekReport : {}", r2LastWeekReport);
        if (r2LastWeekReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        R2LastWeekReport result = r2LastWeekReportService.save(r2LastWeekReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, r2LastWeekReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /r-2-last-week-reports} : get all the r2LastWeekReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of r2LastWeekReports in body.
     */
    @GetMapping("/r-2-last-week-reports")
    public List<R2LastWeekReport> getAllR2LastWeekReports() {
        log.debug("REST request to get all R2LastWeekReports");
        return r2LastWeekReportService.findAll();
    }

    /**
     * {@code GET  /r-2-last-week-reports/:id} : get the "id" r2LastWeekReport.
     *
     * @param id the id of the r2LastWeekReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the r2LastWeekReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/r-2-last-week-reports/{id}")
    public ResponseEntity<R2LastWeekReport> getR2LastWeekReport(@PathVariable Long id) {
        log.debug("REST request to get R2LastWeekReport : {}", id);
        Optional<R2LastWeekReport> r2LastWeekReport = r2LastWeekReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(r2LastWeekReport);
    }

    /**
     * {@code DELETE  /r-2-last-week-reports/:id} : delete the "id" r2LastWeekReport.
     *
     * @param id the id of the r2LastWeekReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/r-2-last-week-reports/{id}")
    public ResponseEntity<Void> deleteR2LastWeekReport(@PathVariable Long id) {
        log.debug("REST request to delete R2LastWeekReport : {}", id);
        r2LastWeekReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
