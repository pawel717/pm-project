package com.pm.pmproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatted extends Date{
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");

    public DateFormatted(Date date) {
        super(date.getTime());

    }

    @Override
    public String toString() {
        String formattedDate = DateFormatted.DATE_FORMAT.format(this);
        return formattedDate;
    }
}
