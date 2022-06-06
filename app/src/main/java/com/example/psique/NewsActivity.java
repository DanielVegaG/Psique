package com.example.psique;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    //atributos
    List<News> newsList;
    Button b_exitNews;
    RadioButton rb_newsDate, rb_newsScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //inicializar atributos
        rb_newsDate = findViewById(R.id.rb_newsDate);
        rb_newsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init("date");
            }
        });

        rb_newsScope = findViewById(R.id.rb_newsScope);
        rb_newsScope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init("");
            }
        });
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

        init("");
    }


    /**
     * mete en la lista todos los datos de las cards
     */
    public void populateList() {
        newsList = new ArrayList<>();

        int newspaperId = R.drawable.news_newspaper;
        int progressId = R.drawable.news_progress;

        newsList.add(new News(newspaperId, "Periódico", "La alegría también necesita un entrenamiento", "https://www.abc.es/bienestar/psicologia-sexo/psicologia/abci-alegria-tambien-necesita-entrenamiento-202206060104_noticia.html", "2022/06/06"));
        newsList.add(new News(newspaperId, "Periódico", "Cómo reconocer la timidez extrema y consejos para superarla", "https://www.mundodeportivo.com/vidae/psicologia-bienestar/20220606/1001816234/como-reconocer-timidez-extrema-consejos-superarla-isc.html", "2022/06/06"));
        newsList.add(new News(newspaperId, "Periódico", "Controlar nervios para selectividad", "https://www.lasexta.com/bienestar/psicologia/como-controlar-nervios-aumentar-confianza-dias-evau-selectividad-pautas-psicologos_20220606629a35c71d1a9700019e2c75.html", "2022/06/06"));
        newsList.add(new News(newspaperId, "Periódico", "Deborah Calvo, psicóloga clínico-forense", "https://www.elespanol.com/mujer/protagonistas/20220606/deborah-calvo-psicologa-clinico-forense-conozco-sinrazon-oscuro/677682242_0.html", "2022/06/06"));

        newsList.add(new News(progressId, "Avances", "Revista de la Facultad de Psicología y Humanidades Vol.29 Num.2", "https://revistas.unife.edu.pe/index.php/avancesenpsicologia/issue/view/183", "2021/11/19"));
        newsList.add(new News(progressId, "Avances", "¿Los animales no domésticos perciben nuestras emociones si les hablamos?", "https://noticiasdelaciencia.com/art/44373/los-animales-no-domesticos-perciben-nuestras-emociones-si-les-hablamos", "2022/06/03"));
        newsList.add(new News(progressId, "Avances", "Oxitocina y conducta prosocial", "https://noticiasdelaciencia.com/art/44368/oxitocina-y-conducta-prosocial", "2022/06/03"));
        newsList.add(new News(progressId, "Avances", "Extirpar tumores cerebrales preservando mejor un área del lenguaje", "https://noticiasdelaciencia.com/art/44366/extirpar-tumores-cerebrales-preservando-mejor-un-area-del-lenguaje", "2022/06/03"));
        newsList.add(new News(progressId, "Avances", "El poder tranquilizante del abrazo en la pareja", "https://noticiasdelaciencia.com/art/44310/el-poder-tranquilizante-del-abrazo-en-la-pareja", "2022/05/26"));
        newsList.add(new News(progressId, "Avances", "Una cura para el Mal de Parkinson", "https://noticiasdelaciencia.com/art/44245/creacion-artificial-de-neuronas-a-partir-de-otras-celulas-una-cura-para-el-mal-de-parkinson", "2022/05/17"));
        newsList.add(new News(progressId, "Avances", "Cómo el dormir ayuda a procesar las emociones", "https://noticiasdelaciencia.com/art/44266/como-el-dormir-ayuda-a-procesar-las-emociones", "2022/05/19"));
        newsList.add(new News(progressId, "Avances", "Arándanos y riesgo de demencia", "https://noticiasdelaciencia.com/art/44234/arandanos-y-riesgo-de-demencia", "2022/05/16"));
        newsList.add(new News(progressId, "Avances", "¿Hay en el cerebro del perro un circuito específico para procesar el lenguaje humano?", "https://noticiasdelaciencia.com/art/44244/hay-en-el-cerebro-del-perro-un-circuito-especifico-para-procesar-el-lenguaje-humano", "2022/05/17"));
        newsList.add(new News(progressId, "Avances", "Enfermedad del hígado graso no alcohólico y estigmatización social", "https://noticiasdelaciencia.com/art/44236/enfermedad-del-higado-graso-no-alcoholico-y-estigmatizacion-social", "2022/05/16"));


    }

    /**
     * LLama al método de meter los objetos a la lista
     * Declara el ReclyclerView
     * Declara el adaptador
     * Declara el layoutManager para el recyclerView
     * Da el adaptador al recyclerView
     *
     * @param sort, ordena la lista según el valor que se pase. Si es "date", por fecha
     *              y si va vacío por ámbito (newsTitle)
     */
    public void init(String sort) {
        populateList();

        //ordenar lista
        if (sort.equals("date")) {
            for (int i = 0; i < newsList.size(); i++) {
                Log.e("Tamaño", i + "");
                if (i + 1 < newsList.size()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    //try{
                    String firstDate = newsList.get(i).getNewsTitle();
                    Toast.makeText(this, "Primera fecha: " + firstDate, Toast.LENGTH_LONG).show();
                    //if(sdf.parse(newsList.get(i).getNewsDate()).before(sdf.parse(newsList.get(i+1).getNewsDate()))){
                    //Toast.makeText(this, sdf.parse(newsList.get(i).getNewsDate()).before(sdf.parse(newsList.get(i+1).getNewsDate()))+"hola", Toast.LENGTH_SHORT).show();
                    //}
                    //}catch (ParseException pe){
                    //Log.e("Error",pe.getMessage());
                    //}

                }
            }
        }

        NewsListAdapter listAdapter = new NewsListAdapter(newsList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }


}