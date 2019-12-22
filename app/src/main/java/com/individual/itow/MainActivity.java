package com.individual.itow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    
    TextView infoRegister; //TEXTVIEW FOR REGISTER IF NOT REGISTERED
    ProgressBar progressBar;
    EditText inputEmail, inputPassword;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String showName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        //LOGIN BUTTON
        Button btnNajava = findViewById(R.id.btnNajava);
        btnNajava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Сите полиња се задолжителни", Toast.LENGTH_SHORT).show();
                }


                //AUTH GOES HERE
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                
                                Toast.makeText(MainActivity.this, showName, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, LoggedInAsUser.class));

                            }

                            else {
                                Toast.makeText(MainActivity.this, "Невалиден внес, обидете се повторно!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }

            }
        });
        
        
        //IF NOT REGISTERED BUTTON
        infoRegister = findViewById(R.id.infoRegister);
        infoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO REGISTER ACTIVITY
                Intent registerActivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
                progressBar.setVisibility(View.GONE);
            }
        });
        
        
        
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBarLogin);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
    }
}
