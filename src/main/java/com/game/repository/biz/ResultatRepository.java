package com.game.repository.biz;

import com.game.biz.model.Resultat;
import com.game.biz.model.enumeration.BadgeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Resultat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {

    List<Resultat> findByUserId(Long userId);

    Optional<Resultat> findByUserIdAndCategorie(Long userId, BadgeType categorie);
 }
