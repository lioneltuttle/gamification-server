package com.game.biz.service;

import com.game.biz.model.Point;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Point}.
 */
public interface PointService {

    /**
     * Save a point.
     *
     * @param point the entity to save.
     * @return the persisted entity.
     */
    Point save(Point point);

    void saveAll(List<Point> points);

    /**
     * Get all the points.
     *
     * @return the list of entities.
     */
    List<Point> findAll();


    /**
     * Get the "id" point.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Point> findOne(Long id);

    /**
     * Delete the "id" point.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<Point> findLast2WByUserId(Long userId);
}
