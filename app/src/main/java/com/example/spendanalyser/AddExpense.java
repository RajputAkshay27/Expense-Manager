package com.example.spendanalyser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddExpense extends AppCompatActivity {
    EditText edtAmount,edtDescription;
    Button btnAddExp;
    String amount,description,type;

    Spinner spnType,spnPaidBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        edtAmount = findViewById(R.id.edtAmount);
        edtDescription = findViewById(R.id.edtDescription);
        btnAddExp = findViewById(R.id.btnAddExp);
        spnType = findViewById(R.id.spnType);
        spnPaidBy = findViewById(R.id.spnPaidBy);

        String[] optType = {"Select","Food","Books","Travel","Photocopy","Other"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,optType);
        spnType.setAdapter(typeAdapter);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        type = "";
                        break;
                    case 1:
                        type = "Food";
                        break;
                    case 2:
                        type = "Books";
                        break;
                    case 3:
                        type = "Travel";
                        break;
                    case 4:
                        type = "Photocopy";
                        break;
                    case 5:
                        type = "Other";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnAddExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = edtAmount.getText().toString();
                description = edtDescription.getText().toString();

                if(amount.isEmpty()){
                    Toast.makeText(AddExpense.this, "Please Add the Amount", Toast.LENGTH_LONG).show();
                }
                else if(description.isEmpty()){
                    Toast.makeText(AddExpense.this, "Please Add the Description", Toast.LENGTH_LONG).show();
                }
                else if(type.isEmpty()){
                    Toast.makeText(AddExpense.this, "Please select the type of Expense", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}