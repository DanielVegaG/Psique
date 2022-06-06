package com.example.psique;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    //atributos
    List<Info> infoList;
    Button b_exitInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        /**
         * Salir de la pantalla de ayudas
         */
        b_exitInfo = findViewById(R.id.b_exitInfo);
        b_exitInfo.setOnClickListener(new View.OnClickListener() {
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
        infoList = new ArrayList<>();

        int depressionId = R.drawable.home_depression;
        int anxietyId = R.drawable.home_anxiety;
        int eatingDisorderId = R.drawable.home_eating_disorder;

        infoList.add(new Info(depressionId, "Depresión", "Depresión: Causas, síntomas y tratamiento", "https://www.cun.es/enfermedades-tratamientos/enfermedades/depresion"));
        infoList.add(new Info(depressionId, "Depresión", "Depresión clínica", "https://medlineplus.gov/spanish/ency/article/003213.htm"));
        infoList.add(new Info(depressionId, "Depresión", "¿Qué Es La Depresión?", "https://www.mhanational.org/que-es-la-depresion"));
        infoList.add(new Info(depressionId, "Depresión", "Información sobre la depresión", "https://www.nimh.nih.gov/health/publications/espanol/depresion-sp"));

        infoList.add(new Info(anxietyId, "Ansiedad", "Ansiedad, MedlinePlus", "https://medlineplus.gov/spanish/anxiety.html#:~:text=La%20ansiedad%20es%20un%20sentimiento,una%20reacci%C3%B3n%20normal%20al%20estr%C3%A9s."));
        infoList.add(new Info(anxietyId, "Ansiedad", "Cómo explicar la ansiedad", "https://www.metlife.es/blog/salud-bienestar/como-explicar-la-ansiedad/"));
        infoList.add(new Info(anxietyId, "Ansiedad", "¿Qué es la ansiedad?", "https://www.sanamente.org/retos/que-es-la-ansiedad/"));
        infoList.add(new Info(anxietyId, "Ansiedad", "La ansiedad es una emoción que nos prepara para luchar o huir", "https://psicoterapeutas.com/trastornos/ansiedad/"));

        infoList.add(new Info(eatingDisorderId, "TCA", "¿Qué son los Trastornos de la Conducta Alimentaria (TCA)?", "https://www.clinicbarcelona.org/asistencia/enfermedades/trastornos-de-la-conducta-alimentaria"));
        infoList.add(new Info(eatingDisorderId, "TCA", "Transtornos alimentarios (TCA)", "https://www.programadesconecta.com/tratamientos/tca-trastornos-alimentarios/"));
        infoList.add(new Info(eatingDisorderId, "TCA", "¿Se pueden evitar los TCA?", "https://www.acab.org/es/prevencion/se-pueden-evitar-los-tca/"));
        infoList.add(new Info(eatingDisorderId, "TCA", "Transtorno por atracón", "https://itasaludmental.com/blog/link/85"));
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

        InfoListAdapter listAdapter = new InfoListAdapter(infoList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_info);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}