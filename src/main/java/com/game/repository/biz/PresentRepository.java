package com.game.repository.biz;

import com.game.biz.model.Present;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Present entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresentRepository extends JpaRepository<Present, Long> {

    List<Present> findAllByUserIdAndDateGreaterThan(Long userId, LocalDate d);

    List<Present> findAllByUserId(Long userId);

    List<Present> findAllByUserIdAndDateBetween(Long userId, LocalDate begin, LocalDate end);

    List<Present> findAllByDateBetween(LocalDate begin, LocalDate end);
}
