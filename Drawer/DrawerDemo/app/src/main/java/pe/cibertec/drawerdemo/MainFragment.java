package pe.cibertec.drawerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Android on 29/04/2017.
 */

public class MainFragment extends Fragment {

    private static final String ARG_OPTION="option";

    private String option;

    public static MainFragment newInstance(String option){
        MainFragment f = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OPTION,option);
        f.setArguments(args);
        return f;
    }

    public  MainFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            option = getArguments().getString(ARG_OPTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtOption=(TextView) view.findViewById(R.id.txt_option);
        txtOption.setText(option);
    }
}
