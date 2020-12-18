package com.example.traveljourney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private ArrayList<String> list;
    private image_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("images");
        getdata();
    }
        private void getdata()
    {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    progressBar.setVisibility(View.GONE);
                    list=new ArrayList<>();
                    for(DataSnapshot shot: snapshot.getChildren()){
                        String data=shot.getValue().toString();
                        list.add(data);
                    }
                    recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    adapter = new image_adapter(list,MainActivity.this);
                    recyclerview.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);

                }
            });
        }
    }