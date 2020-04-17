package com.example.carpool;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class activity_showRideDetails_customer extends Fragment {
    private double sourceLat, sourceLong, destLat, destLong;
    private String rideDate, rideTime, driverName, modelNumber, noOfSeatsAvailable, chargesPerSeat, parentKey;
    private int noOfSeatsRequired;
    private DatabaseReference reference;
    private long maxId = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_show_ride_details_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parentKey = getArguments().getString("parentKey");
        driverName = getArguments().getString("name");
        modelNumber = getArguments().getString("modelNumber");
        noOfSeatsAvailable = getArguments().getString("noOfSeatsAvailable");
        chargesPerSeat = getArguments().getString("chargesPerSeat");
        sourceLat = getArguments().getDouble("sourceLat");
        sourceLong = getArguments().getDouble("sourceLong");
        destLat = getArguments().getDouble("destLat");
        destLong = getArguments().getDouble("destLong");
        rideDate = getArguments().getString("rideDate");
        rideTime = getArguments().getString("rideTime");
        noOfSeatsRequired = getArguments().getInt("noOfSeatsRequired");
        InitializeUI();
    }

    private void InitializeUI() {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) maxId = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        MaterialTextView lbl_rideDate = getView().findViewById(R.id.lbl_rideDate_rideDetails_cus);
        MaterialTextView lbl_source = getView().findViewById(R.id.lbl_source_rideDetails_cus);
        MaterialTextView lbl_seatsAvailable = getView().findViewById(R.id.lbl_seatsAvailable_rideDetails_cus);
        MaterialTextView lbl_seatsRequested = getView().findViewById(R.id.lbl_seatsRequested_rideDetails_cus);
        MaterialTextView lbl_charges = getView().findViewById(R.id.lbl_charges_rideDetails_cus);
        MaterialTextView lbl_destination = getView().findViewById(R.id.lbl_destination_rideDetails_cus);
        MaterialTextView lbl_driverName = getView().findViewById(R.id.lbl_driverName_rideDetails_cus);
        MaterialTextView lbl_modelNumber = getView().findViewById(R.id.lbl_modelNumber_rideDetails_cus);
        TextView lbl_showRoute = getView().findViewById(R.id.lbl_link_showRoute);
        Button btn_bookRide = getView().findViewById(R.id.btn_bookRide_cus);
        String sourceAddress = null, destinationAddress = null;
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(sourceLat, sourceLong, 1);
            sourceAddress = addresses.get(0).getAddressLine(0);
            addresses = geocoder.getFromLocation(destLat, destLong, 1);
            destinationAddress = addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lbl_rideDate.setText(String.format("Ride for %s at %s", rideDate, rideTime));
        lbl_source.setText(sourceAddress);
        lbl_destination.setText(destinationAddress);
        lbl_seatsAvailable.setText(noOfSeatsAvailable);
        lbl_seatsRequested.setText(String.valueOf(noOfSeatsRequired));
        lbl_charges.setText(chargesPerSeat);
        lbl_driverName.setText(driverName);
        lbl_modelNumber.setText(modelNumber);
        btn_bookRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerID = FirebaseAuth.getInstance().getUid();
                Date date = new Date();
                String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
                String curTime = new SimpleDateFormat("hh:mm a").format(date);
                CustomerRequest request = new CustomerRequest(customerID, sourceLat, sourceLong, destLat, destLong, rideDate, rideTime, curDate, curTime, "Requested", noOfSeatsRequired);
                reference.child("CustomerRideRequest").child(String.valueOf(maxId + 1)).setValue(request);
                DriverRequestSummary summary = new DriverRequestSummary(String.valueOf(maxId + 1), parentKey, "Confirmed", Double.valueOf(chargesPerSeat) * noOfSeatsRequired);
                reference.child("DriverRideRequestDetails").push().setValue(summary);
                reference.child(("DriverRideRequest")).child(parentKey).child("noOfSeatsRequired").setValue(Integer.valueOf(noOfSeatsAvailable) - noOfSeatsRequired);
                Toast.makeText(getContext(), "Ride Booked Successfully :)", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getContext(), activity_customerDashboard.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        lbl_showRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", sourceLat, sourceLong, destLat, destLong);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(intent);
            }
        });
    }
}
