package com.mbl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Ayatullah Khomeni
 * Date: 30/12/2015
 * Last modification by: ayat $
 * Last modification on 30/12/2015: 10:58 AM
 * Current revision: : 1.1.1.1
 * <p/>
 * Revision History:
 * ------------------
 */
public class PropertyUtil {
    // public final static String DOC_DRIVE;
    // public final static String DOC_PATH;
    private String fileName = "config.properties";

    public PropertyUtil() {}
    public PropertyUtil(String name) {
        fileName = name;
    }

    public static PropertyUtil getInstance(String fileName) {
        return new PropertyUtil(fileName);
    }

    public static String get(String key) {
       return new PropertyUtil().getPropertyValue(key);
    }

    public String getPropertyValue(String propName) {
        Properties prop = new Properties();
        InputStream input = null;
        String propValue = "";
        try {
            input = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
            if(input==null){
                return "Sorry, unable to find ";
            }
            prop.load(input);
            propValue = prop.getProperty(propName);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return propValue;

    }

    public static void main(String[] args) {
        String DB_URL =  PropertyUtil.getInstance("config.properties").getPropertyValue("DB_URL");
        String DB_UID =  PropertyUtil.getInstance("remote-config").getPropertyValue("DB_UID");
        String DB_CODE =  PropertyUtil.getInstance("remote-config").getPropertyValue("DB_CODE");
        System.out.println("s = " + DB_URL);
        System.out.println("s = " + DB_UID);
        System.out.println("s = " + DB_CODE);

    }
}
