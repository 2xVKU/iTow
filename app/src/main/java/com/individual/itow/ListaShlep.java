package com.individual.itow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListaShlep extends AppCompatActivity {


    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<TowService> towServiceArrayList;
    TowServiceViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_shlep);
        towServiceArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {

        if(towServiceArrayList.size() > 0)
            towServiceArrayList.clear();

        db.collection("TowService")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()) {

                            TowService towService = new TowService(documentSnapshot.getString("Name")
                            , documentSnapshot.getString("E-mail"), documentSnapshot.getString("City"),
                                    documentSnapshot.getString("PhoneNumber"));

                            towServiceArrayList.add(towService);
                            adapter = new TowServiceViewAdapter(ListaShlep.this, towServiceArrayList);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListaShlep.this, "There was a problem!!!!!", Toast.LENGTH_SHORT).show();
                        Log.v("----1----", e.getMessage());
                    }
                });
    }

    private void setUpFirebase() {
        db = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
