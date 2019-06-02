package com.game.biz.service.impl;

import com.game.biz.model.enumeration.BadgeType;
import com.game.biz.service.ResultatService;
import com.game.biz.model.Resultat;
import com.game.repository.biz.ResultatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Resultat}.
 */
@Service
@Transactional
public class ResultatServiceImpl implements ResultatService {

    private final Logger log = LoggerFactory.getLogger(ResultatServiceImpl.class);

    private final ResultatRepository resultatRepository;

    public ResultatServiceImpl(ResultatRepository resultatRepository) {
        this.resultatRepository = resultatRepository;
    }

    /**
     * Save a resultat.
     *
     * @param resultat the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Resultat save(Resultat resultat) {
        log.debug("Request to save Resultat : {}", resultat);
        return resultatRepository.save(resultat);
    }

    /**
     * Get all the resultats.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Resultat> findAll() {
        log.debug("Request to get all Resultats");
        return resultatRepository.findAll();
    }


    /**
     * Get one resultat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Resultat> findOne(Long id) {
        log.debug("Request to get Resultat : {}", id);
        return resultatRepository.findById(id);
    }

    /**
     * Delete the resultat by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resultat : {}", id);
        resultatRepository.deleteById(id);
    }

    @Override
    public List<Resultat> findByUserId(Long id) {
        return resultatRepository.findByUserId(id);
    }

    @Override
    public Optional<Resultat> findByUserIdAndCategorie(Long id, BadgeType badgeType) {
        return resultatRepository.findByUserIdAndCategorie(id, badgeType);
    }

    @Override
    public void resetMonthPoints() {
        resultatRepository.findAll().stream().forEach(resultat -> resultat.setPoints(0));
    }
}
