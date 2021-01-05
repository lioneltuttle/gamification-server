package com.game.repository;

import com.game.biz.model.R2LastWeekReportLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the R2LastWeekReportLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface R2LastWeekReportLineRepository extends JpaRepository<R2LastWeekReportLine, Long> {

}
