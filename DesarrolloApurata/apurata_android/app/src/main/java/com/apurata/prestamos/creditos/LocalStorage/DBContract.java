package com.apurata.prestamos.creditos.LocalStorage;

import android.provider.BaseColumns;

/**
 * Created by apurata on 9/25/17.
 */

public class DBContract {

    public static final class CoordEntry implements BaseColumns {
        public static final String TABLE_NAME="Coordinates";
        public static final String COLUMN_SPEED="speed";
        public static final String COLUMN_ACCURACY="accuracy";
        public static final String COLUMN_ALTITUDE="altitude";
        public static final String COLUMN_LONGITUDE="longitude";
        public static final String COLUMN_LATITUDE="latitude";
        public static final String COLUMN_CREATED="created_at";
        public static final String COLUMN_COUNTER ="counter";
        public static final String COLUMN_RECORDED="recorded_at";
    }
}
