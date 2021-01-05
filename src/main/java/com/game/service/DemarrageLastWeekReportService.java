package com.game.service;

import com.game.biz.model.DemarrageLastWeekReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DemarrageLastWeekReport}.
 */
public interface DemarrageLastWeekReportService {

    /**
     * Save a demarrageLastWeekReport.
     *
     * @param demarrageLastWeekReport the entity to save.
     * @return the persisted entity.
     */
    DemarrageLastWeekReport save(DemarrageLastWeekReport demarrageLastWeekReport);

    /**
     * Get all the demarrageLastWeekReports.
     *
     * @return the list of entities.
     */
    List<DemarrageLastWeekReport> findAll();


    /**
     * Get the "id" demarrageLastWeekReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemarrageLastWeekReport> findOne(Long id);

    /**
     * Delete the "id" demarrageLastWeekReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
