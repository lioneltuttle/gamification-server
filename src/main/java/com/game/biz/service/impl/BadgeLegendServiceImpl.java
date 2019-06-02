package com.game.biz.service.impl;

import com.game.biz.service.BadgeLegendService;
import com.game.biz.model.BadgeLegend;
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

    public BadgeLegendServiceImpl(BadgeLegendRepository badgeLegendRepository) {
        this.badgeLegendRepository = badgeLegendRepository;
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
}
