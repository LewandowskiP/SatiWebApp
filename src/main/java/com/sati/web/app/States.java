package com.sati.web.app;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Timestamp;

/**
 * @author Przemysław
 */
public class States {

    public static final int PRODUCTION_RAPORT_PART_WAITING = 1;
    public static final int PRODUCTION_RAPORT_PART_BLOCKED = 2;
    public static final int PRODUCTION_RAPORT_PART_ACCEPTED = 3;
    public static final int PRODUCTION_RAPORT_PART_TO_STORE = 4;

    public static final int PALLETE_WAITING = 1;
    public static final int PALLETE_CHECKED = 2;
    public static final int PALLETE_STORED = 3;

    public static String timestampToStrDDMMYYYY(Timestamp ts) {
        String date = String.valueOf(ts.getDate());
        date += "-" + String.valueOf(ts.getMonth() + 1);
        date += "-" + String.valueOf(ts.getYear() + 1900);
        return date;
    }


}
