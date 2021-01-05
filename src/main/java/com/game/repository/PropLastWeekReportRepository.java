package com.game.repository;

import com.game.biz.model.PropLastWeekReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PropLastWeekReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropLastWeekReportRepository extends JpaRepository<PropLastWeekReport, Long> {

}
