package pe.cibertec.contentprovider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Android on 29/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "contacts.db";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_CONTACTS =
            "CREATE TABLE " + ContactContract.Contact.TABLE_NAME + " (" +
                    ContactContract.Contact._ID + " INTEGER PRIMARY KEY," +
                    ContactContract.Contact.COLUMN_NAME_NAME + " TEXT," +
                    ContactContract.Contact.COLUMN_NAME_PHONE + " TEXT," +
                    ContactContract.Contact.COLUMN_NAME_DOB + " TEXT)";

    private static final String SQL_DELETE_CONTACTS =
            "DROP TABLE IF EXISTS " + ContactContract.Contact.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CONTACTS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
