package com.game.service.impl;

import com.game.service.R2LastWeekReportLineService;
import com.game.biz.model.R2LastWeekReportLine;
import com.game.repository.R2LastWeekReportLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link R2LastWeekReportLine}.
 */
@Service
@Transactional
public class R2LastWeekReportLineServiceImpl implements R2LastWeekReportLineService {

    private final Logger log = LoggerFactory.getLogger(R2LastWeekReportLineServiceImpl.class);

    private final R2LastWeekReportLineRepository r2LastWeekReportLineRepository;

    public R2LastWeekReportLineServiceImpl(R2LastWeekReportLineRepository r2LastWeekReportLineRepository) {
        this.r2LastWeekReportLineRepository = r2LastWeekReportLineRepository;
    }

    /**
     * Save a r2LastWeekReportLine.
     *
     * @param r2LastWeekReportLine the entity to save.
     * @return the persisted entity.
     */
    @Override
    public R2LastWeekReportLine save(R2LastWeekReportLine r2LastWeekReportLine) {
        log.debug("Request to save R2LastWeekReportLine : {}", r2LastWeekReportLine);
        return r2LastWeekReportLineRepository.save(r2LastWeekReportLine);
    }

    /**
     * Get all the r2LastWeekReportLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<R2LastWeekReportLine> findAll() {
        log.debug("Request to get all R2LastWeekReportLines");
        return r2LastWeekReportLineRepository.findAll();
    }


    /**
     * Get one r2LastWeekReportLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<R2LastWeekReportLine> findOne(Long id) {
        log.debug("Request to get R2LastWeekReportLine : {}", id);
        return r2LastWeekReportLineRepository.findById(id);
    }

    /**
     * Delete the r2LastWeekReportLine by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete R2LastWeekReportLine : {}", id);
        r2LastWeekReportLineRepository.deleteById(id);
    }
}
