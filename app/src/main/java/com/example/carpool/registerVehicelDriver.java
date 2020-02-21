package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class registerVehicelDriver extends AppCompatActivity {
    String TAG = "MainActivity";
    Spinner spn_vehicleType,spn_manufactureYear,spn_availableSeats;
    EditText txt_modelNumber,txt_licenceNumber,txt_insuranceNumber;
    AutoCompleteTextView autoComplete_companyName;
    AutocompleteSupportFragment fragment_address;
    String apiKey;
    LatLng sourceLatLng, destLatLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicel_driver);
        InitializeControls();
    }

    private void InitializeControls() {
        apiKey = getString(R.string.API_KEY);
        spn_vehicleType = findViewById(R.id.spn_vehicleType_registerVehicle);
        spn_manufactureYear = findViewById(R.id.spn_manufactureYear_registerVehicle);
        spn_availableSeats = findViewById(R.id.spn_avaiableSeats_registerVehicle);
        txt_modelNumber = findViewById(R.id.txt_modelNumber_registerVehicle);
        txt_licenceNumber = findViewById(R.id.txt_licenceNumber_registerVehicle);
        txt_insuranceNumber = findViewById(R.id.txt_insuranceNumber_registerVehicle);
        autoComplete_companyName = findViewById(R.id.autoComplete_companyName_registerVehicle);
        ArrayAdapter<String>adapter_CompanyList = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.array_companyName));
        autoComplete_companyName.setThreshold(1);
        autoComplete_companyName.setAdapter(adapter_CompanyList);
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Initialize Fragments
        fragment_address = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_address_registerVehicle);
        // Specify the types of place data to return.
        fragment_address.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        // Set up a PlaceSelectionListener to handle the response.
        fragment_address.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                sourceLatLng = place.getLatLng();
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }
}
