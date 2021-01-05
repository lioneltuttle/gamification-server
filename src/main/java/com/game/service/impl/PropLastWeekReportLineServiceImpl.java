package com.game.service.impl;

import com.game.service.PropLastWeekReportLineService;
import com.game.biz.model.PropLastWeekReportLine;
import com.game.repository.PropLastWeekReportLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PropLastWeekReportLine}.
 */
@Service
@Transactional
public class PropLastWeekReportLineServiceImpl implements PropLastWeekReportLineService {

    private final Logger log = LoggerFactory.getLogger(PropLastWeekReportLineServiceImpl.class);

    private final PropLastWeekReportLineRepository propLastWeekReportLineRepository;

    public PropLastWeekReportLineServiceImpl(PropLastWeekReportLineRepository propLastWeekReportLineRepository) {
        this.propLastWeekReportLineRepository = propLastWeekReportLineRepository;
    }

    /**
     * Save a propLastWeekReportLine.
     *
     * @param propLastWeekReportLine the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PropLastWeekReportLine save(PropLastWeekReportLine propLastWeekReportLine) {
        log.debug("Request to save PropLastWeekReportLine : {}", propLastWeekReportLine);
        return propLastWeekReportLineRepository.save(propLastWeekReportLine);
    }

    /**
     * Get all the propLastWeekReportLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PropLastWeekReportLine> findAll() {
        log.debug("Request to get all PropLastWeekReportLines");
        return propLastWeekReportLineRepository.findAll();
    }


    /**
     * Get one propLastWeekReportLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PropLastWeekReportLine> findOne(Long id) {
        log.debug("Request to get PropLastWeekReportLine : {}", id);
        return propLastWeekReportLineRepository.findById(id);
    }

    /**
     * Delete the propLastWeekReportLine by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PropLastWeekReportLine : {}", id);
        propLastWeekReportLineRepository.deleteById(id);
    }
}
