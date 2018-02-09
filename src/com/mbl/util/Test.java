package com.mbl.util;

import ibbl.deposit.common.web.bean.CustomerBean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Khomeni
 *         Created on : 20-May-17 at 12:57 AM
 */

public class Test {

    public static void main(String[] args) {
        Object o = deserialzeAddress("F:\\Backup\\GroupCustomerBean.ser");
        System.out.println("o.toString() = " + o.toString());

    }

    public static Object deserialzeAddress(String filename) {

        Object object = null;

        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {

            fin = new FileInputStream(filename);
            ois = new ObjectInputStream(fin);
            object =  ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return object;

    }
}
