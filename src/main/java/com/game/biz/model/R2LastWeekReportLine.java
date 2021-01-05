package com.game.biz.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A R2LastWeekReportLine.
 */
@Entity
@Table(name = "r_2_last_week_report_line")
public class R2LastWeekReportLine implements Serializable {

    private static final long serialVersionUID = 1L;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
    private R2LastWeekReport reportLines;

    private transient int field = 0;
    /**
     * method to set up the object
     * @return
     */
    public void set(String value){
        switch (field){
            case 0:
                nomComplet = value;
                break;
            case 1:
                rv1 = value;
                break;
            case 2:
                dateDispo = LocalDate.parse(value,formatter);
                break;
            case 3:
                competencePrincipale = value;
                break;
            case 4:
                exp = Double.parseDouble(value);
                break;
            case 5:
                trancheExp = value;
                break;
            case 6:
                ecole = value;
                break;
            case 7:
                classeEcole = value;
                break;
            case 8:
                fonctions = value;
                break;
            case 9:
                dateRV1 = LocalDate.parse(value,formatter);
            case 10:
                rv2 = value;
                break;
            case 11:
                metiers = value;
                break;
            case 12:
                originePiste = value;
                break;
        }
        field++;
    }

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

    public R2LastWeekReportLine nomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
        return this;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getRv1() {
        return rv1;
    }

    public R2LastWeekReportLine rv1(String rv1) {
        this.rv1 = rv1;
        return this;
    }

    public void setRv1(String rv1) {
        this.rv1 = rv1;
    }

    public LocalDate getDateDispo() {
        return dateDispo;
    }

    public R2LastWeekReportLine dateDispo(LocalDate dateDispo) {
        this.dateDispo = dateDispo;
        return this;
    }

    public void setDateDispo(LocalDate dateDispo) {
        this.dateDispo = dateDispo;
    }

    public String getCompetencePrincipale() {
        return competencePrincipale;
    }

    public R2LastWeekReportLine competencePrincipale(String competencePrincipale) {
        this.competencePrincipale = competencePrincipale;
        return this;
    }

    public void setCompetencePrincipale(String competencePrincipale) {
        this.competencePrincipale = competencePrincipale;
    }

    public Double getExp() {
        return exp;
    }

    public R2LastWeekReportLine exp(Double exp) {
        this.exp = exp;
        return this;
    }

    public void setExp(Double exp) {
        this.exp = exp;
    }

    public String getTrancheExp() {
        return trancheExp;
    }

    public R2LastWeekReportLine trancheExp(String trancheExp) {
        this.trancheExp = trancheExp;
        return this;
    }

    public void setTrancheExp(String trancheExp) {
        this.trancheExp = trancheExp;
    }

    public String getEcole() {
        return ecole;
    }

    public R2LastWeekReportLine ecole(String ecole) {
        this.ecole = ecole;
        return this;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public String getClasseEcole() {
        return classeEcole;
    }

    public R2LastWeekReportLine classeEcole(String classeEcole) {
        this.classeEcole = classeEcole;
        return this;
    }

    public void setClasseEcole(String classeEcole) {
        this.classeEcole = classeEcole;
    }

    public String getFonctions() {
        return fonctions;
    }

    public R2LastWeekReportLine fonctions(String fonctions) {
        this.fonctions = fonctions;
        return this;
    }

    public void setFonctions(String fonctions) {
        this.fonctions = fonctions;
    }

    public LocalDate getDateRV1() {
        return dateRV1;
    }

    public R2LastWeekReportLine dateRV1(LocalDate dateRV1) {
        this.dateRV1 = dateRV1;
        return this;
    }

    public void setDateRV1(LocalDate dateRV1) {
        this.dateRV1 = dateRV1;
    }

    public String getRv2() {
        return rv2;
    }

    public R2LastWeekReportLine rv2(String rv2) {
        this.rv2 = rv2;
        return this;
    }

    public void setRv2(String rv2) {
        this.rv2 = rv2;
    }

    public String getMetiers() {
        return metiers;
    }

    public R2LastWeekReportLine metiers(String metiers) {
        this.metiers = metiers;
        return this;
    }

    public void setMetiers(String metiers) {
        this.metiers = metiers;
    }

    public String getOriginePiste() {
        return originePiste;
    }

    public R2LastWeekReportLine originePiste(String originePiste) {
        this.originePiste = originePiste;
        return this;
    }

    public void setOriginePiste(String originePiste) {
        this.originePiste = originePiste;
    }

    public R2LastWeekReport getReportLines() {
        return reportLines;
    }

    public R2LastWeekReportLine reportLines(R2LastWeekReport r2LastWeekReport) {
        this.reportLines = r2LastWeekReport;
        return this;
    }

    public void setReportLines(R2LastWeekReport r2LastWeekReport) {
        this.reportLines = r2LastWeekReport;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof R2LastWeekReportLine)) {
            return false;
        }
        return id != null && id.equals(((R2LastWeekReportLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "R2LastWeekReportLine{" +
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
