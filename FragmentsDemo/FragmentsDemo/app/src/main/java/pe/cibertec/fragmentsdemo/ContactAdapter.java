package pe.cibertec.fragmentsdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Android on 22/04/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;
    private OnItemClickListener listener;

    public ContactAdapter(@NonNull List<Contact> contacts,
                          OnItemClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtInitial ,txtName;
        OnItemClickListener listener;

        public ViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            txtInitial = (TextView) itemView.findViewById(R.id.txtInitial);
            txtName= (TextView) itemView.findViewById(R.id.txtName);
            this.listener = listener;


        }

        public void bind(final Contact contact){
            txtInitial.setText(String.valueOf(contact.getName().charAt(0)));
            txtInitial.setText(contact.getName());
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(contact);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

}
