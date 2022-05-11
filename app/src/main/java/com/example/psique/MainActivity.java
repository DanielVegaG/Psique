package com.example.psique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //atributes
    private EditText etEmail, etPassword, etPasswordConfirm;
    private Button bRegister, bLogin;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //components
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etPasswordConfirm=findViewById(R.id.etPasswordConfirm);

        bRegister=findViewById(R.id.bRegister);
        bLogin=findViewById(R.id.bLogIn);

        signInButton= findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        try{
            Thread.sleep(500);
        }catch (Exception ex){
            Log.e("Error in start", ex.getMessage());
        }

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter==0){
                    counter++;
                    etPasswordConfirm.setVisibility(View.VISIBLE);
                }else{
                    String email=etEmail.getText().toString();
                    String password=etPassword.getText().toString();
                    String passwordToConfirm=etPasswordConfirm.getText().toString();

                    boolean checkEmpty=noEmptyFields(email, password, passwordToConfirm);
                    boolean checkEmail= checkEmail((CharSequence) email);
                    boolean checkPassword=checkPassword(password);

                    if( checkEmpty && checkEmail && checkPassword){
                        if(password.equals(passwordToConfirm)){

                        }else{
                            Toast.makeText(MainActivity.this, "Both passwords must be equal", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account){
        if(account.getClass() == GoogleSignInAccount.class){
            /*
             el usuario ya ha iniciado sesión
             en su aplicación con Google.
              Actualice su interfaz de usuario en consecuencia,
              es decir, oculte el botón de inicio de sesión,
              inicie su actividad
               principal o lo que sea apropiado para su aplicación.
             */
        }else if( account == null){
            /*
             , el usuario aún no ha iniciado sesión en su
             aplicación con Google. Actualice su interfaz
             de usuario para mostrar el botón de inicio de sesión de Google.
             */
        }else{
            Log.d("Account", account.getClass()+"");
        }
    }

    /**
     * Checks that any of the editTexts are empty
     * @param email
     * @param password
     * @param passwordToConfirm
     */
    private boolean noEmptyFields(String email, String password, String passwordToConfirm) {
        if(email.equals("") || password.equals("") || passwordToConfirm.equals("")){
            Toast.makeText(MainActivity.this, "Any of the fields are empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Check that the email was written correctly
     * @param email
     * @return
     */
    private boolean checkEmail(CharSequence email) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(MainActivity.this, "It doesn't look like an email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Check if the password has all the characteristics mentioned below
     * @param password
     * @return
     */
    private boolean checkPassword(String password) {
        String regex="^(?=.*[0-9])" //min one digit
                + "(?=.*[a-z])(?=.*[A-Z])" //min one lowercase and uppercase char
                + "(?=.*[@#$%^&+=])" //min one special character
                + "(?=\\S+$).{8,20}$"; //no spaces and between 8 and 20 characters
        if(Pattern.matches(regex,password)){
            return false;
        }
        return true;
    }
}