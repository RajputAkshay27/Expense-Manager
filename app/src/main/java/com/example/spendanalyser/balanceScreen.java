package com.example.spendanalyser;

import static com.example.spendanalyser.MainActivity.NAME_KEY;
import static com.example.spendanalyser.MainActivity.SHARED_PREFS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class balanceScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String currUser;

    TextView txtMBValue,txtTEValue;

    DatabaseReference firebaseDatabase;
    MainBalance balance;
    Button btnAddBalance,btnback;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction,ft;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_screen);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currUser = sharedPreferences.getString(NAME_KEY,null);

        txtMBValue = findViewById(R.id.txtMBValue);
        txtTEValue = findViewById(R.id.txtTEValue);

        btnAddBalance = findViewById(R.id.btnAddBalance);
        btnback = findViewById(R.id.btnBack);

        final Fragment fragment = new addBalance();

        btnAddBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.FragBalance,fragment);
                fragmentTransaction.commit();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ft = fragmentManager.beginTransaction();
                ft.remove(fragment);
                ft.commit();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase.child("MainBalance").child(currUser).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                balance = (MainBalance) task.getResult().getValue(MainBalance.class);
                txtMBValue.setText(balance.getMainBalance().toString());
                txtTEValue.setText(balance.getTotalExpense().toString());
            }
        });
    }
}