package com.game.biz.model;


import com.game.biz.model.enumeration.BadgeType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * A Point.
 */
@Entity
@Table(name = "present")
public class Present implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        columnDefinition = "NUMERIC(19,0)"
    )
    private Long id;

    @Column(name = "user_id", columnDefinition = "NUMERIC(19,0)")
    private Long userId;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "consumed")
    private Boolean consumed;

    public Present(){}
    public Present(Present p){

    }
    public Present(Long userId){
        this.userId = userId;
        this.consumed = false;
        this.date = LocalDate.now();
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

    public Present userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setConsumed(Boolean consumed) {
        this.consumed = consumed;
    }

    public Boolean getConsumed() {
        return consumed;
    }

    public Present consumed(Boolean consumed) {
        this.consumed = consumed;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Present date(LocalDate date) {
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
        if (!(o instanceof Present)) {
            return false;
        }
        return id != null && id.equals(((Present) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Present{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", consumed=" + getConsumed() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
