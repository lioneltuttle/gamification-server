package com.game.biz.model;


import com.game.biz.model.enumeration.BadgeType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Point.
 */
@Entity
@Table(name = "point")
public class Point implements Serializable {

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

    @Column(name = "nb_points")
    private Integer nbPoints = Integer.valueOf(0);

    @Column(name = "jhi_date")
    private LocalDate date;

    public Point(){}
    public Point(Point p){

    }
    public Point(Long userId){
        this.userId = userId;
    }

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

    public Point userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BadgeType getCategorie() {
        return categorie;
    }

    public Point categorie(BadgeType categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(BadgeType categorie) {
        this.categorie = categorie;
    }

    public Integer getNbPoints() {
        return nbPoints;
    }

    public Point nbPoints(Integer nbPoints) {
        this.nbPoints = nbPoints;
        return this;
    }

    public void setNbPoints(Integer nbPoints) {
        this.nbPoints = nbPoints;
    }

    public LocalDate getDate() {
        return date;
    }

    public Point date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        return id != null && id.equals(((Point) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Point{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", categorie='" + getCategorie() + "'" +
            ", nbPoints=" + getNbPoints() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
