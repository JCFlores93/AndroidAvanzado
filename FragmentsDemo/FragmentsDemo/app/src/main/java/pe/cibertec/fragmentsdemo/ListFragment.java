package pe.cibertec.fragmentsdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListFragment extends Fragment implements ContactAdapter.OnItemClickListener{
    private OnContactClickListener mListener;
    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;

    public ListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    //Se ejecuta luego de la vista creada
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        contactAdapter = new ContactAdapter(getDummyList(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Aseguramos que siempre nuestras vistas tienen el mismo tamaño
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(contactAdapter);
    }

    private List<Contact> getDummyList() {
        List<Contact> dummyList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Contact contact = new Contact();
            contact.setName("Contact " + i);
            contact.setPhone("999 999 999");
            contact.setAddress("XXXXXX 123");
            contact.setEmail("abc@cibertec.pe");
            Calendar calendar = Calendar.getInstance();
            contact.setDob(calendar.getTime());
            dummyList.add(contact);
        }
        return dummyList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnContactClickListener) {
            mListener = (OnContactClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(Contact contact) {
        mListener.onContactClick(contact);
    }

    public interface OnContactClickListener {
        // TODO: Update argument type and name
        void onContactClick(Contact contact);
    }
}
