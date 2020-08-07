package com.dev.foodreservation.database.utilities;

import com.github.mfathi91.time.PersianDate;

import java.util.Date;

public class DateToPersianDate {

    public PersianDate get(Date date) {
        String theDate = date.toString();
        String year = theDate.substring(0, 4),
                month = theDate.substring(5, 7),
                day = theDate.substring(8, 10);
        return PersianDate.of(Integer.parseInt(year),
                Integer.parseInt(month), Integer.parseInt(day));
    }
}
