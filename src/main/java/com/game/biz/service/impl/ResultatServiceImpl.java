package com.game.biz.service.impl;

import com.game.biz.model.BadgeMaster;
import com.game.biz.model.PointsAudit;
import com.game.biz.model.enumeration.BadgeType;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;
import com.game.biz.service.BadgeMasterService;
import com.game.biz.service.PointsAuditService;
import com.game.biz.service.ResultatService;
import com.game.biz.model.Resultat;
import com.game.repository.biz.ResultatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Resultat}.
 */
@Service
@Transactional
public class ResultatServiceImpl implements ResultatService {

    private final Logger log = LoggerFactory.getLogger(ResultatServiceImpl.class);

    private final ResultatRepository resultatRepository;

    private final BadgeMasterService badgeMasterService;

    private final PointsAuditService pointsAuditService;

    private static int PRO_FOR_MASTER = 3;

    public ResultatServiceImpl(ResultatRepository resultatRepository, BadgeMasterService badgeMasterService, PointsAuditService pointsAuditService) {
        this.resultatRepository = resultatRepository;
        this.badgeMasterService = badgeMasterService;
        this.pointsAuditService = pointsAuditService;
    }

    /**
     * Save a resultat.
     *
     * @param resultat the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Resultat save(Resultat resultat) {
        log.debug("Request to save Resultat : {}", resultat);
        return resultatRepository.save(resultat);
    }

    /**
     * Get all the resultats.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Resultat> findAll() {
        log.debug("Request to get all Resultats");
        return resultatRepository.findAll();
    }


    /**
     * Get one resultat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Resultat> findOne(Long id) {
        log.debug("Request to get Resultat : {}", id);
        return resultatRepository.findById(id);
    }

    /**
     * Delete the resultat by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resultat : {}", id);
        resultatRepository.deleteById(id);
    }

    @Override
    public List<Resultat> findByUserId(Long id) {
        return resultatRepository.findByUserId(id);
    }

    @Override
    public Optional<Resultat> findByUserIdAndCategorie(Long id, BadgeType badgeType) {
        return resultatRepository.findByUserIdAndCategorie(id, badgeType);
    }

    @Override
    public void resetMonthPoints() {
        resultatRepository.findAll().stream().forEach(resultat -> resultat.setPoints(0));
    }

    @Override
    public int countBadgesPro(Long userId) {
        return resultatRepository.sumOfBadgesProByUserd(userId).orElse(0);
    }

    @Override
    public void exchangeProForMaster(Long userID) throws NumberOfBadgesRequiredException {
        int badgesProNeeded = PRO_FOR_MASTER;

            for( BadgeType type : BadgeType.values()){
                Resultat res = resultatRepository.findByUserIdAndCategorie(userID,type).orElseGet(Resultat::new);
                if(res.getNbBadges() > 0){
                    if(res.getNbBadges() >= badgesProNeeded){
                        res.setNbBadges( res.getNbBadges() - badgesProNeeded);
                        save(res);
                        badgeMasterService.addOne(userID);
                        PointsAudit pa = new PointsAudit().userId(userID).subject(EventType.EXCHANGE_PRO_FOR_MASTER).value(type.name()).seen(true);
                        pointsAuditService.save(pa);
                        return;
                    } else {
                        badgesProNeeded -= res.getNbBadges();
                        res.setNbBadges(0);
                        save(res);
                        PointsAudit pa = new PointsAudit().userId(userID).subject(EventType.EXCHANGE_PRO_FOR_MASTER).value(type.name()).seen(true);
                        pointsAuditService.save(pa);
                    }
                }
            }
        throw new NumberOfBadgesRequiredException();
    }
}
