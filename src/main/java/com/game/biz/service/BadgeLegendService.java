package com.game.biz.service;

import com.game.biz.model.BadgeLegend;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BadgeLegend}.
 */
public interface BadgeLegendService {

    /**
     * Save a badgeLegend.
     *
     * @param badgeLegend the entity to save.
     * @return the persisted entity.
     */
    BadgeLegend save(BadgeLegend badgeLegend);

    /**
     * Get all the badgeLegends.
     *
     * @return the list of entities.
     */
    List<BadgeLegend> findAll();


    /**
     * Get the "id" badgeLegend.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeLegend> findOne(Long id);

    /**
     * Delete the "id" badgeLegend.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
