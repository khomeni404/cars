package com.mbl.common.bean;

import net.softengine.util.GValidator;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 28/12/2017 6:05 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 28/12/2017: 28/12/2017 6:05 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

public class TypePropertyBean extends GenericBean{

    private Long id;

    private String key;

    @Size(min=3, max=100, message = "Name is required and within 3-100 char.")
    private String name;

    @Min(11000)@Max(99999)
    private Integer type;

    private String coreKey;

    private Double weightage;

    @Override
    public void validate(BindingResult result) {
        if (!result.hasErrors()) {
            GValidator.validateSafeString(result, this);
        }
    }

    @Override
    public Object toPO() {
        return null;
    }

    @Override
    public String info() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCoreKey() {
        return coreKey;
    }

    public void setCoreKey(String coreKey) {
        this.coreKey = coreKey;
    }

    public Double getWeightage() {
        return weightage;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
    }
}
