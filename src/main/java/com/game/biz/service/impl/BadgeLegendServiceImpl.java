package com.game.biz.service.impl;

import com.game.biz.model.BadgeLegend;
import com.game.biz.model.PointsAudit;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;
import com.game.biz.service.BadgeLegendService;
import com.game.biz.service.PointsAuditService;
import com.game.repository.biz.BadgeLegendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BadgeLegend}.
 */
@Service
@Transactional
public class BadgeLegendServiceImpl implements BadgeLegendService {

    private final Logger log = LoggerFactory.getLogger(BadgeLegendServiceImpl.class);

    private final BadgeLegendRepository badgeLegendRepository;

    private final PointsAuditService pointsAuditService;


    public BadgeLegendServiceImpl(BadgeLegendRepository badgeLegendRepository, PointsAuditService pointsAuditService) {
        this.badgeLegendRepository = badgeLegendRepository;
        this.pointsAuditService  = pointsAuditService;
    }

    /**
     * Save a badgeLegend.
     *
     * @param badgeLegend the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BadgeLegend save(BadgeLegend badgeLegend) {
        log.debug("Request to save BadgeLegend : {}", badgeLegend);
        return badgeLegendRepository.save(badgeLegend);
    }

    /**
     * Get all the badgeLegends.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BadgeLegend> findAll() {
        log.debug("Request to get all BadgeLegends");
        return badgeLegendRepository.findAll();
    }


    /**
     * Get one badgeLegend by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeLegend> findOne(Long id) {
        log.debug("Request to get BadgeLegend : {}", id);
        return badgeLegendRepository.findById(id);
    }

    /**
     * Delete the badgeLegend by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgeLegend : {}", id);
        badgeLegendRepository.deleteById(id);
    }

    @Override
    public BadgeLegend findByUserId(Long userId) {
        return badgeLegendRepository.findByUserId(userId).orElse(new BadgeLegend(userId));
    }

    @Override
    public long getNbBadgesLegend(Long userId) {
        return findByUserId(userId).getNbBadges();
    }

    @Override
    public void exchangeLegendForPresent(Long userID) throws NumberOfBadgesRequiredException {
        BadgeLegend badge = badgeLegendRepository.findByUserId(userID).orElseGet(BadgeLegend::new);
        int LEGEND_FOR_PRESENT = 3;
        if(badge.getNbBadges() < LEGEND_FOR_PRESENT){
            throw new NumberOfBadgesRequiredException("Pas assez de badges : " + badge.getNbBadges());
        }
        int nbNew = 0;
        for(int i = badge.getNbBadges(); i >= LEGEND_FOR_PRESENT; i = i- LEGEND_FOR_PRESENT){
            badge.setNbBadges(badge.getNbBadges() - LEGEND_FOR_PRESENT);
            nbNew++;
        }
        PointsAudit audit = new PointsAudit().userId(userID).subject(EventType.PRESENT_WON).seen(false).value(""+nbNew);
        pointsAuditService.save(audit);
        badgeLegendRepository.save(badge);
    }


}
