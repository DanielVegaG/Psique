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

public class ChatInfoHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_chatAvatar)
    public ImageView iv_chatAvatar;
    @BindView(R.id.tv_chatName)
    public TextView tv_chatName;
    @BindView(R.id.tv_chatLastMessage)
    public TextView tv_chatLastMessage;
    @BindView(R.id.tv_chatTime)
    public TextView tv_chatTime;
    private Unbinder unbinder;

    public ChatInfoHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this,itemView);
    }
}
