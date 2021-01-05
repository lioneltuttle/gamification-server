package com.game.repository;

import com.game.biz.model.Point;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Point entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

}
