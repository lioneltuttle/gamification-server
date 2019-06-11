package com.game.biz.service;

import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.BadgeType;
import com.game.biz.model.exception.NumberOfBadgesRequiredException;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Resultat}.
 */
public interface ResultatService {

    /**
     * Save a resultat.
     *
     * @param resultat the entity to save.
     * @return the persisted entity.
     */
    Resultat save(Resultat resultat);

    /**
     * Get all the resultats.
     *
     * @return the list of entities.
     */
    List<Resultat> findAll();


    /**
     * Get the "id" resultat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Resultat> findOne(Long id);

    /**
     * Get the "id" resultat.
     *
     * @param id the id of the user.
     * @return the entity.
     */
    List<Resultat> findByUserId(Long id);

    Optional<Resultat> findByUserIdAndCategorie(Long id, BadgeType badgeType);

    /**
     * Delete the "id" resultat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * resets everybody's point to 0
     */
    void resetMonthPoints();

    int countBadgesPro(Long userId);

    void exchangeProForMaster(Long userID) throws NumberOfBadgesRequiredException;
}
