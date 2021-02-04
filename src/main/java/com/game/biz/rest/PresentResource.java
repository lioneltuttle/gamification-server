package com.game.biz.rest;

import com.game.biz.model.Present;
import com.game.biz.model.Present;
import com.game.biz.service.PresentService;
import com.game.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * REST controller for managing {@link Present}.
 */
@RestController
@RequestMapping("/api")
public class PresentResource {

    private final Logger log = LoggerFactory.getLogger(PresentResource.class);

    private static final String ENTITY_NAME = "present";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PresentService presentService;

    public PresentResource(PresentService presentService) {
        this.presentService = presentService;
    }

    /**
     * {@code POST  /presents} : Create a new present.
     *
     * @param present the present to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new present, or with status {@code 400 (Bad Request)} if the present has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/present")
    public ResponseEntity<Present> createPresent(@RequestBody Present present) throws URISyntaxException {
        log.debug("REST request to save Present : {}", present);
        if (present.getId() != null) {
            throw new BadRequestAlertException("A new present cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Present result = presentService.save(present);
        return ResponseEntity.created(new URI("/api/present/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/presentSave")
    public void createPresent(@RequestBody List<Present> presents) {
        log.debug("REST request to save all Presents : {}", presents);

        presentService.saveAll(presents);
    }

    /**
     * {@code PUT  /presents} : Updates an existing present.
     *
     * @param present the present to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated present,
     * or with status {@code 400 (Bad Request)} if the present is not valid,
     * or with status {@code 500 (Internal Server Error)} if the present couldn't be updated.
     */
    @PutMapping("/presents")
    public ResponseEntity<Present> updatePresent(@RequestBody Present present) {
        log.debug("REST request to update Present : {}", present);
        if (present.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Present result = presentService.save(present);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, present.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /presents} : Updates an existing present.
     *
     * @param userId the userI%d to consume
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated present,
     * or with status {@code 400 (Bad Request)} if the present is not valid,
     * or with status {@code 500 (Internal Server Error)} if the present couldn't be updated.
     */
    @PostMapping("/consume")
    public ResponseEntity<Present> consume(@RequestBody Long userId) {
        log.debug("REST request to consume a Present");
        Optional<Present> optResult = presentService.consume(userId);
        Present result = optResult.get();
        if(optResult.isPresent()) {
            result = presentService.save(result);

            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
        return null;
    }

    /**
     * {@code GET  /presents} : get all the presents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of presents in body.
     */
    @GetMapping("/presents")
    public List<Present> getAllPresents() {
        log.debug("REST request to get all Presents");
        return presentService.findAll();
    }

    /**
     * {@code GET  /presents/:id} : get the "id" present.
     *
     * @param id the id of the present to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the present, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/presents/{id}")
    public ResponseEntity<Present> getPresent(@PathVariable Long id) {
        log.debug("REST request to get Present : {}", id);
        Optional<Present> present = presentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(present);
    }

    /**
     * {@code DELETE  /presents/:id} : delete the "id" present.
     *
     * @param id the id of the present to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/presents/{id}")
    public ResponseEntity<Void> deletePresent(@PathVariable Long id) {
        log.debug("REST request to delete Present : {}", id);
        presentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/presentsByUserAndPeriod")
    public List<Present> getPresentByUserAndPeriod(@RequestParam("userId") Long userId,
                                            @RequestParam("begin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin,
                                            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return  presentService.findByUserIdAndPeriod(userId, begin.toLocalDate(), end.toLocalDate());
    }


}
