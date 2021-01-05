package com.game.service;

import com.game.biz.model.R2LastWeekReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link R2LastWeekReport}.
 */
public interface R2LastWeekReportService {

    /**
     * Save a r2LastWeekReport.
     *
     * @param r2LastWeekReport the entity to save.
     * @return the persisted entity.
     */
    R2LastWeekReport save(R2LastWeekReport r2LastWeekReport);

    /**
     * Get all the r2LastWeekReports.
     *
     * @return the list of entities.
     */
    List<R2LastWeekReport> findAll();


    /**
     * Get the "id" r2LastWeekReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<R2LastWeekReport> findOne(Long id);

    /**
     * Delete the "id" r2LastWeekReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
