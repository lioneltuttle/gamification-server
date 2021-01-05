package com.game.repository;

import com.game.biz.model.DemarrageLastWeekReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DemarrageLastWeekReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemarrageLastWeekReportRepository extends JpaRepository<DemarrageLastWeekReport, Long> {

}
