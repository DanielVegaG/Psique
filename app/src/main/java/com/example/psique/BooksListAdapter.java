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

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.BooksViewHolder> {

    //atributos
    //lista de Books
    private List<Books> links;
    private final LayoutInflater layoutInflater;
    private final Context context;


    /**
     * Constructor para dar valores iniciales
     *
     * @param itemList, lista de Books
     * @param context
     */
    public BooksListAdapter(List<Books> itemList, Context context) {
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
     * Se le llama cuando el BooksLinksViewHolder necesita ser inicialiado
     * Se le pasa al layout cada ítem del RecyclerView que debería usar
     * Se hace el inflate del layout pasándole el output del constructor del antes inicializado
     *
     * @param parent
     * @param i
     * @return
     */
    @Override
    public BooksListAdapter.BooksViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = layoutInflater.inflate(R.layout.list_books, null);
        return new BooksListAdapter.BooksViewHolder(view);
    }


    /**
     * Para especificar los contenidos de cada ítem del RecyclerView
     * (el método es similar al getView() del adapter del ListView
     * Se establecen los valores de la imagen y del enlace
     *
     * @param booksViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final BooksListAdapter.BooksViewHolder booksViewHolder, final int position) {
        booksViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        booksViewHolder.bindData(links.get(position));
    }

    /**
     * Mete nuevos valores a la lista
     *
     * @param items
     */
    public void setItems(List<Books> items) {
        links = items;
    }

    /**
     *
     */
    public static class BooksViewHolder extends RecyclerView.ViewHolder {
        ImageView booksImage;
        TextView booksTitle;
        Button booksButton;
        CardView cardView;

        BooksViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.booksCardView);
            booksImage = view.findViewById(R.id.iv_booksImage);
            booksTitle = view.findViewById(R.id.tv_booksTitle);
            booksButton = view.findViewById(R.id.b_booksButton);
        }

        void bindData(final Books item) {
            booksImage.setImageResource(item.getBooksImageId());
            booksTitle.setText(item.getBooksTitle());
            booksButton.setText(item.getBooksArticleName());
            booksButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getBooksLink()));
                    view.getContext().startActivity(i);
                }
            });
        }
    }


}
