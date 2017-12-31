package com.apurata.prestamos.creditos.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by USUARIO on 12/07/2017.
 */

public class Utils {

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
