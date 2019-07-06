package com.game.biz.model;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;

/**
 * A BadgeMaster.
 */
@Entity
@Table(name = "badge_master")
public class BadgeMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        columnDefinition = "NUMERIC(19,0)"
    )
    private Long id;

    @Column(name = "user_id", columnDefinition = "NUMERIC(19,0)")
    private Long userId;

    @Column(name = "nb_badges")
    private Integer nbBadges = 0;

    @Column(name = "validity_date")
    private LocalDate validityDate;

    public BadgeMaster(){}
    public BadgeMaster(Long userId){
        this.userId = userId;
        //last day of quarter
        LocalDate date  = LocalDate.now();
        if(date.isBefore(date.with(Month.APRIL).withDayOfMonth(1))){
            validityDate = date.with(Month.APRIL).withDayOfMonth(1);
        }else

        if(date.isBefore(date.with(Month.JULY).withDayOfMonth(1))){
            validityDate = date.with(Month.JULY).withDayOfMonth(1);
        }else

        if(date.isBefore(date.with(Month.OCTOBER).withDayOfMonth(1))){
            validityDate = date.with(Month.OCTOBER).withDayOfMonth(1);
        }else
            validityDate = date.with(Month.DECEMBER).withDayOfMonth(31);
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

    public BadgeMaster userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getNbBadges() {
        return nbBadges;
    }

    public BadgeMaster nbBadges(Integer nbBadges) {
        this.nbBadges = nbBadges;
        return this;
    }

    public void setNbBadges(Integer nbBadges) {
        this.nbBadges = nbBadges;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public BadgeMaster validityDate(LocalDate validityDate) {
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
        if (!(o instanceof BadgeMaster)) {
            return false;
        }
        return id != null && id.equals(((BadgeMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BadgeMaster{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", nbBadges=" + getNbBadges() +
            ", validityDate='" + getValidityDate() + "'" +
            "}";
    }
}
