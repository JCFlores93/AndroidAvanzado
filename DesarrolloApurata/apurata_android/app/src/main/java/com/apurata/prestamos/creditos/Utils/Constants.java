package com.apurata.prestamos.creditos.Utils;

/**
 * Created by apurata on 10/30/17.
 */

public class Constants {

    /* mid layer endpoints */
    public static final String EVE_URL = "http://eve.apurata.com/";

    //    /* apurata endpoints */
    //public static final String APURATA_URL = "https://apurata.com/";
    public static final String APURATA_URL = "http://192.168.0.8/";
//    public static final String APURATA_BASE_URL = "http://192.168.10.54:8000/";

    /* GPS CONSTANTS */

    public static final float LOCATION_DISTANCE = 2f;
    public static final int FIRST_PERIOD_TIME_DAYS = 2;
    public static final int SECOND_PERIOD_TIME_DAYS = 60;
    public static final long LOCATION_INTERVAL = 5 * 60 * 1000;
    public static final int SEND_DATA_FIRST_INTERVAL = (int)(0.5 * 60 * 1000);
    public static final int SEND_DATA_SECOND_INTERVAL = 60 * 60 * 1000;
}
