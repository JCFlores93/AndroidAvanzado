package cibertec.com.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class TareasProvider extends ContentProvider {
    private static  final int LISTAR = 1;
    private static  final int ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(TareasContract.CONTENT_AUTHORITY, TareasContract.PATH_TAREA, LISTAR);
        uriMatcher.addURI(TareasContract.CONTENT_AUTHORITY, TareasContract.PATH_TAREA+"#", ID);
    }

    private DataBaseTareas dataBaseTareas;

    @Override
    public boolean onCreate() {
        dataBaseTareas = new DataBaseTareas(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dataBaseTareas.getReadableDatabase();
        switch(uriMatcher.match(uri)){
            case LISTAR:
                if(TextUtils.isEmpty(sortOrder)){
                    sortOrder = TareasContract.Tarea.COLUMN_NAME_TITLE + " ASC";
                }
                break;
            case ID:
                if(TextUtils.isEmpty(selection)){
                    selection = TareasContract.Tarea._ID + " = " + uri.getLastPathSegment();
                }else{
                    selection += " AND " + TareasContract.Tarea._ID + " = " + uri.getLastPathSegment();
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknow uri: " + uri);
        }
        Cursor cursor = db.query(TareasContract.Tarea.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(uriMatcher.match(uri)){
            case LISTAR:
                return TareasContract.Tarea.CONTENT_TYPE;
            case ID:
                return TareasContract.Tarea.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknow uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dataBaseTareas.getWritableDatabase();
        final long id;
        switch(uriMatcher.match(uri)){
            case LISTAR:
                id = db.insert(TareasContract.Tarea.TABLE_NAME, null, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknow uri: " + uri);
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

}
