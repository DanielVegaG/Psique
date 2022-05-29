package com.example.psique;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    //atributos
        //elementos del xml
    private EditText et_phone, et_verifCode;
    private Button b_generateVerifCode, b_testVerifCode;

        //autenticación de firebase
    FirebaseAuth mAuth;

        //variables
    String verifCode;//código de verificación para la autenticación


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//oculta la statusBar
        setContentView(R.layout.activity_login);

        //inicializar atributos
        et_phone=findViewById(R.id.et_phoneNumber);
        et_verifCode=findViewById(R.id.et_verificationCode);
        b_generateVerifCode=findViewById(R.id.b_generateVerificationCode);
        b_testVerifCode=findViewById(R.id.b_testVerificationCode);

        mAuth=FirebaseAuth.getInstance();


        //acciones de botón
        /**
         * Al hacer click en el botón de generar código de verificación
         * si no está vacío el campo de introducir número,
         * se llama al método de mandar el código de verificación
         * pasándole este como parámetro
         */
        b_generateVerifCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_phone.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Introduce un número de teléfono válido",Toast.LENGTH_SHORT).show();
                }else{
                    String phoneNumber=et_phone.getText().toString().replace(" ","");
                    sendVerifCode(phoneNumber);
                }
            }
        });

        /**
         * Al hacer click en el botón de validar código de verificación
         * si no está vacío el campo del código de verificación,
         * se llama al método de mandar el código de verificación
         * pasándole este como parámetro
         */
        b_testVerifCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(et_verifCode.getText().toString())){
                    Toast.makeText(LoginActivity.this, "Código de verificación incorrecto",Toast.LENGTH_SHORT).show();
                }else{
                    String c=et_verifCode.getText().toString();
                    verifyCode(verifCode);
                }
            }
        });
    }

    //métodos
    private void sendVerifCode(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+34"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(LoginActivity.this,"Error en la verificación ", Toast.LENGTH_SHORT);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            verifCode=verificationId;


        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifCode, code);
        singInByCredentials(credential);
    }

    private void singInByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"R",Toast.LENGTH_SHORT);
                            startActivity(new Intent(LoginActivity.this,MenuActivity.class));
                        }
                    }
                });
    }

}