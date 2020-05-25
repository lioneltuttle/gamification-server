package com.game.biz.model;


import com.game.biz.model.enumeration.EventType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PointsAudit.
 */
@Entity
@Table(name = "points_audit")
public class PointsAudit implements Serializable {

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
    @Column(name = "subject")
    private EventType subject;

    @Column(name = "jhi_value")
    private String value;

    @Column(name = "seen")
    private Boolean seen = true;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "point")
    private Long point;

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

    public PointsAudit userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EventType getSubject() {
        return subject;
    }

    public PointsAudit subject(EventType subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(EventType subject) {
        this.subject = subject;
    }

    public String getValue() {
        return value;
    }

    public PointsAudit value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isSeen() {
        return seen;
    }

    public PointsAudit seen(Boolean seen) {
        this.seen = seen;
        return this;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PointsAudit date(LocalDate date) {
        this.date = date;
        return this;
    }

    public Long getPoint() {
        return point;
    }

    public PointsAudit point(Long point) {
        this.point = point;
        return this;
    }

    public void setPoint(Long point) {
        this.point = point;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointsAudit)) {
            return false;
        }
        return id != null && id.equals(((PointsAudit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PointsAudit{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", subject='" + getSubject() + "'" +
            ", value='" + getValue() + "'" +
            ", seen='" + isSeen() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
