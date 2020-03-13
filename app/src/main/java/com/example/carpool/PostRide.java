package com.example.carpool;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostRide extends Fragment {
    private static final String TAG = "PostRide";
    private AutocompleteSupportFragment autocompleteFragment_from, autocompleteFragment_to;
    private String apiKey, userType;
    private EditText selectDate,selectTime,txt_charges;
    private Spinner spn_noOfSeats;
    private Button btn_findRide;
    private LatLng sourceLatLng, destLatLng;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private final Calendar myCalendar = Calendar.getInstance();
    // Object to store currently logged in user
    private SharedPreferences sharedPreferences;
    private DatabaseReference reference;
    private long maxId = 0;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.post_ride, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitializeUI();
        InitializeControls();
    }
    private void InitializeUI() {
        sharedPreferences = getActivity().getSharedPreferences("CarPool", Context.MODE_PRIVATE);
        userType = sharedPreferences.getString("UserType",null);
        apiKey = getString(R.string.API_KEY);
        // Initialize Fragments
        autocompleteFragment_from = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_from_postRide);
        autocompleteFragment_to = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment_to_postRide);
        selectDate = getView().findViewById(R.id.date_postRide);
        selectTime = getView().findViewById(R.id.time_postRide);
        txt_charges = getView().findViewById(R.id.txt_charges);
        spn_noOfSeats = getView().findViewById(R.id.spn_noOfSeats_postRide);
        btn_findRide = getView().findViewById(R.id.btn_searchRide_postRide);
    }
    private void InitializeControls() {
        final DatePickerDialog date = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
                selectDate.setText(sdf.format(myCalendar.getTime()));
            }
        },mYear,mMonth,mDay);
        final TimePickerDialog time = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                String myFormat = "hh:mm a"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
                selectTime.setText(sdf.format(myCalendar.getTime()));
            }
        },mHour,mMinute, false);
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), apiKey);
        }
        // Specify the types of place data to return.
        autocompleteFragment_from.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment_to.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment_from.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                sourceLatLng = place.getLatLng();
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
            }
            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        autocompleteFragment_to.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                destLatLng = place.getLatLng();
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
            }
            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar tempMaxDate = Calendar.getInstance();
                tempMaxDate.add(Calendar.DAY_OF_MONTH,15);
                date.getDatePicker().setMinDate(System.currentTimeMillis());
                date.getDatePicker().setMaxDate(tempMaxDate.getTimeInMillis());
                date.show();
            }
        });
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.show();
            }
        });
        btn_findRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerID = FirebaseAuth.getInstance().getUid();
                Double sourceLat = sourceLatLng.latitude;
                Double sourceLong = sourceLatLng.longitude;
                Double destLat = destLatLng.latitude;
                Double destLong = destLatLng.longitude;
                String rideDate = selectDate.getText().toString();
                String rideTime = selectTime.getText().toString();
                Double charges = Double.valueOf(txt_charges.getText().toString());
                Date date = new Date();
                String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                String curTime = new SimpleDateFormat("hh:mm a").format(date);
                int noOfSeats = Integer.parseInt(spn_noOfSeats.getSelectedItem().toString());
                    reference = FirebaseDatabase.getInstance().getReference().child("DriverRideRequest");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) maxId = (dataSnapshot.getChildrenCount());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                    DriverRequest request = new DriverRequest(customerID,sourceLat,sourceLong,destLat,destLong,rideDate,rideTime,curDate,curTime,"Posted",noOfSeats,charges);
                    reference.child(String.valueOf(maxId+1)).setValue(request);
                Toast.makeText(getContext(),"Ride Posted Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
