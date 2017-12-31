package cibertec.com.db;

import android.net.Uri;
import android.provider.BaseColumns;

public final class TareasContract {
    public static final String CONTENT_AUTHORITY = "pe.apptareas.provider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_TAREA = "tarea";

    public TareasContract() {
    }

    public static class Tarea implements BaseColumns{
        public static final String TABLE_NAME = "tarea";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE_TIME = "date_time";
        public static final String COLUMN_NAME_REMEMBER = "remember";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAREA).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd."+CONTENT_AUTHORITY+"/"+PATH_TAREA;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."+CONTENT_AUTHORITY+"/"+PATH_TAREA;
    }
}
