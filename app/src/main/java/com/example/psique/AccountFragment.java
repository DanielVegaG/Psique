package com.example.psique;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    //atributos
        //elementos del xml
    private Button b_logout;

        //autenticación de firebase
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_account,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        b_logout=(Button)getView().findViewById(R.id.b_logout);
        mAuth=FirebaseAuth.getInstance();

        //acciones de botón
        /**
         * Cierra sesión y va a la main activity
         */
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutOnClick(view);
            }
        });
    }

    private void logoutOnClick(View view){
        mAuth.signOut();
        Intent i = new Intent(getActivity(), MainActivity.class);
        ((MenuActivity)getActivity()).startActivity(i);
    }
}
