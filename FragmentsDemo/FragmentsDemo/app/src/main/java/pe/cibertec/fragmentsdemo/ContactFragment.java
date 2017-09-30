package pe.cibertec.fragmentsdemo;


import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Android on 22/04/2017.
 */

public class ContactFragment extends Fragment {

    private static final String ARG_CONTACT = "contact";

    private Contact contact;

    public static ContactFragment newInstance(Contact contact){
        ContactFragment f = new ContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CONTACT,contact);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            contact = getArguments().getParcelable(ARG_CONTACT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edtName = (EditText) view.findViewById(R.id.edtName);
        EditText edtPhone = (EditText) view.findViewById(R.id.edtPhone);
        EditText edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        EditText edtAddress = (EditText) view.findViewById(R.id.edtAddress);
        EditText edtDob = (EditText) view.findViewById(R.id.edtDob);
        if (contact != null){
            edtName.setText(contact.getName());
            edtPhone.setText(contact.getPhone());
            edtEmail.setText(contact.getEmail());
            edtAddress.setText(contact.getAddress());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dob = dateFormat.format(contact.getDob());
            edtDob.setText(dob);
        }
    }
}
