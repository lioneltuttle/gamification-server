package com.game.repository;

import com.game.biz.model.R2LastWeekReport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the R2LastWeekReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface R2LastWeekReportRepository extends JpaRepository<R2LastWeekReport, Long> {

}
