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

public class ChatPictureReceiveHolder extends RecyclerView.ViewHolder {
    //atributos
    @BindView(R.id.tv_friendMessagePictureTime)
    public TextView tv_friendMessagePictureTime;
    @BindView(R.id.tv_friendMessagePictureText)
    public TextView tv_friendMessagePictureText;
    @BindView(R.id.iv_friendMessagePicturePreview)
    public ImageView iv_friendMessagePicturePreview;

    private Unbinder unbinder;

    public ChatPictureReceiveHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }
}
