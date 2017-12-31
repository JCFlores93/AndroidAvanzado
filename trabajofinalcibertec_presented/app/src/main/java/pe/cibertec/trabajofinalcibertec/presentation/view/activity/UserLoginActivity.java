package pe.cibertec.trabajofinalcibertec.presentation.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pe.cibertec.trabajofinalcibertec.R;
import pe.cibertec.trabajofinalcibertec.presentation.util.Constants;
import pe.cibertec.trabajofinalcibertec.presentation.view.fragment.UserLoginFragment;
import butterknife.BindView;

public class UserLoginActivity extends AppCompatActivity implements UserLoginFragment.OnEventLoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        if (savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, new UserLoginFragment());
            ft.commit();
        }
    }

    @Override
    public void onEventLogin(String user_token, Integer action) {
        if(action.equals(Constants.ACTION_LOGIN_CANCELAR)){
            finish();
        }

        if(action.equals(Constants.ACTION_LOGIN_INGRESAR)){
            Intent intent = new Intent(this,TasksMainActivity.class);
            intent.putExtra(Constants.NAME_USER_TOKEN_PARAM, user_token);
            startActivity(intent);
            finish();
        }
    }
}
