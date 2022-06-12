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

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.InfoViewHolder> {

    //atributos
    //lista de Info
    private List<Info> links;
    private final LayoutInflater layoutInflater;
    private final Context context;


    /**
     * Constructor para dar valores iniciales
     *
     * @param itemList, lista de Info
     * @param context
     */
    public InfoListAdapter(List<Info> itemList, Context context) {
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
     * Se le llama cuando el InfoLinksViewHolder necesita ser inicialiado
     * Se le pasa al layout cada ítem del RecyclerView que debería usar
     * Se hace el inflate del layout pasándole el output del constructor del antes inicializado
     *
     * @param parent
     * @param i
     * @return
     */
    @Override
    public InfoListAdapter.InfoViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.list_info, null);
        return new InfoListAdapter.InfoViewHolder(view);
    }


    /**
     * Para especificar los contenidos de cada ítem del RecyclerView
     * (el método es similar al getView() del adapter del ListView
     * Se establecen los valores de la imagen y del enlace
     *
     * @param infoViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final InfoListAdapter.InfoViewHolder infoViewHolder, final int position) {
        infoViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        infoViewHolder.bindData(links.get(position));
    }

    /**
     * Mete nuevos valores a la lista
     *
     * @param items
     */
    public void setItems(List<Info> items) {
        links = items;
    }


    public static class InfoViewHolder extends RecyclerView.ViewHolder {
        ImageView infoImage;
        TextView infoTitle;
        Button infoButton;
        CardView cardView;

        InfoViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.infoCardView);
            infoImage = view.findViewById(R.id.iv_infoImage);
            infoTitle = view.findViewById(R.id.tv_infoTitle);
            infoButton = view.findViewById(R.id.b_infoButton);
        }

        void bindData(final Info item) {
            infoImage.setImageResource(item.getInfoImageId());
            infoTitle.setText(item.getInfoTitle());
            infoButton.setText(item.getInfoArticleName());
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getInfoLink()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }


}