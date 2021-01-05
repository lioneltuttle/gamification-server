package com.game.biz.rest;

import com.game.biz.service.RecapService;
import com.game.biz.service.dto.RecapDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RecapServiceResource controller
 */
@RestController
@RequestMapping("/api/recap-service")
public class RecapResource {

    private final Logger log = LoggerFactory.getLogger(RecapResource.class);
    private RecapService recapService;

    public RecapResource(RecapService recapService) {
        this.recapService = recapService;
    }

    /**
    * GET getFullRecap
    */
    @GetMapping("/get-full-recap")
    public List<RecapDTO> getFullRecap() {
        return recapService.fullRecap();
    }

    /**
     * GET getInactiveRecap
     */
    @GetMapping("/get-inactive-recap")
    public List<RecapDTO> getInactiveRecap() {
        return recapService.inactiveRecap();
    }

    /**
     * GET getAdminRecap
     */
    @GetMapping("/get-admin-recap")
    public List<RecapDTO> getAdminRecap() {
        return recapService.adminRecap();
    }


    /**
    * GET getRecap
    */
    @GetMapping("/get-recap")
    public String getRecap() {
        return "getRecap";
    }

}
