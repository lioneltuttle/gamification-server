package com.game.biz.rest;

import com.game.biz.model.BadgeLegend;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;
import com.game.biz.service.BadgeLegendService;
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
 * REST controller for managing {@link BadgeLegend}.
 */
@RestController
@RequestMapping("/api")
public class BadgeLegendResource {

    private final Logger log = LoggerFactory.getLogger(BadgeLegendResource.class);

    private static final String ENTITY_NAME = "badgeLegend";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeLegendService badgeLegendService;

    public BadgeLegendResource(BadgeLegendService badgeLegendService) {
        this.badgeLegendService = badgeLegendService;
    }

    /**
     * {@code POST  /badge-legends} : Create a new badgeLegend.
     *
     * @param badgeLegend the badgeLegend to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeLegend, or with status {@code 400 (Bad Request)} if the badgeLegend has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badge-legends")
    public ResponseEntity<BadgeLegend> createBadgeLegend(@RequestBody BadgeLegend badgeLegend) throws URISyntaxException {
        log.debug("REST request to save BadgeLegend : {}", badgeLegend);
        if (badgeLegend.getId() != null) {
            throw new BadRequestAlertException("A new badgeLegend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeLegend result = badgeLegendService.save(badgeLegend);
        return ResponseEntity.created(new URI("/api/badge-legends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badge-legends} : Updates an existing badgeLegend.
     *
     * @param badgeLegend the badgeLegend to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeLegend,
     * or with status {@code 400 (Bad Request)} if the badgeLegend is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeLegend couldn't be updated.
     */
    @PutMapping("/badge-legends")
    public ResponseEntity<BadgeLegend> updateBadgeLegend(@RequestBody BadgeLegend badgeLegend) {
        log.debug("REST request to update BadgeLegend : {}", badgeLegend);
        if (badgeLegend.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BadgeLegend result = badgeLegendService.save(badgeLegend);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgeLegend.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /badge-legends} : get all the badgeLegends.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badgeLegends in body.
     */
    @GetMapping("/badge-legends")
    public List<BadgeLegend> getAllBadgeLegends() {
        log.debug("REST request to get all BadgeLegends");
        return badgeLegendService.findAll();
    }

    /**
     * {@code GET  /badge-legends/:id} : get the "id" badgeLegend.
     *
     * @param id the id of the badgeLegend to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeLegend, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badge-legends/{id}")
    public ResponseEntity<BadgeLegend> getBadgeLegend(@PathVariable Long id) {
        log.debug("REST request to get BadgeLegend : {}", id);
        Optional<BadgeLegend> badgeLegend = badgeLegendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeLegend);
    }

    /**
     * {@code DELETE  /badge-legends/:id} : delete the "id" badgeLegend.
     *
     * @param id the id of the badgeLegend to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badge-legends/{id}")
    public ResponseEntity<Void> deleteBadgeLegend(@PathVariable Long id) {
        log.debug("REST request to delete BadgeLegend : {}", id);
        badgeLegendService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/badge-legends/count/{userId}")
    public Long getNbBadgesLegend(@PathVariable Long userId) throws NumberOfBadgesRequiredException {
        return badgeLegendService.getNbBadgesLegend(userId);
    }

    @GetMapping("/badge-legends/exchange/{userId}")
    public void exchangeForPresent(@PathVariable Long userId) throws NumberOfBadgesRequiredException {
        badgeLegendService.exchangeLegendForPresent(userId);
    }
}
