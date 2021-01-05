package com.game.service;

import com.game.biz.model.R2LastWeekReportLine;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link R2LastWeekReportLine}.
 */
public interface R2LastWeekReportLineService {

    /**
     * Save a r2LastWeekReportLine.
     *
     * @param r2LastWeekReportLine the entity to save.
     * @return the persisted entity.
     */
    R2LastWeekReportLine save(R2LastWeekReportLine r2LastWeekReportLine);

    /**
     * Get all the r2LastWeekReportLines.
     *
     * @return the list of entities.
     */
    List<R2LastWeekReportLine> findAll();


    /**
     * Get the "id" r2LastWeekReportLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<R2LastWeekReportLine> findOne(Long id);

    /**
     * Delete the "id" r2LastWeekReportLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
