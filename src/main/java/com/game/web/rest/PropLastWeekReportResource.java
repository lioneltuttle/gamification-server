package com.game.web.rest;

import com.game.biz.model.PropLastWeekReport;
import com.game.service.PropLastWeekReportService;
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
 * REST controller for managing {@link PropLastWeekReport}.
 */
@RestController
@RequestMapping("/api")
public class PropLastWeekReportResource {

    private final Logger log = LoggerFactory.getLogger(PropLastWeekReportResource.class);

    private static final String ENTITY_NAME = "propLastWeekReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropLastWeekReportService propLastWeekReportService;

    public PropLastWeekReportResource(PropLastWeekReportService propLastWeekReportService) {
        this.propLastWeekReportService = propLastWeekReportService;
    }

    /**
     * {@code POST  /prop-last-week-reports} : Create a new propLastWeekReport.
     *
     * @param propLastWeekReport the propLastWeekReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propLastWeekReport, or with status {@code 400 (Bad Request)} if the propLastWeekReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prop-last-week-reports")
    public ResponseEntity<PropLastWeekReport> createPropLastWeekReport(@RequestBody PropLastWeekReport propLastWeekReport) throws URISyntaxException {
        log.debug("REST request to save PropLastWeekReport : {}", propLastWeekReport);
        if (propLastWeekReport.getId() != null) {
            throw new BadRequestAlertException("A new propLastWeekReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropLastWeekReport result = propLastWeekReportService.save(propLastWeekReport);
        return ResponseEntity.created(new URI("/api/prop-last-week-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prop-last-week-reports} : Updates an existing propLastWeekReport.
     *
     * @param propLastWeekReport the propLastWeekReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propLastWeekReport,
     * or with status {@code 400 (Bad Request)} if the propLastWeekReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propLastWeekReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prop-last-week-reports")
    public ResponseEntity<PropLastWeekReport> updatePropLastWeekReport(@RequestBody PropLastWeekReport propLastWeekReport) throws URISyntaxException {
        log.debug("REST request to update PropLastWeekReport : {}", propLastWeekReport);
        if (propLastWeekReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropLastWeekReport result = propLastWeekReportService.save(propLastWeekReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, propLastWeekReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prop-last-week-reports} : get all the propLastWeekReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propLastWeekReports in body.
     */
    @GetMapping("/prop-last-week-reports")
    public List<PropLastWeekReport> getAllPropLastWeekReports() {
        log.debug("REST request to get all PropLastWeekReports");
        return propLastWeekReportService.findAll();
    }

    /**
     * {@code GET  /prop-last-week-reports/:id} : get the "id" propLastWeekReport.
     *
     * @param id the id of the propLastWeekReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propLastWeekReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prop-last-week-reports/{id}")
    public ResponseEntity<PropLastWeekReport> getPropLastWeekReport(@PathVariable Long id) {
        log.debug("REST request to get PropLastWeekReport : {}", id);
        Optional<PropLastWeekReport> propLastWeekReport = propLastWeekReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propLastWeekReport);
    }

    /**
     * {@code DELETE  /prop-last-week-reports/:id} : delete the "id" propLastWeekReport.
     *
     * @param id the id of the propLastWeekReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prop-last-week-reports/{id}")
    public ResponseEntity<Void> deletePropLastWeekReport(@PathVariable Long id) {
        log.debug("REST request to delete PropLastWeekReport : {}", id);
        propLastWeekReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
