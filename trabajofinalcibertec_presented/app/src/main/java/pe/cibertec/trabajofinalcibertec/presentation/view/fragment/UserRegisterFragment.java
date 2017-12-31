package pe.cibertec.trabajofinalcibertec.presentation.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import pe.cibertec.trabajofinalcibertec.R;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class UserRegisterFragment extends
        Fragment{

    @BindView(R.id.txtEmail)
    TextView txtEmail;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    //@BindView(R.id.)

    public static final UserRegisterFragment newInstance(){
        UserRegisterFragment f = new UserRegisterFragment();
        return f;
    }

    public UserRegisterFragment(){
        //Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_register,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
