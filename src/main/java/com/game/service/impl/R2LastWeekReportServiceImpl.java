package com.game.service.impl;

import com.game.service.R2LastWeekReportService;
import com.game.biz.model.R2LastWeekReport;
import com.game.repository.R2LastWeekReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link R2LastWeekReport}.
 */
@Service
@Transactional
public class R2LastWeekReportServiceImpl implements R2LastWeekReportService {

    private final Logger log = LoggerFactory.getLogger(R2LastWeekReportServiceImpl.class);

    private final R2LastWeekReportRepository r2LastWeekReportRepository;

    public R2LastWeekReportServiceImpl(R2LastWeekReportRepository r2LastWeekReportRepository) {
        this.r2LastWeekReportRepository = r2LastWeekReportRepository;
    }

    /**
     * Save a r2LastWeekReport.
     *
     * @param r2LastWeekReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public R2LastWeekReport save(R2LastWeekReport r2LastWeekReport) {
        log.debug("Request to save R2LastWeekReport : {}", r2LastWeekReport);
        return r2LastWeekReportRepository.save(r2LastWeekReport);
    }

    /**
     * Get all the r2LastWeekReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<R2LastWeekReport> findAll() {
        log.debug("Request to get all R2LastWeekReports");
        return r2LastWeekReportRepository.findAll();
    }


    /**
     * Get one r2LastWeekReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<R2LastWeekReport> findOne(Long id) {
        log.debug("Request to get R2LastWeekReport : {}", id);
        return r2LastWeekReportRepository.findById(id);
    }

    /**
     * Delete the r2LastWeekReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete R2LastWeekReport : {}", id);
        r2LastWeekReportRepository.deleteById(id);
    }
}
