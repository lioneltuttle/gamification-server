package com.game.biz.model;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A PropLastWeekReport.
 */
@Entity
@Table(name = "prop_last_week_report")
public class PropLastWeekReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id")
    private Double reportId;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @OneToMany(mappedBy = "reportLines")
    private Set<PropLastWeekReportLine> reports = new HashSet<>();

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

    public PropLastWeekReport reportId(Double reportId) {
        this.reportId = reportId;
        return this;
    }

    public void setReportId(Double reportId) {
        this.reportId = reportId;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public PropLastWeekReport reportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public Set<PropLastWeekReportLine> getReports() {
        return reports;
    }

    public PropLastWeekReport reports(Set<PropLastWeekReportLine> propLastWeekReportLines) {
        this.reports = propLastWeekReportLines;
        return this;
    }

    public PropLastWeekReport addReport(PropLastWeekReportLine propLastWeekReportLine) {
        this.reports.add(propLastWeekReportLine);
        propLastWeekReportLine.setReportLines(this);
        return this;
    }

    public PropLastWeekReport removeReport(PropLastWeekReportLine propLastWeekReportLine) {
        this.reports.remove(propLastWeekReportLine);
        propLastWeekReportLine.setReportLines(null);
        return this;
    }

    public void setReports(Set<PropLastWeekReportLine> propLastWeekReportLines) {
        this.reports = propLastWeekReportLines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropLastWeekReport)) {
            return false;
        }
        return id != null && id.equals(((PropLastWeekReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PropLastWeekReport{" +
            "id=" + getId() +
            ", reportId=" + getReportId() +
            ", reportDate='" + getReportDate() + "'" +
            "}";
    }
}
