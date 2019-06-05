package com.game.repository.biz;

import com.game.biz.model.BadgeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the BadgeMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeMasterRepository extends JpaRepository<BadgeMaster, Long> {

    Optional<BadgeMaster> findByUserId(Long userId);
}
