package com.cg.utils;

import com.cg.exception.ParseDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static LocalDate parse(String strDate) throws ParseDateException {
        try {
            LocalDate date = simpleDateFormat.parse(strDate).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            return date;
        } catch (ParseException parseException) {
//            parseException.printStackTrace();
            throw new ParseDateException("Lỗi định dạng ngày tháng");
        }

    }
}

