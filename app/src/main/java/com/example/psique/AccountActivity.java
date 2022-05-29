package com.example.psique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    //atributos
    //elementos del xml
    private Button b_logout;

    //autenticación de firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        b_logout=findViewById(R.id.b_logout);
        mAuth=FirebaseAuth.getInstance();

        //acciones de botón
        /**
         *
         */
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}