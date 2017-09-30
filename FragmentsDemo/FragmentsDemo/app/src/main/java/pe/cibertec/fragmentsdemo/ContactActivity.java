package pe.cibertec.fragmentsdemo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Android on 22/04/2017.
 */

public class ContactActivity extends AppCompatActivity{

    public static final String EXTRA_CONTACT = "contact";
    private Contact contact;
    //mantener solo el bundle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = getIntent().getParcelableExtra(EXTRA_CONTACT);

        FragmentTransaction ft =getSupportFragmentManager().beginTransaction();
        ft.add(android.R.id.content, ContactFragment.newInstance(contact));
        ft.commit();
    }
}
