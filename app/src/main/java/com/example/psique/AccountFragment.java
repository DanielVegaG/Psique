package com.example.psique;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    //autenticación de firebase
    FirebaseAuth mAuth;
    //atributos
    //elementos del xml
    private Button b_logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false); //para el menú
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //inicializar atributos
        b_logout = getView().findViewById(R.id.b_logout);
        mAuth = FirebaseAuth.getInstance();

        //acciones de botón
        /**
         * Al hacer click en el botón de salir de la cuenta, se abre un AlertDialog
         * si se le da a la opción afirmativa, cierra la cuenta y vuelva a la MainActivity
         * en caso contrario, no hace nada y se cierra la alerta
         */
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Salir")
                        .setMessage("¿Está seguro de que quiere salir de la cuenta?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int in) {
                                mAuth.signOut();
                                Intent i = new Intent(getActivity(), MainActivity.class);
                                getActivity().startActivity(i);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }
}
