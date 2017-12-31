package com.apurata.prestamos.creditos.LocalStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by apurata on 9/25/17.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // When database schema changes, you must increment the database version.
    private static final String TAG = "GPS_TRACKER_SQLITE";
    private static final String NAME_BD = "Coordinates.db";
    private static final int VERSION_BD = 3;

    public  FeedReaderDbHelper(Context context){
        super(context,NAME_BD,null,VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+ DBContract.CoordEntry.TABLE_NAME+" ("+
                DBContract.CoordEntry._ID+" INTEGER PRIMARY KEY,"+
                DBContract.CoordEntry.COLUMN_LATITUDE+" REAL NOT NULL,"+
                DBContract.CoordEntry.COLUMN_LONGITUDE+" REAL NOT NULL,"+
                DBContract.CoordEntry.COLUMN_ALTITUDE+" REAL NOT NULL,"+
                DBContract.CoordEntry.COLUMN_SPEED+" REAL NOT NULL,"+
                DBContract.CoordEntry.COLUMN_ACCURACY+" REAL NOT NULL,"+
                DBContract.CoordEntry.COLUMN_CREATED+" DATETIME DEFAULT CURRENT_TIMESTAMP,"+
                DBContract.CoordEntry.COLUMN_COUNTER+" INTEGER NOT NULL,"+
                DBContract.CoordEntry.COLUMN_RECORDED+" INTEGER NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "upgrading data base");
        db.execSQL("DROP TABLE IF EXISTS "+ DBContract.CoordEntry.TABLE_NAME);
        onCreate(db);
    }
};