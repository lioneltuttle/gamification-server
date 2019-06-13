package com.game.biz.rest;

import com.game.biz.model.BadgeMaster;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;
import com.game.biz.service.BadgeMasterService;
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
 * REST controller for managing {@link BadgeMaster}.
 */
@RestController
@RequestMapping("/api")
public class BadgeMasterResource {

    private final Logger log = LoggerFactory.getLogger(BadgeMasterResource.class);

    private static final String ENTITY_NAME = "badgeMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeMasterService badgeMasterService;

    public BadgeMasterResource(BadgeMasterService badgeMasterService) {
        this.badgeMasterService = badgeMasterService;
    }

    /**
     * {@code POST  /badge-masters} : Create a new badgeMaster.
     *
     * @param badgeMaster the badgeMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeMaster, or with status {@code 400 (Bad Request)} if the badgeMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badge-masters")
    public ResponseEntity<BadgeMaster> createBadgeMaster(@RequestBody BadgeMaster badgeMaster) throws URISyntaxException {
        log.debug("REST request to save BadgeMaster : {}", badgeMaster);
        if (badgeMaster.getId() != null) {
            throw new BadRequestAlertException("A new badgeMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeMaster result = badgeMasterService.save(badgeMaster);
        return ResponseEntity.created(new URI("/api/badge-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badge-masters} : Updates an existing badgeMaster.
     *
     * @param badgeMaster the badgeMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeMaster,
     * or with status {@code 400 (Bad Request)} if the badgeMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeMaster couldn't be updated.
     */
    @PutMapping("/badge-masters")
    public ResponseEntity<BadgeMaster> updateBadgeMaster(@RequestBody BadgeMaster badgeMaster) {
        log.debug("REST request to update BadgeMaster : {}", badgeMaster);
        if (badgeMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BadgeMaster result = badgeMasterService.save(badgeMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgeMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /badge-masters} : get all the badgeMasters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badgeMasters in body.
     */
    @GetMapping("/badge-masters")
    public List<BadgeMaster> getAllBadgeMasters() {
        log.debug("REST request to get all BadgeMasters");
        return badgeMasterService.findAll();
    }

    /**
     * {@code GET  /badge-masters/:id} : get the "id" badgeMaster.
     *
     * @param id the id of the badgeMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badge-masters/{id}")
    public ResponseEntity<BadgeMaster> getBadgeMaster(@PathVariable Long id) {
        log.debug("REST request to get BadgeMaster : {}", id);
        Optional<BadgeMaster> badgeMaster = badgeMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeMaster);
    }

    /**
     * {@code DELETE  /badge-masters/:id} : delete the "id" badgeMaster.
     *
     * @param id the id of the badgeMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badge-masters/{id}")
    public ResponseEntity<Void> deleteBadgeMaster(@PathVariable Long id) {
        log.debug("REST request to delete BadgeMaster : {}", id);
        badgeMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/badge-masters/count/{userId}")
    public Long getNbBadgesMaster(@PathVariable Long userId) throws NumberOfBadgesRequiredException {
        log.debug("REST request to get Resultat  for /badge-masters/count/ : {}", userId);
        return badgeMasterService.getNbBadgesMaster(userId);
    }

    @GetMapping("/badge-masters/exchange/{userId}")
    public void exchangeForLegend(@PathVariable Long userId) throws NumberOfBadgesRequiredException {
        badgeMasterService.exchangeMasterForLegend(userId);
    }
}
