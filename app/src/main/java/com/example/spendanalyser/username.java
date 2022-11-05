package com.example.spendanalyser;

import static com.example.spendanalyser.MainActivity.NAME_KEY;
import static com.example.spendanalyser.MainActivity.SHARED_PREFS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class username extends AppCompatActivity {

    EditText edtName;
    Button btnSubmit;
    SharedPreferences sharedPreferences;

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        edtName = findViewById(R.id.edtUserName);
        btnSubmit = findViewById(R.id.btnAddName);

        MainBalance mainBalance = new MainBalance();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser  firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().
                        setDisplayName(edtName.getText().toString()).build();

                firebaseUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString(NAME_KEY,edtName.getText().toString());
                            editor.apply();

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                            person = new Person(edtName.getText().toString());
                            databaseReference.child("MainBalance").child(edtName.getText().toString()).setValue(mainBalance).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("USER", "Main Balance updated.");
                                 }
                            });

                            databaseReference.child("person").child(edtName.getText().toString()).child(edtName.getText().toString()).setValue(person).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("USER", "Person updated.");
                                }
                            });
                            Log.d("USER", "User profile updated.");
                            startActivity(new Intent(username.this,homeActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
}