package pe.cibertec.fragmentsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ListFragment.OnContactClickListener{

        private boolean isDualPane=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDualPane = getResources().getBoolean(R.bool.dual_pane);

    }

    @Override
    public void onContactClick(Contact contact) {

        if (isDualPane){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container,ContactFragment.newInstance(contact));
            ft.commit();
        }else {
            Intent intent = new Intent(this, ContactActivity.class);
            intent.putExtra(ContactActivity.EXTRA_CONTACT, contact);
            startActivity(intent);

        }
        }
}
