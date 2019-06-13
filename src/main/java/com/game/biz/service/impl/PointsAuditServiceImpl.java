package com.game.biz.service.impl;

import com.game.biz.model.PointsAudit;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.service.PointsAuditService;
import com.game.repository.biz.PointsAuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PointsAudit}.
 */
@Service
@Transactional
public class PointsAuditServiceImpl implements PointsAuditService {

    private final Logger log = LoggerFactory.getLogger(PointsAuditServiceImpl.class);

    private final PointsAuditRepository pointsAuditRepository;

    public PointsAuditServiceImpl(PointsAuditRepository pointsAuditRepository) {
        this.pointsAuditRepository = pointsAuditRepository;
    }

    /**
     * Save a pointsAudit.
     *
     * @param pointsAudit the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PointsAudit save(PointsAudit pointsAudit) {
        log.debug("Request to save PointsAudit : {}", pointsAudit);
        return pointsAuditRepository.save(pointsAudit);
    }

    /**
     * Get all the pointsAudits.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PointsAudit> findAll() {
        log.debug("Request to get all PointsAudits");
        return pointsAuditRepository.findAll();
    }


    /**
     * Get one pointsAudit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PointsAudit> findOne(Long id) {
        log.debug("Request to get PointsAudit : {}", id);
        return pointsAuditRepository.findById(id);
    }

    /**
     * Delete the pointsAudit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PointsAudit : {}", id);
        pointsAuditRepository.deleteById(id);
    }

    @Override
    public List<PointsAudit> findAllUnseen(Long userId) {
        return pointsAuditRepository.findAllByUserIdAndSeen(userId, false);
    }

    @Override
    public List<PointsAudit> findAllBadgesProUnseen(Long userId) {
        return pointsAuditRepository.findAllByUserIdAndSubjectAndSeen(userId, EventType.BADGE_PRO, false);
    }
}
