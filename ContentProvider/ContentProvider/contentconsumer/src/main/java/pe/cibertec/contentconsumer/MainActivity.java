package pe.cibertec.contentconsumer;

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
        Log.i(TAG,"Cursor count : "+ cursor.getCount());
    }

    private Cursor query(){
        ContentResolver cr = getContentResolver();
        //Son los campos que quiero que me devuelva
        String [] projection = new String[]{
                "_id",
                "name",
                "phone",
                "dob",
        };
        Uri uri = Uri.parse("content://pe.cibertec.contentprovider/contact");
        return cr.query(uri,projection,null,null,null);
    }
}
