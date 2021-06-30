package com.example.videomefree12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ProfileActivity extends AppCompatActivity implements ValueEventListener, OnCompleteListener<Void> {

    EditText editNickName;
    TextView codeTV;
    User user;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editNickName = findViewById(R.id.editNickName);
        codeTV = findViewById(R.id.txtCode);
        btnSave = findViewById(R.id.btnSave);
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            user = snapshot.getValue(User.class);
            editNickName.setText(user.getNickName());
        }
        else {
            user = new User();
            user.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for(int i=0;i<5; i++){
                sb.append(random.nextInt(9));
            }
            user.setCode(sb.toString());
        }
        codeTV.setText(user.getCode());
        editNickName.setEnabled(true);
        btnSave.setEnabled(true);
    }

    @Override
    public void onCancelled(@NonNull @NotNull DatabaseError error) {
        user = new User();
        user.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<5; i++){
            sb.append(random.nextInt(9));
        }
        user.setCode(sb.toString());

        codeTV.setText(user.getCode());
        editNickName.setEnabled(true);
        btnSave.setEnabled(true);
    }

    public void onClickSave(View view) {
        if(editNickName.getText().toString().trim().isEmpty()) return
        ;

        user.nickName = editNickName.getText().toString().trim();
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull @NotNull Task<Void> task) {
        Log.e("","Saved");
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}