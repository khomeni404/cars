package com.mbl.common.model;

import java.io.Serializable;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.  (www.soft-engine.net)
 * <p>
 * Original author: Khomeni
 * Date: 21/11/2017 10:50 AM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 21/11/2017: 21/11/2017 12:50 AM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

public abstract class GenericModel implements Serializable {

    /**
     * Method returns basic information of Object holds.
     * @return String
     */
    public abstract String info();
}
