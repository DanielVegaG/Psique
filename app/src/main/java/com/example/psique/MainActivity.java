package com.example.psique;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    //atributes
    Button b_loginProf, b_loginUser, b_quitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_main);

        /**
         * Muestra el logo en pantalla completa al iniciar la app
         */
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
            Log.e("Error in start: ", ex.getMessage());
        }

        //Inicializar atributos
        b_loginProf = findViewById(R.id.b_loginProf);
        /**
         * Abre la activity del login del profesional
         */
        b_loginProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginProfActivity.class));
                finish();
            }
        });

        b_loginUser = findViewById(R.id.b_loginUser);
        /**
         * Abre la activity del login de usuario
         */
        b_loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginUserActivity.class));
                finish();
            }
        });

        b_quitar = findViewById(R.id.b_quitar);
        b_quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();
            }
        });
    }

    /**
     * Si hay una sesión no cerrada se inicia directamente el menú
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
            finish();
        }
    }


}