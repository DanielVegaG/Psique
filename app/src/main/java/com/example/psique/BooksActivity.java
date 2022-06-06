package com.example.psique;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    //atributos
    List<Books> booksList;
    Button b_exitBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        /**
         * Salir de la pantalla de ayudas
         */
        b_exitBooks = findViewById(R.id.b_exitBooks);
        b_exitBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();
    }


    /**
     * mete en la lista todos los datos de las cards
     */
    public void populateList() {
        booksList = new ArrayList<>();


        booksList.add(new Books(R.drawable.book_psicologia_para_principiantes, "Psicología en español", "Psicología para principiantes", "https://es.es1lib.org/book/960080/587088"));
        booksList.add(new Books(R.drawable.book_analizar_personas, "Psicología en español", "Cómo analizar a las personas", "https://es.es1lib.org/book/15342134/45fd7a"));
        booksList.add(new Books(R.drawable.book_psicologia_relaciones_humanas, "Psicología en español", "Psicología de las relaciones humanas", "https://es.es1lib.org/book/3553261/9f059c"));
        booksList.add(new Books(R.drawable.book_psicologia_color, "Psicología en español", "Psicología del color", "https://es.es1lib.org/book/2179635/f5f4b8"));
        booksList.add(new Books(R.drawable.book_entrenamiento_mental, "Psicología en español", "Entrenamiento mental", "https://es.es1lib.org/book/888310/06c3d4"));
        booksList.add(new Books(R.drawable.book_seduccion, "Psicología en español", "Psicología de la seducción", "https://es.es1lib.org/book/3593188/a1fa6f"));
        booksList.add(new Books(R.drawable.book_entrenamiento_habilidades, "Psicología en español", "Entrenamiento de habilidades Comunicativas", "https://es.es1lib.org/book/16294570/243007"));
        booksList.add(new Books(R.drawable.book_objetos, "Psicología en español", "La psicología de los objetos cotidianos", "https://es.es1lib.org/book/977189/4e6d26"));
        booksList.add(new Books(R.drawable.book_gran_libro, "Psicología en español", "El libro de la psicología", "https://es.es1lib.org/book/11858311/d79049"));

        booksList.add(new Books(R.drawable.book_psychology_book, "Psicología en inglés", "The Psychology Book", "https://es.es1lib.org/book/2335218/f37d31"));
        booksList.add(new Books(R.drawable.book_warfare, "Psicología en inglés", "The Art Of Psychological Warfare", "https://es.es1lib.org/book/3046245/db4cd2"));
        booksList.add(new Books(R.drawable.book_psych101, "Psicología en inglés", "Psych 101", "https://es.es1lib.org/book/2285656/e97214"));
        booksList.add(new Books(R.drawable.book_flow, "Psicología en inglés", "Flow: The Psychology of Optimal Experience", "https://es.es1lib.org/book/1280379/8014fe"));
        booksList.add(new Books(R.drawable.book_mindset, "Psicología en inglés", "Mindset: The New Psychology of Success", "https://es.es1lib.org/book/3430605/fa7618"));
        booksList.add(new Books(R.drawable.book_very_short_introduction, "Psicología en inglés", "Psychology: A Very Short Introduction", "https://es.es1lib.org/book/3677705/e74d3a"));
        booksList.add(new Books(R.drawable.book_apa, "Psicología en inglés", "APA Dictionary of Psychology", "https://es.es1lib.org/book/2542154/8e4ac3"));
        booksList.add(new Books(R.drawable.book_manual, "Psicología en inglés", "Publication Manual of the American Psychological Association", "https://es.es1lib.org/book/5412383/d136ed"));
        booksList.add(new Books(R.drawable.book_30seconds, "Psicología en inglés", "30-Second Psychology", "https://es.es1lib.org/book/2780346/f0f208"));

        booksList.add(new Books(R.drawable.book_50ideias, "Psicología en portugués", "50 Ideias de Psicologia Que Voce Precisa Conhecer", "https://es.es1lib.org/book/2950070/ea2796"));
        booksList.add(new Books(R.drawable.book_o_livro, "Psicología en portugués", "O Livro da Psicologia", "https://es.es1lib.org/book/5008175/bfb914"));
        booksList.add(new Books(R.drawable.book_cores, "Psicología en portugués", "A psicologia das cores", "https://es.es1lib.org/book/2768233/1cbb74"));
        booksList.add(new Books(R.drawable.book_a_psicologia_das_emocoes, "Psicología en portugués", "A Psicologia das Emoções", "https://es.es1lib.org/book/2476778/118e5e"));
        booksList.add(new Books(R.drawable.book_tudo_o_que_voce, "Psicología en portugués", "Tudo o que você precisa saber sobre Psicologia", "https://es.es1lib.org/book/3570666/7e243a"));
        booksList.add(new Books(R.drawable.book_cognitiva, "Psicología en portugués", "Manual de Psicologia Cognitiva", "https://es.es1lib.org/book/3558879/5fbecb"));
        booksList.add(new Books(R.drawable.book_social, "Psicología en portugués", "Psicologia social", "https://es.es1lib.org/book/3607763/be20fc"));
        booksList.add(new Books(R.drawable.book_creatividade, "Psicología en portugués", "Psicologia da Criatividade", "https://es.es1lib.org/book/2614971/a61d3b"));
        booksList.add(new Books(R.drawable.book_leigos, "Psicología en portugués", "Psicologia para Leigos", "https://es.es1lib.org/book/3406197/3eae24"));

    }

    /**
     * LLama al método de meter los objetos a la lista
     * Declara el ReclyclerView
     * Declara el adaptador
     * Declara el layoutManager para el recyclerView
     * Da el adaptador al recyclerView
     */
    public void init() {
        populateList();

        BooksListAdapter listAdapter = new BooksListAdapter(booksList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_books);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}