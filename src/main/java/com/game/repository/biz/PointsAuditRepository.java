package com.game.repository.biz;

import com.game.biz.model.PointsAudit;
import com.game.biz.model.enumeration.EventType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the PointsAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointsAuditRepository extends JpaRepository<PointsAudit, Long>, JpaSpecificationExecutor<PointsAudit> {

    List<PointsAudit> findAllByUserIdAndSeen(Long userId, Boolean seen);

    List<PointsAudit> findAllByUserIdAndSubjectAndSeen(Long userId, EventType subject, Boolean seen);
}
