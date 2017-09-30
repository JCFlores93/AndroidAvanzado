package pe.cibertec.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pe.cibertec.contentprovider.db.ContactContract;
import pe.cibertec.contentprovider.model.Contact;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //logt
    private static final String TAG = "MainActivity";
    private EditText edtName,edtPhone,edtDob;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       edtName=(EditText)findViewById(R.id.edt_name);
        edtPhone=(EditText)findViewById(R.id.edt_phone);
        edtDob=(EditText)findViewById(R.id.edt_dob);

        ListView listView = (ListView) findViewById(R.id.list_view);
        adapter = new ArrayAdapter<Contact>(this,android.R.layout.simple_list_item_1,contactList);
        listView.setAdapter(adapter);

        Button btnInsert = (Button)findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(this);

        showContactList();
    }

    private void showContactList(){
        contactList.clear();
        contactList.addAll(getContactList());
        adapter.notifyDataSetChanged();
    }
    //ContentValues conjunto de valores que recibe la base de datos

    private ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(ContactContract.Contact.COLUMN_NAME_NAME, edtName.getText().toString());
        values.put(ContactContract.Contact.COLUMN_NAME_PHONE, edtPhone.getText().toString());
        values.put(ContactContract.Contact.COLUMN_NAME_DOB, edtDob.getText().toString());
        return values;
    }

        private Cursor query(){
            ContentResolver cr = getContentResolver();
            //Son los campos que quiero que me devuelva
            String [] projection = new String[]{
                    ContactContract.Contact._ID,
                    ContactContract.Contact.COLUMN_NAME_NAME,
                    ContactContract.Contact.COLUMN_NAME_PHONE,
                    ContactContract.Contact.COLUMN_NAME_DOB,
            };
            return cr.query(ContactContract.Contact.CONTENT_URI,projection,null,null,null);
        }

        private Contact cursorToContactList(Cursor cursor){
            Contact contact = new Contact();
            contact.id = cursor.getLong(0);
            contact.name= cursor.getString(1);
            contact.phone= cursor.getString(2);
            contact.dob= cursor.getString(3);
            return contact;
        }

        private List<Contact> getContactList(){
            Cursor cursor= query();
            List<Contact> contactList = new ArrayList<>();
            if (cursor != null){
                while (cursor.moveToNext()){
                    Contact contact = cursorToContactList(cursor);
                    contactList.add(contact);
                }
                //Cerrar porqye consume memoria
                cursor.close();
            }
            return contactList;
        }

    private void insert(ContentValues values){
        //Para hacer uso del provider se usa esta clase
        ContentResolver cr = getContentResolver();
        Uri uri = cr.insert(ContactContract.Contact.CONTENT_URI,values);
        if (uri != null){
            Log.i(TAG , "Inserted row id :" + uri.getLastPathSegment());
        }else{
            Log.i(TAG,"Uri is null");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_insert:
                insert(getContentValues());
                showContactList();
                break;
        }
    }
}
