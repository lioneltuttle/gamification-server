package com.game.biz.model;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A R2LastWeekReport.
 */
@Entity
@Table(name = "r_2_last_week_report")
public class R2LastWeekReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private Double reportId;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @OneToMany(mappedBy = "reportLines")
    private Set<R2LastWeekReportLine> reports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getReportId() {
        return reportId;
    }

    public R2LastWeekReport reportId(Double reportId) {
        this.reportId = reportId;
        return this;
    }

    public void setReportId(Double reportId) {
        this.reportId = reportId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public R2LastWeekReport reportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public Set<R2LastWeekReportLine> getReports() {
        return reports;
    }

    public R2LastWeekReport reports(Set<R2LastWeekReportLine> r2LastWeekReportLines) {
        this.reports = r2LastWeekReportLines;
        return this;
    }

    public R2LastWeekReport addReport(R2LastWeekReportLine r2LastWeekReportLine) {
        this.reports.add(r2LastWeekReportLine);
        r2LastWeekReportLine.setReportLines(this);
        return this;
    }

    public R2LastWeekReport removeReport(R2LastWeekReportLine r2LastWeekReportLine) {
        this.reports.remove(r2LastWeekReportLine);
        r2LastWeekReportLine.setReportLines(null);
        return this;
    }

    public void setReports(Set<R2LastWeekReportLine> r2LastWeekReportLines) {
        this.reports = r2LastWeekReportLines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof R2LastWeekReport)) {
            return false;
        }
        return id != null && id.equals(((R2LastWeekReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "R2LastWeekReport{" +
            "id=" + getId() +
            ", reportId=" + getReportId() +
            ", reportDate='" + getReportDate() + "'" +
            "}";
    }
}
