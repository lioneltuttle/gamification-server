package com.game.biz.model;



import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A BadgeLegend.
 */
@Entity
@Table(name = "badge_legend")
public class BadgeLegend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Double userId;

    @Column(name = "nb_badges")
    private Integer nbBadges;

    @Column(name = "validity_date")
    private LocalDate validityDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getUserId() {
        return userId;
    }

    public BadgeLegend userId(Double userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Double userId) {
        this.userId = userId;
    }

    public Integer getNbBadges() {
        return nbBadges;
    }

    public BadgeLegend nbBadges(Integer nbBadges) {
        this.nbBadges = nbBadges;
        return this;
    }

    public void setNbBadges(Integer nbBadges) {
        this.nbBadges = nbBadges;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public BadgeLegend validityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
        return this;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgeLegend)) {
            return false;
        }
        return id != null && id.equals(((BadgeLegend) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BadgeLegend{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", nbBadges=" + getNbBadges() +
            ", validityDate='" + getValidityDate() + "'" +
            "}";
    }
}
