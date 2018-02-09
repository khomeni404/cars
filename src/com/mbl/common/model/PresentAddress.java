package com.mbl.common.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 06/12/2017 4:50 PM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 06/12/2017: 06/12/2017 4:50 PM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

@Entity
@DiscriminatorValue("Present")
public class PresentAddress extends Address {
    @Override
    public String info() {
        return super.info();
    }
}
