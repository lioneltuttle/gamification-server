package com.game.service.impl;

import com.game.service.PropLastWeekReportService;
import com.game.biz.model.PropLastWeekReport;
import com.game.repository.PropLastWeekReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PropLastWeekReport}.
 */
@Service
@Transactional
public class PropLastWeekReportServiceImpl implements PropLastWeekReportService {

    private final Logger log = LoggerFactory.getLogger(PropLastWeekReportServiceImpl.class);

    private final PropLastWeekReportRepository propLastWeekReportRepository;

    public PropLastWeekReportServiceImpl(PropLastWeekReportRepository propLastWeekReportRepository) {
        this.propLastWeekReportRepository = propLastWeekReportRepository;
    }

    /**
     * Save a propLastWeekReport.
     *
     * @param propLastWeekReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PropLastWeekReport save(PropLastWeekReport propLastWeekReport) {
        log.debug("Request to save PropLastWeekReport : {}", propLastWeekReport);
        return propLastWeekReportRepository.save(propLastWeekReport);
    }

    /**
     * Get all the propLastWeekReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PropLastWeekReport> findAll() {
        log.debug("Request to get all PropLastWeekReports");
        return propLastWeekReportRepository.findAll();
    }


    /**
     * Get one propLastWeekReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PropLastWeekReport> findOne(Long id) {
        log.debug("Request to get PropLastWeekReport : {}", id);
        return propLastWeekReportRepository.findById(id);
    }

    /**
     * Delete the propLastWeekReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PropLastWeekReport : {}", id);
        propLastWeekReportRepository.deleteById(id);
    }
}
