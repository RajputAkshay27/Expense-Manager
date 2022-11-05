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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    EditText edtEmail,edtPassword,edtConPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edtEmail = findViewById(R.id.edtRegEmail);
        edtPassword = findViewById(R.id.edtRegPassword);
        edtConPassword = findViewById(R.id.edtRegConPassword);
        btnRegister = findViewById(R.id.btnRegRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String conPassword = edtConPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty() || conPassword.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(conPassword)){
                    Toast.makeText(RegistrationActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("Reg", "createUserWithEmail:success");
                        }
                        else {
                            Log.w("Reg", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}