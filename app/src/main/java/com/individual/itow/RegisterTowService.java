package com.individual.itow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class RegisterTowService extends AppCompatActivity {

    EditText fieldEmailTow, fieldPasswordTow, fieldRepeatPasswordTow, fieldNameTow, fieldPhoneNumber;
    Spinner odberiGrad;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tow_service);

        initializeViews();



        Button btnTowServiceRegister = findViewById(R.id.btnTowServiceRegister);
        btnTowServiceRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailTow = fieldEmailTow.getText().toString();
                final String passwordTow = fieldPasswordTow.getText().toString();
                final String passwordTow2 = fieldRepeatPasswordTow.getText().toString();
                final String phoneNumber = fieldPhoneNumber.getText().toString();
                final String nameTow = fieldNameTow.getText().toString();
                final String selectedCity = String.valueOf(odberiGrad.getSelectedItem());
                if(emailTow.equals("") || passwordTow.equals("") || passwordTow2.equals("") || nameTow.equals("")) {

                    Toast.makeText(RegisterTowService.this, "Сите полиња се задолжителни!", Toast.LENGTH_SHORT).show();

                }
                else if(selectedCity.equals("Одбери град"))
                    Toast.makeText(RegisterTowService.this, "Одберете град!", Toast.LENGTH_SHORT).show();
                else if(!passwordTow.equals(passwordTow2))
                    Toast.makeText(RegisterTowService.this, "Лозинките не се совпаѓаат!", Toast.LENGTH_SHORT).show();

                else {
                    progressBar.setVisibility(View.VISIBLE);

                    ////////////////////////////
                    // STORE USER IN DATABASE /
                    //////////////////////////
                    firebaseAuth.createUserWithEmailAndPassword(emailTow,passwordTow).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                                UID = firebaseAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fireStore.collection("TowService").document(UID);
                                Map<String, Object> towService = new HashMap<>();
                                towService.put("Name", nameTow);
                                towService.put("E-mail", emailTow);
                                towService.put("City", selectedCity);
                                towService.put("PhoneNumber", phoneNumber);
                                documentReference.set(towService);


                            }
                        }
                    });


                    ////////////////////////
                    // AFTER STORING USER /
                    //////////////////////
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(RegisterTowService.this, LoggedInAsUser.class));

                }
            }
        });

    }

    private void initializeViews() {
        fieldEmailTow = findViewById(R.id.fieldEmailTow);
        fieldPasswordTow = findViewById(R.id.fieldPasswordTow);
        fieldRepeatPasswordTow = findViewById(R.id.fieldRepeatPasswordTow);
        fieldNameTow = findViewById(R.id.fieldNameTow);
        fieldPhoneNumber = findViewById(R.id.fieldPhoneNumber);
        odberiGrad = findViewById(R.id.odberiGrad);
        progressBar = findViewById(R.id.progressBarRegisterTow);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
    }
}
