package com.example.spendanalyser;

import static com.example.spendanalyser.MainActivity.NAME_KEY;
import static com.example.spendanalyser.MainActivity.SHARED_PREFS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddExpense extends AppCompatActivity {
    EditText edtAmount,edtDescription;
    Button btnAddExp;
    String amount,description,type,paidBy,currUser;

    Spinner spnType,spnPaidBy;

    DatabaseReference mDatabase;
    SharedPreferences sharedPreferences;
    Person person;
    MainBalance mainBalance;

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
                type = optType[i];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currUser = sharedPreferences.getString(NAME_KEY,null);

//        mDatabase.child("person").child(currUser).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.getValue() != null){
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        String[] optPaidBy = {"Select",currUser}; // todo
        ArrayAdapter<String> paidByAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,optPaidBy);
        spnPaidBy.setAdapter(paidByAdapter);

        spnPaidBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0)
                    paidBy = optPaidBy[i];
                else
                    paidBy = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                else if(paidBy.isEmpty()){
                    Toast.makeText(AddExpense.this, "Please select who paid for the expense", Toast.LENGTH_LONG).show();
                }
                else{
                    Expense expense = new Expense(amount,type,description,paidBy);
                    addExpense(expense);
                    addExpenseToPerson(expense.getPaidBy());
                    addToMainBalance(expense.getAmount());
                }
            }
        });
    }

    public void addExpenseToPerson(String name){
        mDatabase.child("person").child(currUser).child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    person = task.getResult().getValue(Person.class);
                    if(person.getLend() > 0){
                        if((person.getLend() - Double.parseDouble(amount)) > 0){
                            person.setLend(person.getLend() - Double.parseDouble(amount));
                        }
                        else {
                            person.setLend(0.0);
                            person.setBorrow(Double.parseDouble(amount) - person.getLend());
                        }
                    }
                    else {
                        person.setBorrow(Double.parseDouble(amount) + person.getBorrow());
                    }
                }
                mDatabase.child("person").child(currUser).child(name).setValue(person);
            }
        });
    }


    public void addExpense(Expense expense){
        String key = mDatabase.child("expense").child(currUser).push().getKey();
        mDatabase.child("expense").child(currUser).child(key).setValue(expense).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddExpense.this, "Data Added to the database", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddExpense.this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addToMainBalance(String amount){
        mDatabase.child("MainBalance").child(currUser).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                mainBalance = (MainBalance) task.getResult().getValue(MainBalance.class);
                mainBalance.setTotalExpense(mainBalance.getTotalExpense() + Double.parseDouble(amount));

                mDatabase.child("MainBalance").child(currUser).setValue(mainBalance);
            }
        });

    }
}