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

public class HelpListAdapter extends RecyclerView.Adapter<HelpListAdapter.HelpViewHolder> {

    //atributos
    //lista de Help
    private List<Help> links;
    private final LayoutInflater layoutInflater;
    private final Context context;


    /**
     * Constructor para dar valores iniciales
     *
     * @param itemList, lista de Help
     * @param context
     */
    public HelpListAdapter(List<Help> itemList, Context context) {
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
     * Se le llama cuando el HelpLinksViewHolder necesita ser inicialiado
     * Se le pasa al layout cada ítem del RecyclerView que debería usar
     * Se hace el inflate del layout pasándole el output del constructor del antes inicializado
     *
     * @param parent
     * @param i
     * @return
     */
    @Override
    public HelpListAdapter.HelpViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.list_help, null);
        return new HelpListAdapter.HelpViewHolder(view);
    }


    /**
     * Para especificar los contenidos de cada ítem del RecyclerView
     * (el método es similar al getView() del adapter del ListView
     * Se establecen los valores de la imagen y del enlace
     *
     * @param helpViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final HelpListAdapter.HelpViewHolder helpViewHolder, final int position) {
        helpViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        helpViewHolder.bindData(links.get(position));
    }

    /**
     * Mete nuevos valores a la lista
     *
     * @param items
     */
    public void setItems(List<Help> items) {
        links = items;
    }


    public static class HelpViewHolder extends RecyclerView.ViewHolder {
        ImageView helpImage;
        TextView helpTitle;
        Button helpButton;
        CardView cardView;

        HelpViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.helpCardView);
            helpImage = view.findViewById(R.id.iv_helpImage);
            helpTitle = view.findViewById(R.id.tv_helpTitle);
            helpButton = view.findViewById(R.id.b_helpButton);
        }

        void bindData(final Help item) {
            helpImage.setImageResource(item.getHelpImageId());
            helpTitle.setText(item.getHelpTitle());
            helpButton.setText(item.getHelpArticleName());
            helpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getHelpLink()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }


}
