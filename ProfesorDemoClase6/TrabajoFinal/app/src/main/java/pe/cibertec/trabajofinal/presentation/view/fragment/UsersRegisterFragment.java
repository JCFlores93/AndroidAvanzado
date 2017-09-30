package pe.cibertec.trabajofinal.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pe.cibertec.trabajofinal.R;
import pe.cibertec.trabajofinal.presentation.presenter.UsersPresenter;
import pe.cibertec.trabajofinal.presentation.view.UsersRegister;

/**
 * Created by USUARIO on 1/06/2017.
 */

public class UsersRegisterFragment extends Fragment
    implements UsersRegister,View.OnClickListener{

    @BindView(R.id.edtNombre)
    EditText edtNombre;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPw)
    EditText  edtPw;

    @BindView(R.id.btnGuardar)
    EditText btnGuardar;

    @BindView(R.id.btnCancelar)
    EditText btnCancelar;

    private Unbinder unbinder;

    private UsersPresenter usersPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.saveusers_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        btnGuardar.setOnClickListener(this);
    }

    @Override
    public void registerUsers() {

    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar :

                break;
        }
    }
}
