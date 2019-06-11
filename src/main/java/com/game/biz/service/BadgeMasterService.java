package com.game.biz.service;

import com.game.biz.model.BadgeMaster;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BadgeMaster}.
 */
public interface BadgeMasterService {

    /**
     * Save a badgeMaster.
     *
     * @param badgeMaster the entity to save.
     * @return the persisted entity.
     */
    BadgeMaster save(BadgeMaster badgeMaster);

    /**
     * Get all the badgeMasters.
     *
     * @return the list of entities.
     */
    List<BadgeMaster> findAll();


    /**
     * Get the "id" badgeMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeMaster> findOne(Long id);

    /**
     * Delete the "id" badgeMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * counts the number of badges master
     * @param userId
     * @return
     */
    long getNbBadgesMaster(Long userId);

    void exchangeMasterForLegend(Long userID) throws NumberOfBadgesRequiredException;

    void addOne(Long userId);
}
