package com.game.biz.service.impl;

import com.game.biz.model.BadgeMaster;
import com.game.biz.model.Present;
import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.service.PresentService;
import com.game.biz.service.ResultatService;
import com.game.repository.biz.PresentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Present}.
 */
@Service
@Transactional
public class PresentServiceImpl implements PresentService {

    private final Logger log = LoggerFactory.getLogger(PresentServiceImpl.class);

    private final PresentRepository presentRepository;

    private ResultatService resultatService;


    public PresentServiceImpl(PresentRepository presentRepository, ResultatService resultatService) {
        this.presentRepository = presentRepository;
        this.resultatService = resultatService;
    }

    /**
     * Save a present.
     *
     * @param present the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Present save(Present present) {
        log.debug("Request to save Present : {}", present);
        Present present_retour =  presentRepository.save(present);
        return present_retour;
    }

    @Override
    public void saveAll(List<Present> presents) {
        presents.stream().forEach(this::save);
    }

    /**
     * Get all the presents.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Present> findAll() {
        log.debug("Request to get all Presents");
        return presentRepository.findAll();
    }


    /**
     * Get one present by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Present> findOne(Long id) {
        log.debug("Request to get Present : {}", id);
        return presentRepository.findById(id);
    }

    @Override
    public Optional<Present> consume(Long userId) {
        List<Present> presents = findByUserId(userId).stream().filter(p -> !p.getConsumed()).collect(Collectors.toList());

        if(!presents.isEmpty()){
            final Present present = presents.get(0);
            present.setConsumed(true);
            return Optional.ofNullable(present);
        }
        return Optional.empty();
    }

    /**
     * Delete the present by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Present : {}", id);
        presentRepository.deleteById(id);
    }

    @Override
    public Integer getNbConsumedPresents(Long userId) {
        List<Present> allPresents = presentRepository.findAllByUserId(userId);
        if(allPresents != null && !allPresents.isEmpty()) {
            return (int) allPresents.stream()
                .filter(p ->p.getConsumed() == true)
                .count();
        }
        return 0;
    }

    @Override
    public Integer getNbPendingPresents(Long userId) {
        List<Present> allPresents = presentRepository.findAllByUserId(userId);
        if(allPresents != null && !allPresents.isEmpty()) {
            return (int) allPresents.stream()
                .filter(e -> e.getConsumed() != true)
                .count();
        }
        return 0;
    }


    @Override
    public List<Present> findByUserIdAndPeriod(Long userId, LocalDate begin, LocalDate end) {
        return presentRepository.findAllByUserIdAndDateBetween(userId, begin, end);
    }

    @Override
    public List<Present> findByUserId(Long userId) {
        return presentRepository.findAllByUserId(userId);
    }


}
