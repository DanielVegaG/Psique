package com.example.psique.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psique.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_peopleAvatar)
    public ImageView iv_peopleAvatar;
    @BindView(R.id.tv_peopleName)
    public TextView tv_peopleName;
    @BindView(R.id.tv_peopleBio)
    public TextView tv_peopleBio;

    private Unbinder unbinder;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this,itemView);
    }
}
