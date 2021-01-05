package com.game.service.impl;

import com.game.service.DemarrageLastWeekReportService;
import com.game.biz.model.DemarrageLastWeekReport;
import com.game.repository.DemarrageLastWeekReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DemarrageLastWeekReport}.
 */
@Service
@Transactional
public class DemarrageLastWeekReportServiceImpl implements DemarrageLastWeekReportService {

    private final Logger log = LoggerFactory.getLogger(DemarrageLastWeekReportServiceImpl.class);

    private final DemarrageLastWeekReportRepository demarrageLastWeekReportRepository;

    public DemarrageLastWeekReportServiceImpl(DemarrageLastWeekReportRepository demarrageLastWeekReportRepository) {
        this.demarrageLastWeekReportRepository = demarrageLastWeekReportRepository;
    }

    /**
     * Save a demarrageLastWeekReport.
     *
     * @param demarrageLastWeekReport the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DemarrageLastWeekReport save(DemarrageLastWeekReport demarrageLastWeekReport) {
        log.debug("Request to save DemarrageLastWeekReport : {}", demarrageLastWeekReport);
        return demarrageLastWeekReportRepository.save(demarrageLastWeekReport);
    }

    /**
     * Get all the demarrageLastWeekReports.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DemarrageLastWeekReport> findAll() {
        log.debug("Request to get all DemarrageLastWeekReports");
        return demarrageLastWeekReportRepository.findAll();
    }


    /**
     * Get one demarrageLastWeekReport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DemarrageLastWeekReport> findOne(Long id) {
        log.debug("Request to get DemarrageLastWeekReport : {}", id);
        return demarrageLastWeekReportRepository.findById(id);
    }

    /**
     * Delete the demarrageLastWeekReport by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemarrageLastWeekReport : {}", id);
        demarrageLastWeekReportRepository.deleteById(id);
    }
}
