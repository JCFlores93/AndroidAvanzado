package com.cesarynga.cleanarchitecture.presentation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesarynga.cleanarchitecture.R;
import com.cesarynga.cleanarchitecture.presentation.model.UserModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private List<UserModel> userList;

    private OnItemClickListener onItemClickListener;

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final UserModel userModel = this.userList.get(position);
        holder.bind(userModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onUserItemClick(userModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.userList != null ? this.userList.size() : 0;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_user)
        TextView txtUser;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(UserModel userModel) {
            txtUser.setText(userModel.getUsername());
        }
    }

    public interface OnItemClickListener {
        void onUserItemClick(UserModel userModel);
    }
}
