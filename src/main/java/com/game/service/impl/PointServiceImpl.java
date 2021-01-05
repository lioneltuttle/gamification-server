package com.game.service.impl;

import com.game.biz.model.Point;
import com.game.service.PointService;
import com.game.repository.PointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Point}.
 */
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final Logger log = LoggerFactory.getLogger(PointServiceImpl.class);

    private final PointRepository pointRepository;

    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    /**
     * Save a point.
     *
     * @param point the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Point save(Point point) {
        log.debug("Request to save Point : {}", point);
        return pointRepository.save(point);
    }

    /**
     * Get all the points.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Point> findAll() {
        log.debug("Request to get all Points");
        return pointRepository.findAll();
    }


    /**
     * Get one point by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Point> findOne(Long id) {
        log.debug("Request to get Point : {}", id);
        return pointRepository.findById(id);
    }

    /**
     * Delete the point by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Point : {}", id);
        pointRepository.deleteById(id);
    }
}
