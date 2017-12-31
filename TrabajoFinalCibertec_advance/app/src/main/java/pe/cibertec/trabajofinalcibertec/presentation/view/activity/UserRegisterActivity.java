package pe.cibertec.trabajofinalcibertec.presentation.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pe.cibertec.trabajofinalcibertec.R;
import pe.cibertec.trabajofinalcibertec.presentation.view.fragment.UserRegisterFragment;

public class UserRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        if (savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, UserRegisterFragment.newInstance());
            ft.commit();
        }
    }
}
