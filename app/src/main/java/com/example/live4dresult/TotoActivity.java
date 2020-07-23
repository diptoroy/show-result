package com.example.live4dresult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class TotoActivity extends AppCompatActivity {
    EditText editText,edage;
    Button button, btget;
    TextView tv,tvage;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    String getname,getage, TAG="mytag";
    Tag mytag;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toto);
        editText = findViewById(R.id.edname);
        edage=findViewById(R.id.edage);
        button = findViewById(R.id.btSave);
        btget = findViewById(R.id.btget);
        tv = findViewById(R.id.tv);
        tvage=findViewById(R.id.tvage);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("result");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = editText.getText().toString();
                int age=Integer.parseInt(edage.getText().toString());
                String key=reference.push().getKey();
                reference.child(key).child("name").setValue(string);
                reference.child(key).child("age").setValue(age);

            }
        });

        btget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               reference.child("1st").addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {


                       Map<String, Object> data= (Map<String, Object>) snapshot.getValue();
                       Log.d(TAG,"onDataChange: Name "+data.get("name"));
                       Log.d(TAG,"onDataChange: Age "+data.get("age"));

                       Object s=data.get("name");
                       String st=s.toString();
                       tv.setText(st);
                      /* for(DataSnapshot ds: snapshot.getChildren()){
                           getname=ds.child("name").toString();
                           getage=ds.child("age").toString();
                           tv.setText(getname);
                           tvage.setText(getage);
                       }*/

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

            }
        });
    }
}