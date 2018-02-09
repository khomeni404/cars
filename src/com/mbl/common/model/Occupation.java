package com.mbl.common.model;

import net.softengine.util.GValidator;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 29/11/2017 10:59 AM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 29/11/2017: 29/11/2017 10:59 AM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Entity
@Table(name = "COM_OCCUPATION")
public class Occupation extends GenericModel {

    public Occupation() {
    }

    public Occupation(Long id) {
        this.id = id;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "name_bn", length = 700, columnDefinition = "text")
    private String nameBN;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "parent")
    private Occupation parentOccupation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentOccupation")
    @Fetch(FetchMode.SELECT)
    private List<Occupation> childrenList = new ArrayList<>(0);

    @Override
    public String info() {
        return this.name + (GValidator.isBlankOrNull(this.nameBN) ? "" : (" (" + nameBN + ")"))
                + (this.parentOccupation == null ? "" : (" - " + parentOccupation.name))
                ;
    }


    //=======================================


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

    public String getNameBN() {
        return nameBN;
    }

    public void setNameBN(String nameBN) {
        this.nameBN = nameBN;
    }

    public Occupation getParentOccupation() {
        return parentOccupation;
    }

    public void setParentOccupation(Occupation parentOccupation) {
        this.parentOccupation = parentOccupation;
    }

    public List<Occupation> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Occupation> childrenList) {
        this.childrenList = childrenList;
    }
}
