package com.example.psique;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.psique.Models.UserModel;
import com.example.psique.Utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

//https://www.youtube.com/watch?v=cXQ0_WG-VcY&ab_channel=EDMTDev
public class RegisterUserActivity extends AppCompatActivity {

    //atributos
    @BindView(R.id.et_firstName)
    TextInputEditText et_firstName;
    @BindView(R.id.et_lastName)
    TextInputEditText et_lastName;
    @BindView(R.id.et_phone)
    TextInputEditText et_phone;
    @BindView(R.id.et_birthdate)
    TextInputEditText et_birthdate;
    @BindView(R.id.cb_professional)
    CheckBox cb_professional;
    @BindView(R.id.et_bio)
    TextInputEditText et_bio;
    @BindView(R.id.b_register)
    Button b_register;

    FirebaseDatabase database;
    DatabaseReference userRef;

    //para la fecha de nacimiento
    MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().build();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Calendar calendar = Calendar.getInstance();
    boolean isSelectBirthdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        init();

        setDefaultData();
    }

    private void setDefaultData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        et_phone.setText(user.getPhoneNumber());
        et_phone.setEnabled(false);

        et_birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    materialDatePicker.show(getSupportFragmentManager(), materialDatePicker.toString());
                }
            }
        });
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelectBirthdate || et_firstName.getText().toString().equals("")) {
                    Toast.makeText(RegisterUserActivity.this, "Por favor, es obligatorio que rellene el nombre y la fecha de nacimiento", Toast.LENGTH_LONG).show();
                    return;
                }
                UserModel userModel = new UserModel();

                userModel.setLastName(et_lastName.getText().toString());
                userModel.setPhone(et_phone.getText().toString());
                userModel.setBirthDate(calendar.getTimeInMillis());
                userModel.setProfessional(cb_professional.isChecked());

                if (cb_professional.isChecked())
                    userModel.setFirstName("Dr/a. "+et_firstName.getText().toString());
                else
                    userModel.setFirstName(et_firstName.getText().toString());

                userModel.setBio(et_bio.getText().toString());
                userModel.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

                userRef.child(userModel.getUid())
                        .setValue(userModel)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterUserActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterUserActivity.this, "Usuario creado sin problemas", Toast.LENGTH_SHORT).show();
                                Constants.currentUser = userModel;
                                startActivity(new Intent(RegisterUserActivity.this, MenuActivity.class));
                                finish();
                            }
                        });
            }
        });
    }

    private void init() {
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Constants.USER_REFERENCES);
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                calendar.setTimeInMillis(selection);
                et_birthdate.setText(simpleDateFormat.format(selection));
                isSelectBirthdate = true;
            }
        });

    }
}