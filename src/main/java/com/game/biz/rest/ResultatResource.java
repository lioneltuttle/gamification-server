package com.game.biz.rest;

import com.game.biz.model.Resultat;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;
import com.game.biz.service.ResultatService;
import com.game.service.UserService;
import com.game.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link Resultat}.
 */
@RestController
@RequestMapping("/api")
public class ResultatResource {

    private final Logger log = LoggerFactory.getLogger(ResultatResource.class);

    private static final String ENTITY_NAME = "resultat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultatService resultatService;

    private final UserService userService;

    public ResultatResource(ResultatService resultatService, UserService userService) {
        this.resultatService = resultatService;
        this.userService = userService;
    }

    /**
     * {@code POST  /resultats} : Create a new resultat.
     *
     * @param resultat the resultat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultat, or with status {@code 400 (Bad Request)} if the resultat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultats")
    public ResponseEntity<Resultat> createResultat(@RequestBody Resultat resultat) throws URISyntaxException {
        log.debug("REST request to save Resultat : {}", resultat);
        if (resultat.getId() != null) {
            throw new BadRequestAlertException("A new resultat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resultat result = resultatService.save(resultat);
        return ResponseEntity.created(new URI("/api/resultats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultats} : Updates an existing resultat.
     *
     * @param resultat the resultat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultat,
     * or with status {@code 400 (Bad Request)} if the resultat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultat couldn't be updated.
     */
    @PutMapping("/resultats")
    public ResponseEntity<Resultat> updateResultat(@RequestBody Resultat resultat) {
        log.debug("REST request to update Resultat : {}", resultat);
        if (resultat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Resultat result = resultatService.save(resultat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resultat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultats} : get all the resultats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultats in body.
     */
    @GetMapping("/resultats")
    public List<Resultat> getAllResultats() {
        log.debug("REST request to get all Resultats");
        return resultatService.findAll();
    }

    /**
     * {@code GET  /resultats/:id} : get the "id" resultat.
     *
     * @param userId the id of the resultat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultats/{userId}")
    public List<Resultat> getResultat(@PathVariable Long userId) {
        log.debug("REST request to get Resultat : {}", userId);
        return resultatService.findByUserId(userId);
    }

    /**
     * {@code DELETE  /resultats/:id} : delete the "id" resultat.
     *
     * @param id the id of the resultat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultats/{id}")
    public ResponseEntity<Void> deleteResultat(@PathVariable Long id) {
        log.debug("REST request to delete Resultat : {}", id);
        resultatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/badge-pro/count/{userId}")
    public int countBadgesPro(@PathVariable Long userId){
        return resultatService.countBadgesPro(userId);
    }

    @GetMapping("/badge-pro/exchange/{userId}")
    public void exchangeProForMaster(@PathVariable Long userId) throws NumberOfBadgesRequiredException {
        log.debug("REST exchangeProForMaster for : {}", userId);
       resultatService.exchangeProForMaster(userId);
    }
}
