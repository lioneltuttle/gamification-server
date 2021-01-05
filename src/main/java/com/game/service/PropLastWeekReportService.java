package com.game.service;

import com.game.biz.model.PropLastWeekReport;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PropLastWeekReport}.
 */
public interface PropLastWeekReportService {

    /**
     * Save a propLastWeekReport.
     *
     * @param propLastWeekReport the entity to save.
     * @return the persisted entity.
     */
    PropLastWeekReport save(PropLastWeekReport propLastWeekReport);

    /**
     * Get all the propLastWeekReports.
     *
     * @return the list of entities.
     */
    List<PropLastWeekReport> findAll();


    /**
     * Get the "id" propLastWeekReport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropLastWeekReport> findOne(Long id);

    /**
     * Delete the "id" propLastWeekReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
