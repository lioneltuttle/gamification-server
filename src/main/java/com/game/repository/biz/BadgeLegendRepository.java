package com.game.repository.biz;

import com.game.biz.model.BadgeLegend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the BadgeLegend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeLegendRepository extends JpaRepository<BadgeLegend, Long> {
    Optional<BadgeLegend> findByUserId(Long userId);
}
