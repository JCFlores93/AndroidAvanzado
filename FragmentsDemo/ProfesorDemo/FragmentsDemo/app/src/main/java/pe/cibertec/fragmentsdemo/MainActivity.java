package pe.cibertec.fragmentsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements ListFragment.OnContactClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContactClick(Contact contact) {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }
}
