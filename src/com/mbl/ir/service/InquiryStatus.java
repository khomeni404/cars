package com.mbl.ir.service;

import java.util.*;

/**
 * Copyright (C) 2002-2003 Mercantile Bank Limited
 * <p>
 * Original author: Ayatullah Khomeni
 * Date: 03/01/2016
 * Last modification by: ayat $
 * Last modification on 03/01/2016: 10:33 AM
 * Current revision: : 1.1.1.1
 * <p>
 * Revision History:
 * ------------------
 */
public enum InquiryStatus {
    // Inquiry Status CODE, NAME, HISTORY TEXT
    CREATED(11, "Created", "Inquiry has been Created and waiting for Forward"),
    FORWARDED(12, "Forwarded", "Inquiry has been approved and Forwarded to CIB, Head Office."),
    PROCESSING(13, "Processing", "Inquiry is being Processed by CIB, Head Office."),
    REPORTED(14, "Reported", "Reported & Document Uploaded by CIB, Head Office."),
    COMPLAINED(15, "Complained", "Complained on inquiry."),
    CLOSED(16, "Closed", "Inquiry story has been closed."),
    REJECTED (17, "Rejected", "Inquiry has been Rejected");

    public final Integer CODE;
    public final String STATUS;
    public final String DESC;

    InquiryStatus(int code, String status, String desc) {
        this.CODE = code;
        this.STATUS = status;
        this.DESC = desc;
    }


    private static final Map<Integer, InquiryStatus> STATUS_MAP = new HashMap<>();
    public static final List<Map<String, String>> STATUS_MAP_LIST = new ArrayList<>();

    static {
        Map<String, String> map;
        for (InquiryStatus pops : InquiryStatus.values()) {
            STATUS_MAP.put(pops.CODE, pops);

            map = new HashMap<>();
            map.put("CODE", pops.CODE.toString());
            map.put("STATUS", pops.STATUS);
            map.put("DESC", pops.DESC);
            STATUS_MAP_LIST.add(map);
        }
    }

    public static InquiryStatus get(final int CODE) {
        return STATUS_MAP.get(CODE);
    }

    public static List<InquiryStatus> getList() {
        return new ArrayList<>(EnumSet.allOf(InquiryStatus.class));
    }
}
