package com.mbl.inquiry.util;

import com.ibbl.context.ContextLocator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * package com.ibbl.report.util;
 * Copyright (C) 2002-2003 Islami Bank Bangladesh Limited
 * <p/>
 * Original author: Khomeni
 * Date: 3/5/13(12:29 PM)
 * Last modification by: $Author: ayat $
 * Last modification on $Date: 2017/08/01 02:24:07 $
 * Current revision: $Revision: 1.2 $
 * <p/>
 * Revision History:
 * ------------------
 */
public class DBConfig
{
    private static Properties properties = null;
    private static DBConfig dbConfig = null;
    public static final String DB_DRIVER = "DB_DRIVER";
    public static final String DB_URL = "DB_URL";
    public static final String DB_USR = "DB_UID";
    public static final String DB_PSWD = "DB_CODE";

    private DBConfig()
    {
        readPropertyFile();
    }

    public static DBConfig getInstance()
    {
        if (dbConfig == null)
        {
            dbConfig = new DBConfig();
        }

        return dbConfig;
    }
    private void readPropertyFile()
    {
        if (properties == null || properties.size() == 0)
        {
            String configFile = "remote-config.properties";
            String filePath = ContextLocator.getConfigRoot()
                    + File.separator + "classes" + File.separator + configFile;
            try
            {
                properties = new Properties();
                properties.load(new FileInputStream(filePath));
            }
            catch (FileNotFoundException e)
            {
                properties = null;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getProperity(String key)
    {
        return properties.getProperty(key);
    }

    public Connection getJdBcConnection()
    {
        String dbUsr = getProperity(DB_USR);
        String dbPswd = getProperity(DB_PSWD);
        String dbURL = getProperity(DB_URL);
        String dbDriver = getProperity(DB_DRIVER);
        Connection conn = null;
        try
        {
            Class.forName(dbDriver);
        }
        catch (ClassNotFoundException e)
        {
            return conn;  //To change body of catch statement use File | Settings | File Templates.
        }
        try
        {
            conn = DriverManager.getConnection(dbURL, dbUsr, dbPswd);
        }
        catch (SQLException e)
        {
            conn = null;  //To change body of catch statement use File | Settings | File Templates.
        }
        return conn;
    }

    public void closeJdBcConnection(Connection conn)
    {
        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage()); //To change body of catch statement use File | Settings | File Templates.
        }
    }
}