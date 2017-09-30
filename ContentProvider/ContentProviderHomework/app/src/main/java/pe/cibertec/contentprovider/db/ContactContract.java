package pe.cibertec.contentprovider.db;

import android.provider.BaseColumns;

/**
 * Created by Android on 29/04/2017.
 */

public final class ContactContract {

    private ContactContract (){
    }

    public static class Contact implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_DOB = "dob";
    }
}
