package com.example.psique;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CounsellingListAdapter extends RecyclerView.Adapter<CounsellingListAdapter.CounsellingViewHolder> {

    //atributos
    //lista de Counselling
    private List<Counselling> links;
    private final LayoutInflater layoutInflater;
    private final Context context;


    /**
     * Constructor para dar valores iniciales a
     *
     * @param itemList, lista de Counselling
     * @param context
     */
    public CounsellingListAdapter(List<Counselling> itemList, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.links = itemList;
    }


    /**
     * Retorna número de ítems que tiene la lista
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return links.size();
    }


    /**
     * Se le llama cuando el CounsellingLinksViewHolder necesita ser inicialiado
     * Se le pasa al layout cada ítem del RecyclerView que debería usar
     * Se hace el inflate del layout pasándole el output del constructor del antes inicializado
     *
     * @param parent
     * @param i
     * @return
     */
    @Override
    public CounsellingListAdapter.CounsellingViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.list_counselling, null);
        return new CounsellingListAdapter.CounsellingViewHolder(view);
    }


    /**
     * Para especificar los contenidos de cada ítem del RecyclerView
     * (el método es similar al getView() del adapter del ListView
     * Se establecen los valores de la imagen y del enlace
     *
     * @param counsellingViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final CounsellingListAdapter.CounsellingViewHolder counsellingViewHolder, final int position) {
        counsellingViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        counsellingViewHolder.bindData(links.get(position));
    }

    /**
     * Mete nuevos valores a la lista
     *
     * @param items
     */
    public void setItems(List<Counselling> items) {
        links = items;
    }

    /**
     *
     */
    public static class CounsellingViewHolder extends RecyclerView.ViewHolder {
        ImageView counsellingImage;
        TextView counsellingTitle;
        Button counsellingButton;
        CardView cardView;

        CounsellingViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.counsellingCardView);
            counsellingImage = view.findViewById(R.id.iv_counsellingImage);
            counsellingTitle = view.findViewById(R.id.tv_counsellingTitle);
            counsellingButton = view.findViewById(R.id.b_counsellingButton);
        }

        void bindData(final Counselling item) {
            counsellingImage.setImageResource(item.getCounsellingImageId());
            counsellingTitle.setText(item.getCounsellingTitle());
            counsellingButton.setText(item.getCounsellingArticleName());
            counsellingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getCounsellingLink()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }


}