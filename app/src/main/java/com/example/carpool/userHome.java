package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class userHome extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    AutocompleteSupportFragment autocompleteFragment_from_userHome, autocompleteFragment_to_userHome;
    String apiKey;
    EditText selectDate,selectTime;
    Spinner spn_noOfSeats;
    Button btn_findRide;
    LatLng sourceLatLng, destLatLng;
    private int mYear, mMonth, mDay, mHour, mMinute;
    final Calendar myCalendar = Calendar.getInstance();
    // Object to store currently logged in user
    SharedPreferences sharedPreferences;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        InitializeControls();
    }

    private void InitializeControls() {
        databaseHandler = new DatabaseHandler(getApplicationContext());
        sharedPreferences = getSharedPreferences("CarPool",Context.MODE_PRIVATE);
        Toast.makeText(getApplicationContext(), sharedPreferences.getString("userName",null),Toast.LENGTH_LONG).show();
        final DatePickerDialog date = new DatePickerDialog(userHome.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
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
        final TimePickerDialog time = new TimePickerDialog(userHome.this, new TimePickerDialog.OnTimeSetListener() {
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
        apiKey = getString(R.string.API_KEY);
        /**
         * Initialize Places. For simplicity, the API key is hard-coded. In a production
         * environment we recommend using a secure mechanism to manage API keys.
         */
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Initialize Fragments
        autocompleteFragment_from_userHome = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_from_userHome);
        autocompleteFragment_to_userHome = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_to_userHome);
        // Specify the types of place data to return.
        autocompleteFragment_from_userHome.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment_to_userHome.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment_from_userHome.setOnPlaceSelectedListener(new PlaceSelectionListener() {
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
        autocompleteFragment_to_userHome.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                destLatLng = place.getLatLng();
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
            }
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        selectDate=(EditText)findViewById(R.id.date_userHome);
        selectTime=(EditText)findViewById(R.id.time_userHome);
        spn_noOfSeats = findViewById(R.id.spn_noOfSeats_userHome);
        btn_findRide = findViewById(R.id.btn_searchRide_userHome);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
                InsertRideRequest();
            }
        });
    }

    private void InsertRideRequest() {
        int custID = sharedPreferences.getInt("userID",0);
        Double sourceLat = sourceLatLng.latitude;
        Double sourceLong = sourceLatLng.longitude;
        Double destLat = destLatLng.latitude;
        Double destLong = destLatLng.longitude;
        String rideDate = selectDate.getText().toString();
        String rideTime = selectTime.getText().toString();
        Date date = new Date();
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String curTime = new SimpleDateFormat("hh:mm a").format(date);
        int noOfSeats = Integer.parseInt(spn_noOfSeats.getSelectedItem().toString());
        if(1 == databaseHandler.insertCustomerRequest(custID, sourceLat, sourceLong, destLat, destLong, rideDate, rideTime, curDate, curTime, noOfSeats)){
            Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_SHORT).show();
        }
    }
}