package com.individual.itow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    EditText fieldPassword, fieldRepeatPassword, fieldEmail, fieldName;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseFirestore fireStore;
    private static final String TAG = "MyActivity";
    //USER ID
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_user);


        findViews();
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        Button btnUserRegister = findViewById(R.id.btnUserRegister);

        if(firebaseAuth.getCurrentUser()!=null) {
            startActivity(new Intent(RegisterUser.this, LoggedInAsUser.class));
            finish();
        }

        btnUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = fieldName.getText().toString();
                final String email = fieldEmail.getText().toString();
                String pass1 = fieldPassword.getText().toString();
                String pass2 = fieldRepeatPassword.getText().toString();
                final String typeUser = "user";
                if(name.equals("") || email.equals("") || pass1.equals("") || pass2.equals("")) {
                    Toast.makeText(RegisterUser.this, "Сите полиња треба да се пополнат!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!pass1.equals(pass2)) {
                        Toast.makeText(RegisterUser.this, "Лозинките не се совпаѓаат", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        ////////////////////////////
                        // STORE USER IN DATABASE /
                        //////////////////////////
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(email, pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {

                                    UID = firebaseAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fireStore.collection("users").document(UID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Name", name);
                                    user.put("E-mail", email);
                                    user.put("Type", typeUser);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: userProfile is created for " + UID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(RegisterUser.this, LoggedInAsUser.class));
                                }
                            }
                        });

                        ////////////////////////
                        // AFTER STORING USER /
                        //////////////////////
                    }
                }
            }
        });

    }

    private void findViews() {
        fieldPassword = findViewById(R.id.fieldPassword);
        fieldRepeatPassword = findViewById(R.id.fieldRepeatPasswordTow);
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldName = findViewById(R.id.fieldName);
        progressBar = findViewById(R.id.progressBarRegisterUser);
    }
}
