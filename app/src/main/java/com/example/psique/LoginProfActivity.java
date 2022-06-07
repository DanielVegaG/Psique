package com.example.psique;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;
import com.example.psique.utils.Constants;


public class LoginProfActivity extends AppCompatActivity {

    //atributos
        //elementos del xml
    private EditText et_userId;
    private Button b_login;


    //métodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_login_prof);

        //inicializar atributos
        et_userId = findViewById(R.id.et_userId);
        b_login = findViewById(R.id.b_login);
        /**
         * Loguea al usuario
         */
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogin();
            }
        });


        initChat();

    }


    /**
     * Inicializa los ajustes necesarios para Cometchat
     */
    private void initChat(){
        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(Constants.REGION).build();
        CometChat.init(this, Constants.API_ID, appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {//si se hace correctamente
            }
            @Override
            public void onError(CometChatException e) {//si hay un error
                Toast.makeText(LoginProfActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Una vez que el usuario se ha creado correctamente, hay que loguearlo
     */
    private void initLogin(){
        String userId= et_userId.getText().toString();
        if (CometChat.getLoggedInUser() == null) {
            CometChat.login(userId, Constants.AUTH_KEY, new CometChat.CallbackListener<User>() {
                @Override
                public void onSuccess(User user) {//si sale bien
                    Toast.makeText(LoginProfActivity.this, "Init login bien", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginProfActivity.this, MenuActivity.class));//abre el menú
                }

                @Override
                public void onError(CometChatException e) {//si hay un error
                    Toast.makeText(LoginProfActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Abriendo sesión con el último usuario que inició sesión en este dispositivo...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginProfActivity.this, MenuActivity.class));//abre el menú
            // El usuario ya ha iniciado sesión
        }
    }


}