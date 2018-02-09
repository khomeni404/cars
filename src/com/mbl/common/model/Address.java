package com.mbl.common.model;

import net.softengine.model.User;
import net.softengine.util.GValidator;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 03/12/2017 3:33 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 03/12/2017: 03/12/2017 3:33 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "COM_ADDRESS_Master")
public class Address extends GenericModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String note;

    @Override
    public String info() {
        return line1 +
                (GValidator.isBlankOrNull(line2) ? "" : (", " + line2));
        // // TODO: 06/12/2017  Concatenate District, PO, PS, Division etc
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private User holder;

    @Column(name = "line_1", length = 200)
    private String line1;

    @Column(name = "line_2", length = 200)
    private String line2;

    private String po;

    private String pc;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private PoliceStation ps;

    @Transient
    public String getDiscriminatorValue() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);
        return val == null ? this.getClass().getSimpleName() : val.value();
    }


    // ===========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getHolder() {
        return holder;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public PoliceStation getPs() {
        return ps;
    }

    public void setPs(PoliceStation ps) {
        this.ps = ps;
    }

}
