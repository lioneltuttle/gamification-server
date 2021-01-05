package com.game.web.rest;

import com.game.biz.model.PropLastWeekReportLine;
import com.game.service.PropLastWeekReportLineService;
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
 * REST controller for managing {@link PropLastWeekReportLine}.
 */
@RestController
@RequestMapping("/api")
public class PropLastWeekReportLineResource {

    private final Logger log = LoggerFactory.getLogger(PropLastWeekReportLineResource.class);

    private static final String ENTITY_NAME = "propLastWeekReportLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PropLastWeekReportLineService propLastWeekReportLineService;

    public PropLastWeekReportLineResource(PropLastWeekReportLineService propLastWeekReportLineService) {
        this.propLastWeekReportLineService = propLastWeekReportLineService;
    }

    /**
     * {@code POST  /prop-last-week-report-lines} : Create a new propLastWeekReportLine.
     *
     * @param propLastWeekReportLine the propLastWeekReportLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new propLastWeekReportLine, or with status {@code 400 (Bad Request)} if the propLastWeekReportLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prop-last-week-report-lines")
    public ResponseEntity<PropLastWeekReportLine> createPropLastWeekReportLine(@RequestBody PropLastWeekReportLine propLastWeekReportLine) throws URISyntaxException {
        log.debug("REST request to save PropLastWeekReportLine : {}", propLastWeekReportLine);
        if (propLastWeekReportLine.getId() != null) {
            throw new BadRequestAlertException("A new propLastWeekReportLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PropLastWeekReportLine result = propLastWeekReportLineService.save(propLastWeekReportLine);
        return ResponseEntity.created(new URI("/api/prop-last-week-report-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prop-last-week-report-lines} : Updates an existing propLastWeekReportLine.
     *
     * @param propLastWeekReportLine the propLastWeekReportLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated propLastWeekReportLine,
     * or with status {@code 400 (Bad Request)} if the propLastWeekReportLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the propLastWeekReportLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prop-last-week-report-lines")
    public ResponseEntity<PropLastWeekReportLine> updatePropLastWeekReportLine(@RequestBody PropLastWeekReportLine propLastWeekReportLine) throws URISyntaxException {
        log.debug("REST request to update PropLastWeekReportLine : {}", propLastWeekReportLine);
        if (propLastWeekReportLine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PropLastWeekReportLine result = propLastWeekReportLineService.save(propLastWeekReportLine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, propLastWeekReportLine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prop-last-week-report-lines} : get all the propLastWeekReportLines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of propLastWeekReportLines in body.
     */
    @GetMapping("/prop-last-week-report-lines")
    public List<PropLastWeekReportLine> getAllPropLastWeekReportLines() {
        log.debug("REST request to get all PropLastWeekReportLines");
        return propLastWeekReportLineService.findAll();
    }

    /**
     * {@code GET  /prop-last-week-report-lines/:id} : get the "id" propLastWeekReportLine.
     *
     * @param id the id of the propLastWeekReportLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the propLastWeekReportLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prop-last-week-report-lines/{id}")
    public ResponseEntity<PropLastWeekReportLine> getPropLastWeekReportLine(@PathVariable Long id) {
        log.debug("REST request to get PropLastWeekReportLine : {}", id);
        Optional<PropLastWeekReportLine> propLastWeekReportLine = propLastWeekReportLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(propLastWeekReportLine);
    }

    /**
     * {@code DELETE  /prop-last-week-report-lines/:id} : delete the "id" propLastWeekReportLine.
     *
     * @param id the id of the propLastWeekReportLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prop-last-week-report-lines/{id}")
    public ResponseEntity<Void> deletePropLastWeekReportLine(@PathVariable Long id) {
        log.debug("REST request to delete PropLastWeekReportLine : {}", id);
        propLastWeekReportLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
