package com.game.biz.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A PropLastWeekReportLine.
 */
@Entity
@Table(name = "prop_last_week_report_line")
public class PropLastWeekReportLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_complet")
    private String nomComplet;

    @Column(name = "rv_1")
    private String rv1;

    @Column(name = "date_dispo")
    private LocalDate dateDispo;

    @Column(name = "competence_principale")
    private String competencePrincipale;

    @Column(name = "exp")
    private Double exp;

    @Column(name = "tranche_exp")
    private String trancheExp;

    @Column(name = "ecole")
    private String ecole;

    @Column(name = "classe_ecole")
    private String classeEcole;

    @Column(name = "fonctions")
    private String fonctions;

    @Column(name = "date_rv_1")
    private LocalDate dateRV1;

    @Column(name = "rv_2")
    private String rv2;

    @Column(name = "metiers")
    private String metiers;

    @Column(name = "origine_piste")
    private String originePiste;

    @ManyToOne
    @JsonIgnoreProperties("reports")
    private PropLastWeekReport reportLines;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public PropLastWeekReportLine nomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
        return this;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getRv1() {
        return rv1;
    }

    public PropLastWeekReportLine rv1(String rv1) {
        this.rv1 = rv1;
        return this;
    }

    public void setRv1(String rv1) {
        this.rv1 = rv1;
    }

    public LocalDate getDateDispo() {
        return dateDispo;
    }

    public PropLastWeekReportLine dateDispo(LocalDate dateDispo) {
        this.dateDispo = dateDispo;
        return this;
    }

    public void setDateDispo(LocalDate dateDispo) {
        this.dateDispo = dateDispo;
    }

    public String getCompetencePrincipale() {
        return competencePrincipale;
    }

    public PropLastWeekReportLine competencePrincipale(String competencePrincipale) {
        this.competencePrincipale = competencePrincipale;
        return this;
    }

    public void setCompetencePrincipale(String competencePrincipale) {
        this.competencePrincipale = competencePrincipale;
    }

    public Double getExp() {
        return exp;
    }

    public PropLastWeekReportLine exp(Double exp) {
        this.exp = exp;
        return this;
    }

    public void setExp(Double exp) {
        this.exp = exp;
    }

    public String getTrancheExp() {
        return trancheExp;
    }

    public PropLastWeekReportLine trancheExp(String trancheExp) {
        this.trancheExp = trancheExp;
        return this;
    }

    public void setTrancheExp(String trancheExp) {
        this.trancheExp = trancheExp;
    }

    public String getEcole() {
        return ecole;
    }

    public PropLastWeekReportLine ecole(String ecole) {
        this.ecole = ecole;
        return this;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public String getClasseEcole() {
        return classeEcole;
    }

    public PropLastWeekReportLine classeEcole(String classeEcole) {
        this.classeEcole = classeEcole;
        return this;
    }

    public void setClasseEcole(String classeEcole) {
        this.classeEcole = classeEcole;
    }

    public String getFonctions() {
        return fonctions;
    }

    public PropLastWeekReportLine fonctions(String fonctions) {
        this.fonctions = fonctions;
        return this;
    }

    public void setFonctions(String fonctions) {
        this.fonctions = fonctions;
    }

    public LocalDate getDateRV1() {
        return dateRV1;
    }

    public PropLastWeekReportLine dateRV1(LocalDate dateRV1) {
        this.dateRV1 = dateRV1;
        return this;
    }

    public void setDateRV1(LocalDate dateRV1) {
        this.dateRV1 = dateRV1;
    }

    public String getRv2() {
        return rv2;
    }

    public PropLastWeekReportLine rv2(String rv2) {
        this.rv2 = rv2;
        return this;
    }

    public void setRv2(String rv2) {
        this.rv2 = rv2;
    }

    public String getMetiers() {
        return metiers;
    }

    public PropLastWeekReportLine metiers(String metiers) {
        this.metiers = metiers;
        return this;
    }

    public void setMetiers(String metiers) {
        this.metiers = metiers;
    }

    public String getOriginePiste() {
        return originePiste;
    }

    public PropLastWeekReportLine originePiste(String originePiste) {
        this.originePiste = originePiste;
        return this;
    }

    public void setOriginePiste(String originePiste) {
        this.originePiste = originePiste;
    }

    public PropLastWeekReport getReportLines() {
        return reportLines;
    }

    public PropLastWeekReportLine reportLines(PropLastWeekReport propLastWeekReport) {
        this.reportLines = propLastWeekReport;
        return this;
    }

    public void setReportLines(PropLastWeekReport propLastWeekReport) {
        this.reportLines = propLastWeekReport;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropLastWeekReportLine)) {
            return false;
        }
        return id != null && id.equals(((PropLastWeekReportLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PropLastWeekReportLine{" +
            "id=" + getId() +
            ", nomComplet='" + getNomComplet() + "'" +
            ", rv1='" + getRv1() + "'" +
            ", dateDispo='" + getDateDispo() + "'" +
            ", competencePrincipale='" + getCompetencePrincipale() + "'" +
            ", exp=" + getExp() +
            ", trancheExp='" + getTrancheExp() + "'" +
            ", ecole='" + getEcole() + "'" +
            ", classeEcole='" + getClasseEcole() + "'" +
            ", fonctions='" + getFonctions() + "'" +
            ", dateRV1='" + getDateRV1() + "'" +
            ", rv2='" + getRv2() + "'" +
            ", metiers='" + getMetiers() + "'" +
            ", originePiste='" + getOriginePiste() + "'" +
            "}";
    }
}
