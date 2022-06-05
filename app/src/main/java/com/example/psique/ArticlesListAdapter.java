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

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.ArticlesViewHolder> {

    //atributos
    //lista de Articles
    private List<Articles> links;
    private LayoutInflater layoutInflater;
    private Context context;


    /**
     * Constructor para dar valores iniciales a
     * @param itemList, lista de Articles
     * @param context
     */
    public ArticlesListAdapter(List<Articles> itemList, Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.links = itemList;
    }


    /**
     * Retorna número de ítems que tiene la lista
     * @return
     */
    @Override
    public int getItemCount(){
        return links.size();
    }


    /**
     * Se le llama cuando el ArticlesLinksViewHolder necesita ser inicialiado
     * Se le pasa al layout cada ítem del RecyclerView que debería usar
     * Se hace el inflate del layout pasándole el output del constructor del antes inicializado
     * @param parent
     * @param i
     * @return
     */
    @Override
    public ArticlesListAdapter.ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int i){
        View view=layoutInflater.inflate(R.layout.list_articles,null);
        return new ArticlesListAdapter.ArticlesViewHolder(view);
    }


    /**
     * Para especificar los contenidos de cada ítem del RecyclerView
     * (el método es similar al getView() del adapter del ListView
     * Se establecen los valores de la imagen y del enlace
     * @param articlesViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ArticlesListAdapter.ArticlesViewHolder articlesViewHolder, final int position){
        articlesViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        articlesViewHolder.bindData(links.get(position));
    }

    /**
     * Mete nuevos valores a la lista
     * @param items
     */
    public void setItems(List<Articles> items) {
        links = items;
    }

    /**
     *
     */
    public static class ArticlesViewHolder extends RecyclerView.ViewHolder{
        ImageView articlesImage;
        TextView articlesTitle;
        Button articlesButton;
        CardView cardView;

        ArticlesViewHolder(View view){
            super(view);
            cardView=(CardView)view.findViewById(R.id.articlesCardView);
            articlesImage = (ImageView) view.findViewById(R.id.iv_articlesImage);
            articlesTitle= (TextView) view.findViewById(R.id.tv_articlesTitle);
            articlesButton = (Button) view.findViewById(R.id.b_articlesButton);
        }

        void bindData(final Articles item){
            articlesImage.setImageResource(item.getArticlesImageId());
            articlesTitle.setText(item.getArticlesTitle());
            articlesButton.setText(item.getArticlesArticleName());
            articlesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getArticlesLink()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }




}