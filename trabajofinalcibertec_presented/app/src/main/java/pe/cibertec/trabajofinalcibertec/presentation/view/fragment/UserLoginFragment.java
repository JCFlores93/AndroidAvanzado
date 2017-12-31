package pe.cibertec.trabajofinalcibertec.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pe.cibertec.trabajofinalcibertec.R;
import pe.cibertec.trabajofinalcibertec.presentation.UsersView;
import pe.cibertec.trabajofinalcibertec.presentation.model.UsersModel;
import pe.cibertec.trabajofinalcibertec.presentation.presenter.UsersPresenter;
import pe.cibertec.trabajofinalcibertec.presentation.util.Constants;

/**
 * Created by USUARIO on 3/06/2017.
 */

public class UserLoginFragment extends Fragment
    implements UsersView,View.OnClickListener{

    public OnEventLoginListener mListener;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.txtResponse)
    TextView txtResponse;

    private Unbinder unbinder;

    private UsersPresenter usersPresenter;

    public UserLoginFragment(){
        //Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_login,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        if (savedInstanceState == null){
            usersPresenter = new  UsersPresenter(this);
        }
        btnLogin.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventLoginListener) {
            mListener = (OnEventLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventLoginListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin :
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                UsersModel usersModel = new UsersModel(null);
                usersModel.setEmail(email);
                usersModel.setPassword(password);
                usersPresenter.login(usersModel);//todo agregar el presenter

                break;
            case R.id.btnCancel:
                mListener.onEventLogin(null, Constants.ACTION_LOGIN_CANCELAR);
                break;
            default:
                break;
        }
    }

    @Override
    public void renderResponseLogin(UsersModel userModel) {
        //Aquí obtenemos el user Token , se perdieron varios datos en el proceso xD
        txtResponse.setText(userModel.getUserToken());
        mListener.onEventLogin(userModel.getUserToken(), Constants.ACTION_LOGIN_INGRESAR);
    }

    public interface OnEventLoginListener{
        void onEventLogin(String user_token, Integer action);
    }
}
