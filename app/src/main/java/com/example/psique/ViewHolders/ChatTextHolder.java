package com.example.psique.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psique.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatTextHolder extends RecyclerView.ViewHolder {

    //atributos
    @BindView(R.id.tv_ownChatMessage)
    public TextView tv_ownChatMessage;
    @BindView(R.id.tv_ownChatTime)
    public TextView tv_ownChatTime;


    private Unbinder unbinder;

    public ChatTextHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }
}
