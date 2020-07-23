package com.example.live4dresult;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.live4dresult.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MagnumActivity extends AppCompatActivity {
    Spinner spinner;
    String TAG = "mytag";
    DatabaseReference reference, dateReferance;
    FirebaseDatabase fdatabase;
    TextView tv_1st, tv_2nd, tv_3rd;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    User user;
    String age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnum);
        spinner = findViewById(R.id.date_spinner);
        tv_1st = findViewById(R.id.first_res);
        tv_2nd = findViewById(R.id.sec_res);
        tv_3rd = findViewById(R.id.third_res);


        fdatabase = FirebaseDatabase.getInstance();
        reference = fdatabase.getReference();

        list = new ArrayList<>();
        retriveData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user = new User();
                Query query = reference.orderByChild("name").equalTo(user.getName());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    public void retriveData() {
        reference.child("result").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    list.add(item.child("name").getValue().toString());
                }
                adapter = new ArrayAdapter<String>(MagnumActivity.this, R.layout.spinner_style, list);
                Collections.reverse(list);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}