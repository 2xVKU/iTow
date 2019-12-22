package com.individual.itow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button btnGoNext = findViewById(R.id.btnGoNext);
        btnGoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = findViewById(R.id.radioGroup);
                if(radioGroup.getCheckedRadioButtonId()==-1)
                    Toast.makeText(RegisterActivity.this, "Одберете тип корисник!", Toast.LENGTH_SHORT).show();
                else {
                    //USER CHOSE ONE OF THE RADIO BUTTONS

                    //FIND THE SELECTED RADIO BUTTON
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedId);

                    //GET THE TEXT FROM THE SELECTED RADIO BUTTON
                    String textFromRadioButton = selectedRadioButton.getText().toString();

                    if(textFromRadioButton.equals("Корисник")) {
                        Intent registerUser = new Intent(RegisterActivity.this, RegisterUser.class);
                        startActivity(registerUser);
                    }
                    else {
                        Intent registerTowService = new Intent(RegisterActivity.this, RegisterTowService.class);
                        startActivity(registerTowService);
                    }
                }
            }
        });

    }
}
