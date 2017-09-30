package pe.cibertec.cleanarchitecture.presentation.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import pe.cibertec.cleanarchitecture.R;
import pe.cibertec.cleanarchitecture.presentation.view.fragment.NewsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager()
                    .beginTransaction();
            ft.add(android.R.id.content, new NewsFragment());
            ft.commit();
        }
    }
}
