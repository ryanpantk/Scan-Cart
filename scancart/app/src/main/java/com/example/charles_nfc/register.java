package com.example.charles_nfc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class register extends AppCompatActivity {
    private Button signUp;
    private Button b2Login;
    private EditText nameField;
    private EditText phoneNumberField;
    private EditText streetAddressField;
    private EditText postalCodeField;
    private EditText floorAndUnitField;
    private String spinnerChoice;
    private Spinner healthConditionsChoice;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fStore;

    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Log.d(TAG, "Register Page");

        //database handlers
        firebaseAuth = FirebaseHandler.getInstanceAuth();
        fStore = FirebaseHandler.getInstanceDatabase();

        //All Fields
        nameField = findViewById(R.id.Name);
        phoneNumberField = findViewById(R.id.setPhoneNumber);
        streetAddressField = findViewById(R.id.streetAddress);
        postalCodeField = findViewById(R.id.postalcode);
        floorAndUnitField = findViewById(R.id.unitnumber);

        phoneNumberField.setText(FirebaseHandler.getPhone());

        //Retrieve ID from login
        Intent uidRegister = getIntent();
        String UID = uidRegister.getStringExtra("UUID");
        Log.d(TAG, UID);


        //Button IDS
        signUp = findViewById(R.id.signUp);
        b2Login = findViewById(R.id.b2loginName);


        //Spinner Handler
        healthConditionsChoice = (Spinner) findViewById(R.id.healthConditions);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.health_conditions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        healthConditionsChoice.setAdapter(adapter);
        healthConditionsChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
                spinnerChoice = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                spinnerChoice = parent.getItemAtPosition(5).toString();
            }

        });

//        HashMap<String, String> userDetails = new HashMap<>();

        //Sign up button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = nameField.getText().toString();
                String phoneNumber = phoneNumberField.getText().toString();
                String streetAddress = streetAddressField.getText().toString();
                String postalCode = postalCodeField.getText().toString();
                String floorAndUnit = floorAndUnitField.getText().toString();
                FirebaseHandler.User user = new FirebaseHandler().new User(UID, Name, phoneNumber, streetAddress, postalCode, floorAndUnit, spinnerChoice);
                FirebaseHandler.registerUser(user, fStore);
                startActivity(new Intent(register.this, ProfileActivity.class));

            }
        });

        b2Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, ScanActivity.class));
            }
        });


    }
}