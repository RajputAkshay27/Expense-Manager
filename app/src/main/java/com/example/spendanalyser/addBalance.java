package com.example.spendanalyser;

import static com.example.spendanalyser.MainActivity.NAME_KEY;
import static com.example.spendanalyser.MainActivity.SHARED_PREFS;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addBalance#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addBalance extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addBalance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addBalance.
     */
    // TODO: Rename and change types and number of parameters
    public static addBalance newInstance(String param1, String param2) {
        addBalance fragment = new addBalance();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DatabaseReference databaseReference;
    String amount,currUser;
    MainBalance balance;
    SharedPreferences sharedPreferences;

    EditText edtAmount;
    Button btnAddAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_balance, container, false);
        edtAmount = view.findViewById(R.id.edtAmount);
        btnAddAmount = view.findViewById(R.id.addBalBtn);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currUser = sharedPreferences.getString(NAME_KEY,null);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = edtAmount.getText().toString();
                if(amount.isEmpty()){
                    Toast.makeText(getContext(), "Please Enter the name", Toast.LENGTH_SHORT).show();
                }
                else {
                    addAmount(amount);
                }
            }
        });


        return view;
    }

    public void addAmount(String amount){
        databaseReference.child("MainBalance").child(currUser).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                balance = (MainBalance) task.getResult().getValue(MainBalance.class);
                balance.setMainBalance(balance.getMainBalance() + Double.parseDouble(amount));
                databaseReference.child("MainBalance").child(currUser).setValue(balance);
            }
        });
    }
}