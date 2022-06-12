package com.example.psique.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.psique.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Clase que sirve para los mensajes de texto recibidos
 */
public class ChatTextReceiveHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_friendChatMessage)
    public TextView tv_friendChatMessage;
    @BindView(R.id.tv_friendChatTime)
    public TextView tv_friendChatTime;


    private Unbinder unbinder;

    //constructor
    public ChatTextReceiveHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }
}
