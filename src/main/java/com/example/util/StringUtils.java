package com.example.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static Timestamp dateParse(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return new Timestamp(parsedDate.getTime());
    }
}
