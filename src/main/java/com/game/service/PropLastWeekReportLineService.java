package com.game.service;

import com.game.biz.model.PropLastWeekReportLine;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PropLastWeekReportLine}.
 */
public interface PropLastWeekReportLineService {

    /**
     * Save a propLastWeekReportLine.
     *
     * @param propLastWeekReportLine the entity to save.
     * @return the persisted entity.
     */
    PropLastWeekReportLine save(PropLastWeekReportLine propLastWeekReportLine);

    /**
     * Get all the propLastWeekReportLines.
     *
     * @return the list of entities.
     */
    List<PropLastWeekReportLine> findAll();


    /**
     * Get the "id" propLastWeekReportLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PropLastWeekReportLine> findOne(Long id);

    /**
     * Delete the "id" propLastWeekReportLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
