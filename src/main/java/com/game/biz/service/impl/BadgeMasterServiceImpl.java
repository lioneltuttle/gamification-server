package com.game.biz.service.impl;

import com.game.biz.model.BadgeLegend;
import com.game.biz.model.BadgeMaster;
import com.game.biz.model.PointsAudit;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;
import com.game.biz.service.BadgeLegendService;
import com.game.biz.service.BadgeMasterService;
import com.game.biz.service.PointsAuditService;
import com.game.repository.biz.BadgeMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BadgeMaster}.
 */
@Service
@Transactional
public class BadgeMasterServiceImpl implements BadgeMasterService {

    private final Logger log = LoggerFactory.getLogger(BadgeMasterServiceImpl.class);

    private final BadgeMasterRepository badgeMasterRepository;

    private final BadgeLegendService badgeLegendService;

    private final PointsAuditService pointsAuditService;

    public BadgeMasterServiceImpl(BadgeMasterRepository badgeMasterRepository, BadgeLegendService badgeLegendService, PointsAuditService pointsAuditService) {
        this.badgeMasterRepository = badgeMasterRepository;
        this.badgeLegendService = badgeLegendService;
        this.pointsAuditService = pointsAuditService;
    }

    /**
     * Save a badgeMaster.
     *
     * @param badgeMaster the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BadgeMaster save(BadgeMaster badgeMaster) {
        log.debug("Request to save BadgeMaster : {}", badgeMaster);
        return badgeMasterRepository.save(badgeMaster);
    }

    /**
     * Get all the badgeMasters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BadgeMaster> findAll() {
        log.debug("Request to get all BadgeMasters");
        return badgeMasterRepository.findAll();
    }


    /**
     * Get one badgeMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeMaster> findOne(Long id) {
        log.debug("Request to get BadgeMaster : {}", id);
        return badgeMasterRepository.findById(id);
    }

    /**
     * Delete the badgeMaster by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgeMaster : {}", id);
        badgeMasterRepository.deleteById(id);
    }

    @Override
    public long getNbBadgesMaster(Long userId){
        BadgeMaster master =  badgeMasterRepository.findByUserId(userId).orElse(new BadgeMaster(userId));

        return master.getNbBadges();
    }

    @Override
    public void exchangeMasterForLegend(Long userID) throws NumberOfBadgesRequiredException {
        BadgeMaster badge = badgeMasterRepository.findByUserId(userID).orElse(new BadgeMaster(userID));
        int MASTER_FOR_LEGEND = 2;
        if(badge.getNbBadges() < MASTER_FOR_LEGEND){
            throw new NumberOfBadgesRequiredException("Pas assez de badges : " + badge.getNbBadges());
        }
        BadgeLegend legend = badgeLegendService.findByUserId(userID);
        int nbNew = 0;
        for(int i = badge.getNbBadges(); i >= MASTER_FOR_LEGEND; i = i- MASTER_FOR_LEGEND){
            badge.setNbBadges(badge.getNbBadges() - MASTER_FOR_LEGEND);
            legend.setNbBadges(legend.getNbBadges() +1);
            nbNew++;
        }
            PointsAudit audit = new PointsAudit().userId(userID).subject(EventType.EXCHANGE_MASTER_FOR_LEGEND).seen(true).value(""+nbNew);
        pointsAuditService.save(audit);
        badgeMasterRepository.save(badge);
        badgeLegendService.save(legend);
    }

    public void addOne(Long userId){
        BadgeMaster bm = badgeMasterRepository.findByUserId(userId).orElse(new BadgeMaster(userId));
        bm.setNbBadges(bm.getNbBadges()+1);
        save(bm);
    }
}
