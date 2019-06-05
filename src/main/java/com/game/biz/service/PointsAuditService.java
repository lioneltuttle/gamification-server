package com.game.biz.service;

import com.game.biz.model.PointsAudit;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PointsAudit}.
 */
public interface PointsAuditService {

    /**
     * Save a pointsAudit.
     *
     * @param pointsAudit the entity to save.
     * @return the persisted entity.
     */
    PointsAudit save(PointsAudit pointsAudit);

    /**
     * Get all the pointsAudits.
     *
     * @return the list of entities.
     */
    List<PointsAudit> findAll();


    /**
     * Get the "id" pointsAudit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PointsAudit> findOne(Long id);

    /**
     * Delete the "id" pointsAudit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<PointsAudit> findAllUnseen(Long userId);
}
