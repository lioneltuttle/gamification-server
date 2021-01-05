package com.game.repository;

import com.game.biz.model.PropLastWeekReportLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PropLastWeekReportLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropLastWeekReportLineRepository extends JpaRepository<PropLastWeekReportLine, Long> {

}
