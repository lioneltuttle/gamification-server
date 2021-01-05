package com.game.biz.model;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A DemarrageLastWeekReport.
 */
@Entity
@Table(name = "demarrage_last_week_report")
public class DemarrageLastWeekReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private Double reportId;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @OneToMany(mappedBy = "reportLines")
    private Set<DemarrageLastWeekReportLine> reports = new HashSet<>();

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

    public DemarrageLastWeekReport reportId(Double reportId) {
        this.reportId = reportId;
        return this;
    }

    public void setReportId(Double reportId) {
        this.reportId = reportId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public DemarrageLastWeekReport reportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public Set<DemarrageLastWeekReportLine> getReports() {
        return reports;
    }

    public DemarrageLastWeekReport reports(Set<DemarrageLastWeekReportLine> demarrageLastWeekReportLines) {
        this.reports = demarrageLastWeekReportLines;
        return this;
    }

    public DemarrageLastWeekReport addReport(DemarrageLastWeekReportLine demarrageLastWeekReportLine) {
        this.reports.add(demarrageLastWeekReportLine);
        demarrageLastWeekReportLine.setReportLines(this);
        return this;
    }

    public DemarrageLastWeekReport removeReport(DemarrageLastWeekReportLine demarrageLastWeekReportLine) {
        this.reports.remove(demarrageLastWeekReportLine);
        demarrageLastWeekReportLine.setReportLines(null);
        return this;
    }

    public void setReports(Set<DemarrageLastWeekReportLine> demarrageLastWeekReportLines) {
        this.reports = demarrageLastWeekReportLines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemarrageLastWeekReport)) {
            return false;
        }
        return id != null && id.equals(((DemarrageLastWeekReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DemarrageLastWeekReport{" +
            "id=" + getId() +
            ", reportId=" + getReportId() +
            ", reportDate='" + getReportDate() + "'" +
            "}";
    }
}
