package cibertec.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cibertec.com.model.Tarea;

public class DataBaseTareas extends SQLiteOpenHelper {
    private static final String DB_NAME = "tareas.db";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_TAREAS =
            "CREATE TABLE " + TareasContract.Tarea.TABLE_NAME + " (" +
                    TareasContract.Tarea._ID + " INTEGER PRIMARY KEY," +
                    TareasContract.Tarea.COLUMN_NAME_TITLE + " TEXT," +
                    TareasContract.Tarea.COLUMN_NAME_DATE_TIME + " TEXT," +
                    TareasContract.Tarea.COLUMN_NAME_REMEMBER + " INTEGER)";

    private static final String SQL_DELETE_TAREAS =
            "DROP TABLE IF EXISTS " + TareasContract.Tarea.TABLE_NAME;

    public DataBaseTareas(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

        @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablaTarea(db);
        cargarDummyDatosTarea(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TAREAS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void crearTablaTarea(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_TAREAS);
    }

    private void cargarDummyDatosTarea(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, "Tarea primera clase");
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, "28/05/2017 13:00");
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, 0);
        db.insert(TareasContract.Tarea.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, "Tarea segunda clase");
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, "28/05/2017 13:00");
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, 0);
        db.insert(TareasContract.Tarea.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, "Trabajo Parcial");
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, "28/05/2017 13:00");
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, 1);
        db.insert(TareasContract.Tarea.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, "Tarea tercera clase");
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, "28/05/2017 13:00");
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, 1);
        db.insert(TareasContract.Tarea.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, "Tarea cuarta clase");
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, "28/05/2017 13:00");
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, 1);
        db.insert(TareasContract.Tarea.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(TareasContract.Tarea.COLUMN_NAME_TITLE, "Trabajo Final");
        values.put(TareasContract.Tarea.COLUMN_NAME_DATE_TIME, "28/05/2017 13:00");
        values.put(TareasContract.Tarea.COLUMN_NAME_REMEMBER, 1);
        db.insert(TareasContract.Tarea.TABLE_NAME, null, values);
    }

    public boolean insertTarea(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TareasContract.Tarea.TABLE_NAME, null, values);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean updateTarea(Tarea tarea, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TareasContract.Tarea.TABLE_NAME, values, "_id = ?", new String[]{""+tarea.getId()});
        return true;
    }

    public Integer deleteTarea(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TareasContract.Tarea.TABLE_NAME, "_id = ?", new String[]{""+id});
    }

    public Cursor listarTareas(String[] projection){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TareasContract.Tarea.TABLE_NAME, projection, null, null, null, null, "_id DESC");
    }
}
