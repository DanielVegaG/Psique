package com.example.psique;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    //atributos
    List<News> newsList;
    Button b_exitNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        /**
         * Salir de la pantalla de ayudas
         */
        b_exitNews = findViewById(R.id.b_exitNews);
        b_exitNews.setOnClickListener(new View.OnClickListener() {
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
        newsList = new ArrayList<>();

        int newspaperId = R.drawable.news_newspaper;
        int progressId = R.drawable.news_progress;

        newsList.add(new News(newspaperId, "Periódico", "La alegría también necesita un entrenamiento", "https://www.abc.es/bienestar/psicologia-sexo/psicologia/abci-alegria-tambien-necesita-entrenamiento-202206060104_noticia.html"));
        newsList.add(new News(newspaperId, "Periódico", "Cómo reconocer la timidez extrema y consejos para superarla", "https://www.mundodeportivo.com/vidae/psicologia-bienestar/20220606/1001816234/como-reconocer-timidez-extrema-consejos-superarla-isc.html"));
        newsList.add(new News(newspaperId, "Periódico", "Controlar nervios para selectividad", "https://www.lasexta.com/bienestar/psicologia/como-controlar-nervios-aumentar-confianza-dias-evau-selectividad-pautas-psicologos_20220606629a35c71d1a9700019e2c75.html"));
        newsList.add(new News(newspaperId, "Periódico", "Deborah Calvo, psicóloga clínico-forense", "https://www.elespanol.com/mujer/protagonistas/20220606/deborah-calvo-psicologa-clinico-forense-conozco-sinrazon-oscuro/677682242_0.html"));

        newsList.add(new News(progressId, "Avances", "The Value of Positive Psychology", "https://academic.oup.com/abm/article/39/1/4/4569218?login=false"));
        newsList.add(new News(progressId, "Avances", "Empirical Validation of Interventions.", "https://psycnet.apa.org/record/2005-08033-003"));
        newsList.add(new News(progressId, "Avances", "Bowlby's secure base theory", "https://www.tandfonline.com/doi/abs/10.1080/14616730210154216?journalCode=rahd20"));
        newsList.add(new News(progressId, "Avances", "The yin and yang of progress in social psychology", "https://psycnet.apa.org/record/1973-30203-001"));

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

        NewsListAdapter listAdapter = new NewsListAdapter(newsList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}