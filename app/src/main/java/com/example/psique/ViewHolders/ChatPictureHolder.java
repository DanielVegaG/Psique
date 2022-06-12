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

/**
 * Holder para mandar imágenes
 */
public class ChatPictureHolder extends RecyclerView.ViewHolder {

    //atributos
    @BindView(R.id.tv_ownMessagePictureTime)
    public TextView tv_ownMessagePictureTime;
    @BindView(R.id.tv_ownMessagePictureText)
    public TextView tv_ownMessagePictureText;
    @BindView(R.id.iv_ownMessagePicturePreview)
    public ImageView iv_ownMessagePicturePreview;

    private Unbinder unbinder;

    //constructor que une atributos a su valor
    public ChatPictureHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }
}
