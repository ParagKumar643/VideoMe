package com.example.videomefree12.Games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.videomefree12.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Games extends AppCompatActivity {

    Button tiktak;

    EditText editCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        tiktak = findViewById(R.id.TicTakToe);
        editCode = findViewById(R.id.edtCode);
        tiktak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Games.this, TicTakToeActivity.class));
            }
        });

    }

    public void onClickSearchCode(View view) {

        String code = editCode.getText().toString().trim();
        if(code.length()!=5){
            Toast.makeText(this,"Invalid code",Toast.LENGTH_SHORT).show();
            return;
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.orderByChild("code").equalTo(code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    startActivity(new Intent(Games.this, TicTakToeActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(Games.this,"Invalid code",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(Games.this,"Invalid code",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}