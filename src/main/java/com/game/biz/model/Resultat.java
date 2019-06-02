package com.game.biz.model;



import javax.persistence.*;

import java.io.Serializable;

import com.game.biz.model.enumeration.BadgeType;

/**
 * A Resultat.
 */
@Entity
@Table(name = "resultat")
public class Resultat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "categorie")
    private BadgeType categorie;

    @Column(name = "points")
    private Integer points;

    @Column(name = "nb_badges")
    private Integer nbBadges;

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
    public void addPoints(int nbPoints){
        if(BadgeType.R2 == categorie){
            if (this.points + nbPoints > 20){
                this.nbBadges ++;
                this.points = points + nbPoints - 20;
            }
        } else {
            if (this.points + nbPoints > 10){
                this.nbBadges ++;
                this.points = points + nbPoints - 10;
            }
        }
    }
}
