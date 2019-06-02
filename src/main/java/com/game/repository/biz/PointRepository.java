package com.game.repository.biz;

import com.game.biz.model.Point;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the Point entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByUserIdAndDateGreaterThan(Long userId, LocalDate d);
}
