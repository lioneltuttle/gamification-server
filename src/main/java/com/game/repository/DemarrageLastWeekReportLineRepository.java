package com.game.repository;

import com.game.biz.model.DemarrageLastWeekReportLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DemarrageLastWeekReportLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemarrageLastWeekReportLineRepository extends JpaRepository<DemarrageLastWeekReportLine, Long> {

}
