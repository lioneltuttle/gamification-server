package com.game.repository.biz;

import com.game.biz.model.BadgeLegend;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BadgeLegend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeLegendRepository extends JpaRepository<BadgeLegend, Long> {

}
