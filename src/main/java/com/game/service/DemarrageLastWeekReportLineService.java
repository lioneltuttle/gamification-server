package com.game.service;

import com.game.biz.model.DemarrageLastWeekReportLine;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DemarrageLastWeekReportLine}.
 */
public interface DemarrageLastWeekReportLineService {

    /**
     * Save a demarrageLastWeekReportLine.
     *
     * @param demarrageLastWeekReportLine the entity to save.
     * @return the persisted entity.
     */
    DemarrageLastWeekReportLine save(DemarrageLastWeekReportLine demarrageLastWeekReportLine);

    /**
     * Get all the demarrageLastWeekReportLines.
     *
     * @return the list of entities.
     */
    List<DemarrageLastWeekReportLine> findAll();


    /**
     * Get the "id" demarrageLastWeekReportLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemarrageLastWeekReportLine> findOne(Long id);

    /**
     * Delete the "id" demarrageLastWeekReportLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
