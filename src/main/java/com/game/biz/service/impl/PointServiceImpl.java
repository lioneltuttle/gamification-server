package com.game.biz.service.impl;

import com.game.biz.model.Point;
import com.game.biz.model.PointsAudit;
import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.EventType;
import com.game.biz.service.PointService;
import com.game.biz.service.PointsAuditService;
import com.game.biz.service.ResultatService;
import com.game.repository.biz.PointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Point}.
 */
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final Logger log = LoggerFactory.getLogger(PointServiceImpl.class);

    private final PointRepository pointRepository;

    private ResultatService resultatService;

    private PointsAuditService pointsAuditService;

    public PointServiceImpl(PointRepository pointRepository, ResultatService resultatService, PointsAuditService pointsAuditService) {
        this.pointRepository = pointRepository;
        this.resultatService = resultatService;
        this.pointsAuditService = pointsAuditService;
    }

    /**
     * Save a point.
     *
     * @param point the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Point save(Point point) {
        log.debug("Request to save Point : {}", point);
        Point point_retour =  pointRepository.save(point);
        Resultat resultat = resultatService.findByUserIdAndCategorie(point.getUserId(), point.getCategorie()).orElseGet(Resultat::new);
        resultat.setCategorie(point.getCategorie());
        resultat.setUserId(point.getUserId());
        int newBadges = resultat.addPoints(point.getNbPoints());
        resultatService.save(resultat);

        //audit the code
        if(newBadges != 0){
            PointsAudit pa = new PointsAudit()
                .userId(point.getUserId())
                .seen(false)
                .subject(EventType.BADGE_PRO)
                .value(newBadges + " nouveau(x) badge(s) " + point.getCategorie() )
                .date(point.getDate());
            pointsAuditService.save(pa);
        }

        return point_retour;
    }

    @Override
    public void saveAll(List<Point> points) {
        points.stream().forEach(this::save);
    }

    /**
     * Get all the points.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Point> findAll() {
        log.debug("Request to get all Points");
        return pointRepository.findAll();
    }


    /**
     * Get one point by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Point> findOne(Long id) {
        log.debug("Request to get Point : {}", id);
        return pointRepository.findById(id);
    }

    /**
     * Delete the point by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Point : {}", id);
        pointRepository.deleteById(id);
    }

    @Override
    public List<Point> findLast2WByUserId(Long userId) {
        LocalDate w2Ago = LocalDate.now().with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek()).atStartOfDay().minusWeeks(2).toLocalDate();

        return pointRepository.findAllByUserIdAndDateGreaterThan(userId, w2Ago);
    }

    @Override
    public List<Point> findByUserIdAndPeriod(Long userId, LocalDate begin, LocalDate end) {
        return pointRepository.findAllByUserIdAndDateBetween(userId, begin, end);
    }

    @Override
    public List<Point> findByPeriod(LocalDate begin, LocalDate end) {
        return pointRepository.findAllByDateBetween( begin, end);
    }


}
