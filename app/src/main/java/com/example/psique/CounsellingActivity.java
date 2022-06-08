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

public class CounsellingActivity extends AppCompatActivity {

    //atributos
    List<Counselling> counsellingList;
    Button b_exitCounselling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counselling);

        /**
         * Salir de la pantalla de ayudas
         */
        b_exitCounselling = findViewById(R.id.b_exitCounselling);
        b_exitCounselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CounsellingActivity.this, MenuActivity.class));
                finish();
            }
        });

        init();
    }


    /**
     * mete en la lista todos los datos de las cards
     */
    public void populateList() {
        counsellingList = new ArrayList<>();

        int bachilleratoId = R.drawable.councelling_bachelor;
        int accesoCarreraId = R.drawable.councelling_access;
        int fpId = R.drawable.councelling_fp;
        int gradoId= R.drawable.councelling_university;

        counsellingList.add(new Counselling(bachilleratoId, "Acceso a bachillerato", "Periodismo, Psicología y Criminología: ¿qué Bachillerato estudiar?", "https://www.emagister.com/blog/periodismo-psicologia-criminologia-bachillerato-estudiar/"));
        counsellingList.add(new Counselling(bachilleratoId, "Acceso a bachillerato", "Para estudiar psicología, ¿debo estudiar en ciencias o letras?", "https://www.mundopsicologos.com/consultas/para-estudiar-psicologia-debo-estudiar-en-ciencias-o-letras"));
        counsellingList.add(new Counselling(bachilleratoId, "Acceso a bachillerato", "Orientación académica", "https://www.mundopsicologos.com/consultas/para-estudiar-psicologia-debo-estudiar-en-ciencias-o-letras"));
        counsellingList.add(new Counselling(bachilleratoId, "Acceso a bachillerato", "¿Qué bachillerato se necesita para estudiar psicología?", "https://www.euroinnova.edu.es/blog/que-bachillerato-se-necesita-para-estudiar-psicologia#:~:text=Se%20debe%20tomar%20en%20cuenta,al%20grado%20de%20psicolog%C3%ADa%20educativa."));
        counsellingList.add(new Counselling(bachilleratoId, "Acceso a bachillerato", "¿Qué estudiar para ser psicóloga?", "https://www.cesurformacion.com/blog/que-hay-que-estudiar-para-ser-psicologa"));

        counsellingList.add(new Counselling(accesoCarreraId, "Acceso a la carrera", "Acceso a psicología desde humanidades/ciencias sociales", "https://educaweb.com/consulta-orientacion/acceso-psicologia-bachillerato-humanidadesciencias-sociales-1153/"));
        counsellingList.add(new Counselling(accesoCarreraId, "Acceso a la carrera", "Requisitos para acceder al grado de psicología", "https://www.unir.net/salud/revista/bachillerato-para-psicologia/"));

        counsellingList.add(new Counselling(fpId, "Ciclos de psicología", "Cursos FP Psicologia y Ciencias Sociales y de Comportamiento", "https://cursos.universia.es/Psicologia-y-Ciencias-Sociales-y-de-Comportamiento/5cp22/"));
        counsellingList.add(new Counselling(fpId, "Ciclos de psicología", "Rama FP de Servicios SocioCulturales y a la Comunidad", "https://www.todofp.es/que-estudiar/loe/servicios-socioculturales-comunidad.html"));
        counsellingList.add(new Counselling(fpId, "Ciclos de psicología", "Ciclos formativos de grado superior de psicologia sanitaria", "https://www.educaweb.com/ciclos-formativos-grado-superior-de/psicologia-sanitaria/"));
        counsellingList.add(new Counselling(fpId, "Ciclos de psicología", "Curso de Psicología Infantil y Juvenil", "https://fpformacionprofesional.com/curso-de-psicologia-infantil-y-juvenil-art"));

        counsellingList.add(new Counselling(gradoId, "Grados de psicología", "TITULACIÓN Grado en Psicología", "https://www.educaweb.com/estudio/titulacion-grado-psicologia/"));
        counsellingList.add(new Counselling(gradoId, "Grados de psicología", "Grado en Psicología UAM", "https://www.uam.es/uam/psicologia"));
        counsellingList.add(new Counselling(gradoId, "Grados de psicología", "Grado en Psicología UNED", "http://portal.uned.es/portal/page?_pageid=93,71396222&_dad=portal&_schema=PORTAL"));
        counsellingList.add(new Counselling(gradoId, "Grados de psicología", "Grado en Psicología UNIOVI", "https://psicologia.uniovi.es/infoacademica/grados/-/asset_publisher/NR6k/content/grado-en-psicologia?redirect=%2Finfoacademica%2Fgrados"));


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

        CounsellingListAdapter listAdapter = new CounsellingListAdapter(counsellingList, this);
        RecyclerView recyclerView = findViewById(R.id.rv_counselling);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}