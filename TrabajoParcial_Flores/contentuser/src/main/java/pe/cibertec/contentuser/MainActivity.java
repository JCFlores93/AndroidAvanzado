package pe.cibertec.contentuser;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Consumer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = query();
        Log.i(TAG, "Cursor count: " + cursor.getCount());
    }

    private Cursor query() {
        ContentResolver cr = getContentResolver();
        String[] projection = new String[]{
                "_id",
                "taskTitle",
                "day",
                "remember",
        };


        Uri uri = Uri.parse("content://alarma.cibertec.pe.trabajoparcial_flores/task");
        return cr.query(uri, projection, null, null, null);
    }
}
