package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class activity_registerVehicle extends AppCompatActivity {
    Spinner spn_vehicleType,spn_manufactureYear,spn_availableSeats;
    EditText txt_modelNumber,txt_licenceNumber,txt_insuranceNumber;
    AutoCompleteTextView autoComplete_companyName;
    AutocompleteSupportFragment fragment_address;
    Button btn_registerVehicle;
    String apiKey;
    LatLng addressLatLng;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);
        InitializeUI();
        InitializeControls();
    }

    private void InitializeControls() {
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Specify the types of place data to return.
        fragment_address.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        // Set up a PlaceSelectionListener to handle the response.
        fragment_address.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                addressLatLng = place.getLatLng();
                Log.i("RegisterVehicle", "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
            }
            @Override
            public void onError(Status status) {
                Log.i("RegisterVehicle", "An error occurred: " + status);
            }
        });
        // Insert DATA on click of Register Vehicle
        btn_registerVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String vehicleType = spn_vehicleType.getSelectedItem().toString();
                final String modelNumber = txt_modelNumber.getText().toString().trim();
                final int mfYear = Integer.parseInt(spn_manufactureYear.getSelectedItem().toString());
                final String companyName = autoComplete_companyName.getText().toString();
                final int noOfSeats = Integer.parseInt(spn_availableSeats.getSelectedItem().toString());
                final String userID = FirebaseAuth.getInstance().getUid();
                final String licenceNumber = txt_licenceNumber.getText().toString().trim();
                final String insuranceNumber = txt_insuranceNumber.getText().toString().trim();
                final Double addressLat, addressLong;
                boolean hasError = false;
                if(modelNumber.equals("") || modelNumber.isEmpty()){
                    txt_modelNumber.setError("Please enter Model Number!");
                    hasError = true;
                }
                if(licenceNumber.equals("") || licenceNumber.isEmpty()){
                    txt_licenceNumber.setError("Please enter Licence Number!");
                    hasError = true;
                }
                if(insuranceNumber.equals("") || licenceNumber.isEmpty()){
                    txt_insuranceNumber.setError("Please enter Insurance NumberA");
                    hasError = true;
                }
                if(addressLatLng == null){
                    Toast.makeText(getApplicationContext(), "Please enter Address!", Toast.LENGTH_SHORT).show();
                    hasError = true;
                    addressLat = 0.0;
                    addressLong = 0.0;
                }
                else {
                    addressLat = addressLatLng.latitude;
                    addressLong = addressLatLng.longitude;
                }
                List<String> list_companyName = Arrays.asList(getResources().getStringArray(R.array.array_companyName));
                if(!list_companyName.contains(companyName)){
                    autoComplete_companyName.setError("Please select Company Name!");
                    hasError = true;
                }
                if(!hasError) {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = new User();
                            user.setFname(dataSnapshot.child(userID).child("fname").getValue().toString());
                            user.setLname(dataSnapshot.child(userID).child("lname").getValue().toString());
                            user.setContact(dataSnapshot.child(userID).child("contact").getValue().toString());
                            user.setEmail(dataSnapshot.child(userID).child("email").getValue().toString());
                            user.setUserType(dataSnapshot.child(userID).child("userType").getValue().toString());
                            user.setUserStatus(dataSnapshot.child(userID).child("userStatus").getValue().toString());
                            Vehicle vehicle = new Vehicle(vehicleType, modelNumber, mfYear, companyName, noOfSeats, licenceNumber, insuranceNumber, addressLat, addressLong, "Registered");
                            user.setVehicle(vehicle);
                            reference.child(userID).setValue(user);
                            Toast.makeText(getApplicationContext(),"Vehicle Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            // to make sure user cant go back
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void InitializeUI() {
        apiKey = getString(R.string.API_KEY);
        spn_vehicleType = findViewById(R.id.spn_vehicleType_registerVehicle);
        spn_manufactureYear = findViewById(R.id.spn_manufactureYear_registerVehicle);
        spn_availableSeats = findViewById(R.id.spn_avaiableSeats_registerVehicle);
        txt_modelNumber = findViewById(R.id.txt_modelNumber_registerVehicle);
        txt_licenceNumber = findViewById(R.id.txt_licenceNumber_registerVehicle);
        txt_insuranceNumber = findViewById(R.id.txt_insuranceNumber_registerVehicle);
        btn_registerVehicle = findViewById(R.id.btn_registerVehicle);
        autoComplete_companyName = findViewById(R.id.autoComplete_companyName_registerVehicle);
        ArrayAdapter<String> adapter_CompanyList = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.array_companyName));
        autoComplete_companyName.setThreshold(1);
        autoComplete_companyName.setAdapter(adapter_CompanyList);
        // Initialize Fragments
        fragment_address = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_address_registerVehicle);
        reference = FirebaseDatabase.getInstance().getReference().child("User");
    }
}
