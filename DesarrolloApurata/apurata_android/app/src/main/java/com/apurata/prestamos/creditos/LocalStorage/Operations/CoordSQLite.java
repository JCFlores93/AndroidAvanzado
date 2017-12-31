package com.apurata.prestamos.creditos.LocalStorage.Operations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.apurata.prestamos.creditos.LocalStorage.DBContract;
import com.apurata.prestamos.creditos.LocalStorage.FeedReaderDbHelper;
import com.apurata.prestamos.creditos.RequestModels.CoordBGService;
import com.apurata.prestamos.creditos.RequestModels.Coord;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by apurata on 9/25/17.
 */

public class CoordSQLite {

    FeedReaderDbHelper openHelper;
    private String TAG = "COORDS_SQLITE";

    public CoordSQLite(Context context)
    {
        openHelper=new FeedReaderDbHelper(context);
    }

    public void insertCoord(CoordBGService coordBGService) {
        Log.i(TAG, "is inserting");
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Date now = new Date();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBContract.CoordEntry.COLUMN_LATITUDE, coordBGService.getLatitude());
        contentValues.put(DBContract.CoordEntry.COLUMN_LONGITUDE, coordBGService.getLongitude());
        contentValues.put(DBContract.CoordEntry.COLUMN_ALTITUDE, coordBGService.getAltitude());
        contentValues.put(DBContract.CoordEntry.COLUMN_SPEED, coordBGService.getSpeed());
        contentValues.put(DBContract.CoordEntry.COLUMN_ACCURACY, coordBGService.getAccuracy());
        contentValues.put(DBContract.CoordEntry.COLUMN_CREATED, coordBGService.getCreated_at());
        contentValues.put(DBContract.CoordEntry.COLUMN_COUNTER, coordBGService.getCounter());
        contentValues.put(DBContract.CoordEntry.COLUMN_RECORDED, coordBGService.getRecorded_at());
        db.insert(DBContract.CoordEntry.TABLE_NAME,null,contentValues);
        Log.d(TAG, "inserting with counter: " + coordBGService.getCounter() );
    }

    public void insertCoords(List<Coord> coords) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for (Coord coord : coords) {
                contentValues.put(DBContract.CoordEntry.COLUMN_LATITUDE,coord.getLatitude());
                contentValues.put(DBContract.CoordEntry.COLUMN_LONGITUDE,coord.getLongitude());
                contentValues.put(DBContract.CoordEntry.COLUMN_ALTITUDE,coord.getAltitude());
                contentValues.put(DBContract.CoordEntry.COLUMN_SPEED,coord.getSpeed());
                contentValues.put(DBContract.CoordEntry.COLUMN_ACCURACY,coord.getAccuracy());
                db.insert(DBContract.CoordEntry.TABLE_NAME,null,contentValues);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void removeCoords() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.delete(DBContract.CoordEntry.TABLE_NAME,null,null);
    }

    public void removeCoordsByCounter(int counter) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.delete(DBContract.CoordEntry.TABLE_NAME, "counter" + " = " + counter, null);
    }

    public long size() {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, DBContract.CoordEntry.TABLE_NAME);
    }

    public List<CoordBGService> listCoords()
    {
        ArrayList<CoordBGService> list=new ArrayList<>();
        SQLiteDatabase db=openHelper.getReadableDatabase();
        CoordBGService coordBGService = null;
        String[] columns={
                DBContract.CoordEntry.COLUMN_LATITUDE,
                DBContract.CoordEntry.COLUMN_LONGITUDE,
                DBContract.CoordEntry.COLUMN_ALTITUDE,
                DBContract.CoordEntry.COLUMN_SPEED,
                DBContract.CoordEntry.COLUMN_ACCURACY,
                DBContract.CoordEntry.COLUMN_CREATED,
                DBContract.CoordEntry.COLUMN_COUNTER,
                DBContract.CoordEntry.COLUMN_RECORDED
        };
        Cursor cursor=db.query(DBContract.CoordEntry.TABLE_NAME,columns,null,null,null,null,null);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                float latitude = cursor.getFloat(0);
                float longitude = cursor.getFloat(1);
                float altitude = cursor.getFloat(2);
                float speed = cursor.getFloat(3);
                float accuracy = cursor.getFloat(4);
                String created = cursor.getString(5);
                int counter= cursor.getInt(6);
                String recorded = cursor.getString(7);
                coordBGService = new CoordBGService();
                coordBGService.setCounter(counter);
                coordBGService.setLatitude(latitude);
                coordBGService.setLongitude(longitude);
                coordBGService.setAltitude(altitude);
                coordBGService.setSpeed(speed);
                coordBGService.setAccuracy(accuracy);
                coordBGService.setCreated_at(created);
                coordBGService.setRecorded_at(recorded);

                Log.i(TAG, "created in db: " + coordBGService.getCreated_at());
                Log.i(TAG, "Counter :" + counter);

                list.add(coordBGService);
                cursor.moveToNext();
            }
        }
        Log.i(TAG, String.valueOf(list));
        return list;
    }
}
