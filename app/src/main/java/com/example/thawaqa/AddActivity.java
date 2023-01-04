package com.example.thawaqa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name , price , image ;

    Button btnAdd, btnBack , btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText) findViewById(R.id.txtBookName);
        price = (EditText) findViewById(R.id.txtBookPrice);
        image = (EditText) findViewById(R.id.txtImgUrl);


        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnClear = (Button) findViewById(R.id.btnClear);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertNewBook();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });




        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name.setText("");
                price.setText("");
                image.setText("");
            }
        });

    }


    private void insertNewBook(){

        Map<String, Object> map = new HashMap<>();
        map.put("name" , name.getText().toString());
        map.put("image" , image.getText().toString());
        map.put("Price" , price.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Books").push()

                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this , "Book has ben insertrd" , Toast.LENGTH_SHORT).show();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {


                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(AddActivity.this , "cannot insert" , Toast.LENGTH_SHORT).show();
                    }
                });


    }
}