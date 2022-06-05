package com.example.psique;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    //atributos
    Button b_help, b_info, b_articles, b_news, b_counselling;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);//para el menú
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //inicializar atributos
        b_help = (Button) getView().findViewById(R.id.b_help);
        b_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HelpActivity.class));
            }
        });

        b_info = (Button) getView().findViewById(R.id.b_info);
        b_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InfoActivity.class));
            }
        });

        b_articles = (Button) getView().findViewById(R.id.b_articles);
        b_articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ArticlesActivity.class));
            }
        });

        b_news = (Button) getView().findViewById(R.id.b_news);
        b_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewsActivity.class));

            }
        });

        b_counselling = (Button) getView().findViewById(R.id.b_counselling);
        b_counselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CounsellingActivity.class));
            }
        });
    }
}
