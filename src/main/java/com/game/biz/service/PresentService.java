package com.game.biz.service;

import com.game.biz.model.Present;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Present}.
 */
public interface PresentService {

    /**
     * Save a present.
     *
     * @param present the entity to save.
     * @return the persisted entity.
     */
    Present save(Present present);

    void saveAll(List<Present> presents);

    /**
     * Get all the presents.
     *
     * @return the list of entities.
     */
    List<Present> findAll();


    /**
     * Get the "id" present.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Present> findOne(Long id);

    Optional<Present> consume(Long userId);

    /**
     * Delete the "id" present.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

     Integer getNbConsumedPresents(Long userId);
     Integer getNbPendingPresents(Long userId);

    List<Present> findByUserIdAndPeriod(Long userId, LocalDate begin, LocalDate end);
    List<Present> findByUserId(Long userId);
}
