package com.game.service.impl;

import com.game.service.DemarrageLastWeekReportLineService;
import com.game.biz.model.DemarrageLastWeekReportLine;
import com.game.repository.DemarrageLastWeekReportLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DemarrageLastWeekReportLine}.
 */
@Service
@Transactional
public class DemarrageLastWeekReportLineServiceImpl implements DemarrageLastWeekReportLineService {

    private final Logger log = LoggerFactory.getLogger(DemarrageLastWeekReportLineServiceImpl.class);

    private final DemarrageLastWeekReportLineRepository demarrageLastWeekReportLineRepository;

    public DemarrageLastWeekReportLineServiceImpl(DemarrageLastWeekReportLineRepository demarrageLastWeekReportLineRepository) {
        this.demarrageLastWeekReportLineRepository = demarrageLastWeekReportLineRepository;
    }

    /**
     * Save a demarrageLastWeekReportLine.
     *
     * @param demarrageLastWeekReportLine the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DemarrageLastWeekReportLine save(DemarrageLastWeekReportLine demarrageLastWeekReportLine) {
        log.debug("Request to save DemarrageLastWeekReportLine : {}", demarrageLastWeekReportLine);
        return demarrageLastWeekReportLineRepository.save(demarrageLastWeekReportLine);
    }

    /**
     * Get all the demarrageLastWeekReportLines.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DemarrageLastWeekReportLine> findAll() {
        log.debug("Request to get all DemarrageLastWeekReportLines");
        return demarrageLastWeekReportLineRepository.findAll();
    }


    /**
     * Get one demarrageLastWeekReportLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DemarrageLastWeekReportLine> findOne(Long id) {
        log.debug("Request to get DemarrageLastWeekReportLine : {}", id);
        return demarrageLastWeekReportLineRepository.findById(id);
    }

    /**
     * Delete the demarrageLastWeekReportLine by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemarrageLastWeekReportLine : {}", id);
        demarrageLastWeekReportLineRepository.deleteById(id);
    }
}
