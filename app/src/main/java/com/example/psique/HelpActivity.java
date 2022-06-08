package com.example.psique;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    //atributos
    List<Help> helpList;
    Button b_exitHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        /**
         * Salir de la pantalla de ayudas
         */
        b_exitHelp = findViewById(R.id.b_exitHelp);
        b_exitHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this, MenuActivity.class));
                finish();
            }
        });

        init();
    }


    /**
     * mete en la lista todos los datos de las cards
     */
    public void populateList() {
        helpList = new ArrayList<>();

        int depressionId = R.drawable.home_depression;
        int anxietyId = R.drawable.home_anxiety;
        int eatingDisorderId = R.drawable.home_eating_disorder;

        helpList.add(new Help(depressionId, "Depresión", "Tips para la depresión", "https://cinfasalud.cinfa.com/p/depresion/"));
        helpList.add(new Help(depressionId, "Depresión", "15 consejos para superar la depresión", "https://institutret.com/mejores-consejos-superar-depresion/"));
        helpList.add(new Help(depressionId, "Depresión", "5 formas de superar la depresión", "https://kidshealth.org/es/teens/depression-tips.html"));

        helpList.add(new Help(anxietyId, "Ansiedad", "10 consejos sobre cómo reducir la ansiedad", "https://orientacionpsicologica.es/10-consejos-como-reducir-la-ansiedad/"));
        helpList.add(new Help(anxietyId, "Ansiedad", "¿Cómo controlar la ansiedad?", "https://www.bachrescue.com/es-es/explora/blog/2021/como-controlar-la-ansiedad-y-8-consejos-para-combatirla/"));
        helpList.add(new Help(anxietyId, "Ansiedad", "5 formas de afrontar la ansiedad", "https://kidshealth.org/es/teens/anxiety-tips.html"));

        helpList.add(new Help(eatingDisorderId, "TCA", "Los 3 consejos de María, 10 años después de superar un trastorno alimentario", "http://cometeelmundotca.es/index.php/blog/item/246-los-3-consejos-de-maria-10-anos-despues-de-haber-superado-un-trastorno-alimentario"));
        helpList.add(new Help(eatingDisorderId, "TCA", "12 consejos para prevenir los trastornos de conducta alimentaria", "https://www.redcenit.com/12-consejos-para-prevenir-los-trastornos-de-conducta-alimentaria/"));
        helpList.add(new Help(eatingDisorderId, "TCA", "¿Qué puedo hacer para ayudar a una persona querida que sufre TCA?", "https://www.acab.org/es/los-trastornos-de-conducta-alimentaria/que-puedo-hacer-para-ayudar-a-una-persona-querida-que-sufre-tca/"));
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

        HelpListAdapter listAdapter = new HelpListAdapter(helpList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_help);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}