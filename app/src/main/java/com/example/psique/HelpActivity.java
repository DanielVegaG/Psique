package com.example.psique;

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

        int depressionId = R.drawable.depression;
        int anxietyId = R.drawable.anxiety;
        int eatingDisorderId = R.drawable.eating_disorder;

        helpList.add(new Help(depressionId, "Depresión", "Neurotrophins and depression", "https://www.sciencedirect.com/science/article/abs/pii/S0165614799013097"));
        helpList.add(new Help(depressionId, "Depresión", "Hypercortisolemia and Depression", "https://journals.lww.com/psychosomaticmedicine/Abstract/2005/05001/Hypercortisolemia_and_Depression.7.aspx"));
        helpList.add(new Help(depressionId, "Depresión", "Poststroke Depression: A Review", "https://journals.sagepub.com/doi/abs/10.1177/070674371005500602"));
        helpList.add(new Help(depressionId, "Depresión", "The mechanism of depression", "https://psycnet.apa.org/record/1955-01239-001"));

        helpList.add(new Help(anxietyId, "Ansiedad", "Measures of Anxiety", "https://www.ncbi.nlm.nih.gov/pmc/articles/PMC3879951/"));
        helpList.add(new Help(anxietyId, "Ansiedad", "The development of anxiety", "https://psycnet.apa.org/buy/1998-04232-001"));
        helpList.add(new Help(anxietyId, "Ansiedad", "The Psychology of Anxiety", "https://www.taylorfrancis.com/books/mono/10.4324/9781315673127/psychology-anxiety-eugene-levitt"));

        helpList.add(new Help(eatingDisorderId, "TCA", "CAUSES OF EATING DISORDERS", "http://www.gruberpeplab.com/teaching/psych3303_spring2019/documents/4.1_Polivy.Herman2002.pdf"));
        helpList.add(new Help(eatingDisorderId, "TCA", "Risk factors for eating disorders.", "https://psycnet.apa.org/buy/2007-04834-005"));
        helpList.add(new Help(eatingDisorderId, "TCA", "Outcome of Eating Disorders", "https://www.sciencedirect.com/science/article/abs/pii/S1056499308000631"));
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