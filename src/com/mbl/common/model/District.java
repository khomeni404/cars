package com.mbl.common.model;

import javax.persistence.*;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 23/11/2017 11:06 AM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 23/11/2017: 23/11/2017 11:06 AM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Entity
@Table(name = "COM_DISTRICT")
public class District extends GenericModel{

    public District(){}
    public District(Long id){this.id = id;}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String bbID;

    private String name;

    private String description;

    private Integer population;

    private Double area;

    @Column(name = "PPK")
    private Integer populationPerKm;

    @Column(name = "DIVISION_ID")
    private String divisionId;



    @Override
    public String info() {
        return this.name;
    }

    // ============================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getBbID() {
        return bbID;
    }

    public void setBbID(String bbID) {
        this.bbID = bbID;
    }

    public Integer getPopulationPerKm() {
        return populationPerKm;
    }

    public void setPopulationPerKm(Integer populationPerKm) {
        this.populationPerKm = populationPerKm;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }



}
