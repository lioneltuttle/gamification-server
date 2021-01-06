package com.game.biz.model;


import com.game.biz.model.enumeration.BadgeType;

import javax.persistence.*;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * A Resultat.
 */
@Entity
@Table(name = "resultat")
public class Resultat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        columnDefinition = "NUMERIC(19,0)"
    )
    private Long id;

    @Column(name = "user_id", columnDefinition = "NUMERIC(19,0)")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private BadgeType categorie;

    @Column(name = "points")
    private Integer points = 0;

    @Column(name = "nb_badges")
    private Integer nbBadges = 0;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Resultat userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BadgeType getCategorie() {
        return categorie;
    }

    public Resultat categorie(BadgeType categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(BadgeType categorie) {
        this.categorie = categorie;
    }

    public Integer getPoints() {
        return points;
    }

    public Resultat points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getNbBadges() {
        return nbBadges;
    }

    public Resultat nbBadges(Integer nbBadges) {
        this.nbBadges = nbBadges;
        return this;
    }

    public void setNbBadges(Integer nbBadges) {
        this.nbBadges = nbBadges;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resultat)) {
            return false;
        }
        return id != null && id.equals(((Resultat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Resultat{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", categorie='" + getCategorie() + "'" +
            ", points=" + getPoints() +
            ", nbBadges=" + getNbBadges() +
            "}";
    }

    //business methods

    /**
     * returns the number of badges created
     * @param nbPoints
     * @return
     */
    public int addPoints(int nbPoints){
        return generateBadges(points + nbPoints, BadgeType.R2 == categorie ? 20:10);
    }

    private int generateBadges(int points, int nbPointsForBadge){

        int totalConvertedPoints = points + this.nbBadges*nbPointsForBadge;
        if(totalConvertedPoints<0){
            totalConvertedPoints = 0;
        }
        this.points = totalConvertedPoints%nbPointsForBadge;
        int totalBadges = Math.floorDiv(totalConvertedPoints, nbPointsForBadge);
        int newBadges = Math.floorDiv(totalConvertedPoints, nbPointsForBadge) - this.nbBadges;
        if(newBadges <0){
            newBadges = 0;
        }
        this.nbBadges = totalBadges;
        return newBadges;
    }
}
