package com.game.biz.service.impl;

import com.game.biz.service.BadgeMasterService;
import com.game.biz.model.BadgeMaster;
import com.game.repository.biz.BadgeMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BadgeMaster}.
 */
@Service
@Transactional
public class BadgeMasterServiceImpl implements BadgeMasterService {

    private final Logger log = LoggerFactory.getLogger(BadgeMasterServiceImpl.class);

    private final BadgeMasterRepository badgeMasterRepository;

    public BadgeMasterServiceImpl(BadgeMasterRepository badgeMasterRepository) {
        this.badgeMasterRepository = badgeMasterRepository;
    }

    /**
     * Save a badgeMaster.
     *
     * @param badgeMaster the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BadgeMaster save(BadgeMaster badgeMaster) {
        log.debug("Request to save BadgeMaster : {}", badgeMaster);
        return badgeMasterRepository.save(badgeMaster);
    }

    /**
     * Get all the badgeMasters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BadgeMaster> findAll() {
        log.debug("Request to get all BadgeMasters");
        return badgeMasterRepository.findAll();
    }


    /**
     * Get one badgeMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeMaster> findOne(Long id) {
        log.debug("Request to get BadgeMaster : {}", id);
        return badgeMasterRepository.findById(id);
    }

    /**
     * Delete the badgeMaster by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgeMaster : {}", id);
        badgeMasterRepository.deleteById(id);
    }
}
