package com.game.web.rest;

import com.game.biz.model.DemarrageLastWeekReport;
import com.game.service.DemarrageLastWeekReportService;
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
 * REST controller for managing {@link DemarrageLastWeekReport}.
 */
@RestController
@RequestMapping("/api")
public class DemarrageLastWeekReportResource {

    private final Logger log = LoggerFactory.getLogger(DemarrageLastWeekReportResource.class);

    private static final String ENTITY_NAME = "demarrageLastWeekReport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemarrageLastWeekReportService demarrageLastWeekReportService;

    public DemarrageLastWeekReportResource(DemarrageLastWeekReportService demarrageLastWeekReportService) {
        this.demarrageLastWeekReportService = demarrageLastWeekReportService;
    }

    /**
     * {@code POST  /demarrage-last-week-reports} : Create a new demarrageLastWeekReport.
     *
     * @param demarrageLastWeekReport the demarrageLastWeekReport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demarrageLastWeekReport, or with status {@code 400 (Bad Request)} if the demarrageLastWeekReport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demarrage-last-week-reports")
    public ResponseEntity<DemarrageLastWeekReport> createDemarrageLastWeekReport(@RequestBody DemarrageLastWeekReport demarrageLastWeekReport) throws URISyntaxException {
        log.debug("REST request to save DemarrageLastWeekReport : {}", demarrageLastWeekReport);
        if (demarrageLastWeekReport.getId() != null) {
            throw new BadRequestAlertException("A new demarrageLastWeekReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemarrageLastWeekReport result = demarrageLastWeekReportService.save(demarrageLastWeekReport);
        return ResponseEntity.created(new URI("/api/demarrage-last-week-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demarrage-last-week-reports} : Updates an existing demarrageLastWeekReport.
     *
     * @param demarrageLastWeekReport the demarrageLastWeekReport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demarrageLastWeekReport,
     * or with status {@code 400 (Bad Request)} if the demarrageLastWeekReport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demarrageLastWeekReport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demarrage-last-week-reports")
    public ResponseEntity<DemarrageLastWeekReport> updateDemarrageLastWeekReport(@RequestBody DemarrageLastWeekReport demarrageLastWeekReport) throws URISyntaxException {
        log.debug("REST request to update DemarrageLastWeekReport : {}", demarrageLastWeekReport);
        if (demarrageLastWeekReport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DemarrageLastWeekReport result = demarrageLastWeekReportService.save(demarrageLastWeekReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demarrageLastWeekReport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /demarrage-last-week-reports} : get all the demarrageLastWeekReports.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demarrageLastWeekReports in body.
     */
    @GetMapping("/demarrage-last-week-reports")
    public List<DemarrageLastWeekReport> getAllDemarrageLastWeekReports() {
        log.debug("REST request to get all DemarrageLastWeekReports");
        return demarrageLastWeekReportService.findAll();
    }

    /**
     * {@code GET  /demarrage-last-week-reports/:id} : get the "id" demarrageLastWeekReport.
     *
     * @param id the id of the demarrageLastWeekReport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demarrageLastWeekReport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demarrage-last-week-reports/{id}")
    public ResponseEntity<DemarrageLastWeekReport> getDemarrageLastWeekReport(@PathVariable Long id) {
        log.debug("REST request to get DemarrageLastWeekReport : {}", id);
        Optional<DemarrageLastWeekReport> demarrageLastWeekReport = demarrageLastWeekReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demarrageLastWeekReport);
    }

    /**
     * {@code DELETE  /demarrage-last-week-reports/:id} : delete the "id" demarrageLastWeekReport.
     *
     * @param id the id of the demarrageLastWeekReport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demarrage-last-week-reports/{id}")
    public ResponseEntity<Void> deleteDemarrageLastWeekReport(@PathVariable Long id) {
        log.debug("REST request to delete DemarrageLastWeekReport : {}", id);
        demarrageLastWeekReportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
