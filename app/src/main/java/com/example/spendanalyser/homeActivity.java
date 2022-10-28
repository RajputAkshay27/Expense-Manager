package com.example.spendanalyser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class homeActivity extends AppCompatActivity {

    ImageButton addExpense,viewExpense,addMainBal,imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addExpense = findViewById(R.id.imgbtnAddExp);
        viewExpense = findViewById(R.id.imgbtnViewExp);
        addMainBal = findViewById(R.id.imgbtnAddMB);
        imgView = findViewById(R.id.imgbtnView);

        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homeActivity.this,AddExpense.class));
            }
        });

        viewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addMainBal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}