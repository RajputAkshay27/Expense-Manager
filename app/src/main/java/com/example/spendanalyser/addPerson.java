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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addPerson#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addPerson extends Fragment {

    EditText edtName;
    Button btnAddPerson;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addPerson() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addPerson.
     */
    // TODO: Rename and change types and number of parameters
    public static addPerson newInstance(String param1, String param2) {
        addPerson fragment = new addPerson();
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
    String Name,currUser;
    Person person;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_person, container, false);
        edtName = view.findViewById(R.id.edtName);
        btnAddPerson = view.findViewById(R.id.btnAddPerson);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        currUser = sharedPreferences.getString(NAME_KEY,null);
        databaseReference = FirebaseDatabase.getInstance().getReference("person");

        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = edtName.getText().toString();
                if(Name.isEmpty()){
                    Toast.makeText(getContext(), "Please Enter the name", Toast.LENGTH_SHORT).show();
                }
                else {
                    person = new Person(Name);
                    addPersonDB(person);
                }
            }
        });

        return view;
    }

    public void addPersonDB(Person person){
        databaseReference.child(currUser).child(person.getPersonName()).setValue(person).
                addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(),"Data Added Successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Failed to add data"+ e.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}