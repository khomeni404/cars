package com.mbl.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 28/11/2017 6:05 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 28/11/2017: 28/11/2017 6:05 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Entity
@Table(name = "COM_PROPERTIES")
public class Properties extends GenericModel{

    /**
     * 5 digits id
     * First 3 pointing group code and last 3 pointing property ID
     * There is a map to identify GROUP'S code
     */
    @Id
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(name = "core_key", length = 4)
    private String coreKey;

    private Double marks;

    private Double weightage;


    @Override
    public String info() {
        return this.name;
    }

    ///==============================


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

    public String getCoreKey() {
        return coreKey;
    }

    public void setCoreKey(String coreKey) {
        this.coreKey = coreKey;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Double getWeightage() {
        return weightage;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
    }
}
