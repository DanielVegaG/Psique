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

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    //atributos
    //lista de News
    private List<News> links;
    private final LayoutInflater layoutInflater;
    private final Context context;


    /**
     * Constructor para dar valores iniciales a
     *
     * @param itemList, lista de News
     * @param context
     */
    public NewsListAdapter(List<News> itemList, Context context) {
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
     * Se le llama cuando el NewsLinksViewHolder necesita ser inicialiado
     * Se le pasa al layout cada ítem del RecyclerView que debería usar
     * Se hace el inflate del layout pasándole el output del constructor del antes inicializado
     *
     * @param parent
     * @param i
     * @return
     */
    @Override
    public NewsListAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.list_news, null);
        return new NewsListAdapter.NewsViewHolder(view);
    }


    /**
     * Para especificar los contenidos de cada ítem del RecyclerView
     * (el método es similar al getView() del adapter del ListView
     * Se establecen los valores de la imagen y del enlace
     *
     * @param newsViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final NewsListAdapter.NewsViewHolder newsViewHolder, final int position) {
        newsViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        newsViewHolder.bindData(links.get(position));
    }

    /**
     * Mete nuevos valores a la lista
     *
     * @param items
     */
    public void setItems(List<News> items) {
        links = items;
    }

    /**
     *
     */
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImage;
        TextView newsTitle;
        Button newsButton;
        CardView cardView;

        NewsViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.newsCardView);
            newsImage = view.findViewById(R.id.iv_newsImage);
            newsTitle = view.findViewById(R.id.tv_newsTitle);
            newsButton = view.findViewById(R.id.b_newsButton);
        }

        void bindData(final News item) {
            newsImage.setImageResource(item.getNewsImageId());
            newsTitle.setText(item.getNewsTitle());
            newsButton.setText(item.getNewsArticleName());
            newsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getNewsLink()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }


}
