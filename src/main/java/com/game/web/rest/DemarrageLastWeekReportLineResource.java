package com.game.web.rest;

import com.game.biz.model.DemarrageLastWeekReportLine;
import com.game.service.DemarrageLastWeekReportLineService;
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
 * REST controller for managing {@link DemarrageLastWeekReportLine}.
 */
@RestController
@RequestMapping("/api")
public class DemarrageLastWeekReportLineResource {

    private final Logger log = LoggerFactory.getLogger(DemarrageLastWeekReportLineResource.class);

    private static final String ENTITY_NAME = "demarrageLastWeekReportLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemarrageLastWeekReportLineService demarrageLastWeekReportLineService;

    public DemarrageLastWeekReportLineResource(DemarrageLastWeekReportLineService demarrageLastWeekReportLineService) {
        this.demarrageLastWeekReportLineService = demarrageLastWeekReportLineService;
    }

    /**
     * {@code POST  /demarrage-last-week-report-lines} : Create a new demarrageLastWeekReportLine.
     *
     * @param demarrageLastWeekReportLine the demarrageLastWeekReportLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demarrageLastWeekReportLine, or with status {@code 400 (Bad Request)} if the demarrageLastWeekReportLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demarrage-last-week-report-lines")
    public ResponseEntity<DemarrageLastWeekReportLine> createDemarrageLastWeekReportLine(@RequestBody DemarrageLastWeekReportLine demarrageLastWeekReportLine) throws URISyntaxException {
        log.debug("REST request to save DemarrageLastWeekReportLine : {}", demarrageLastWeekReportLine);
        if (demarrageLastWeekReportLine.getId() != null) {
            throw new BadRequestAlertException("A new demarrageLastWeekReportLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemarrageLastWeekReportLine result = demarrageLastWeekReportLineService.save(demarrageLastWeekReportLine);
        return ResponseEntity.created(new URI("/api/demarrage-last-week-report-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demarrage-last-week-report-lines} : Updates an existing demarrageLastWeekReportLine.
     *
     * @param demarrageLastWeekReportLine the demarrageLastWeekReportLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demarrageLastWeekReportLine,
     * or with status {@code 400 (Bad Request)} if the demarrageLastWeekReportLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demarrageLastWeekReportLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demarrage-last-week-report-lines")
    public ResponseEntity<DemarrageLastWeekReportLine> updateDemarrageLastWeekReportLine(@RequestBody DemarrageLastWeekReportLine demarrageLastWeekReportLine) throws URISyntaxException {
        log.debug("REST request to update DemarrageLastWeekReportLine : {}", demarrageLastWeekReportLine);
        if (demarrageLastWeekReportLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DemarrageLastWeekReportLine result = demarrageLastWeekReportLineService.save(demarrageLastWeekReportLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, demarrageLastWeekReportLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /demarrage-last-week-report-lines} : get all the demarrageLastWeekReportLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demarrageLastWeekReportLines in body.
     */
    @GetMapping("/demarrage-last-week-report-lines")
    public List<DemarrageLastWeekReportLine> getAllDemarrageLastWeekReportLines() {
        log.debug("REST request to get all DemarrageLastWeekReportLines");
        return demarrageLastWeekReportLineService.findAll();
    }

    /**
     * {@code GET  /demarrage-last-week-report-lines/:id} : get the "id" demarrageLastWeekReportLine.
     *
     * @param id the id of the demarrageLastWeekReportLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demarrageLastWeekReportLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demarrage-last-week-report-lines/{id}")
    public ResponseEntity<DemarrageLastWeekReportLine> getDemarrageLastWeekReportLine(@PathVariable Long id) {
        log.debug("REST request to get DemarrageLastWeekReportLine : {}", id);
        Optional<DemarrageLastWeekReportLine> demarrageLastWeekReportLine = demarrageLastWeekReportLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demarrageLastWeekReportLine);
    }

    /**
     * {@code DELETE  /demarrage-last-week-report-lines/:id} : delete the "id" demarrageLastWeekReportLine.
     *
     * @param id the id of the demarrageLastWeekReportLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demarrage-last-week-report-lines/{id}")
    public ResponseEntity<Void> deleteDemarrageLastWeekReportLine(@PathVariable Long id) {
        log.debug("REST request to delete DemarrageLastWeekReportLine : {}", id);
        demarrageLastWeekReportLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
