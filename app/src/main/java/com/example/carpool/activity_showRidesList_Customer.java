package com.example.carpool;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_showRidesList_Customer extends Fragment {

    private ArrayList<rideList_customer> ridesList;
    private DatabaseReference reference;
    private double sourceLat, sourceLong, destLat, destLong;
    String rideDate, rideTime;
    int noOfSeats;
    Boolean setflag = false;
    private String name = "", modelNumber = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_rides_list_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ridesList = new ArrayList<>();
        sourceLat = getArguments().getDouble("sourceLat");
        sourceLong = getArguments().getDouble("sourceLong");
        destLat = getArguments().getDouble("destLat");
        destLong = getArguments().getDouble("destLong");
        rideDate = getArguments().getString("rideDate");
        rideTime = getArguments().getString("rideTime");
        noOfSeats = getArguments().getInt("noOfSeats");

        getAvailableRides();
    }

    private void getAvailableRides() {
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("DriverRideRequest").orderByChild("rideRequestStatus").equalTo("Posted");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    final DriverRequest request = snapshot.getValue(DriverRequest.class);
                    if (request.getNoOfSeatsRequired() <= noOfSeats && request.getRideDate().equals(rideDate)) {
                        boolean sourceInRadius = distance(sourceLat, sourceLong, request.getSourceLat(), request.getSourceLong()) < 10.0;
                        boolean destinationInRadius = distance(destLat, destLong, request.getDestinationLat(), request.getDestinationLong()) < 10.0;
                        //if (sourceInRadius && destinationInRadius) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(request.getDriverID());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                name = dataSnapshot.child("fname").getValue(String.class);
                                modelNumber = dataSnapshot.child("vehicle").child("companyName").getValue(String.class) + " " + dataSnapshot.child("vehicle").child("modelNumber").getValue(String.class);
                                Log.d("name", name + " " + modelNumber);
                                setflag = true;
                                setUI(request);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //}
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeUI() {
        RecyclerView availableRides = getView().findViewById(R.id.recycler_ridesList_customer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        availableRides.setLayoutManager(mLayoutManager);
        rideList_customerAdapter adapter = new rideList_customerAdapter(ridesList);
        availableRides.setItemAnimator(new DefaultItemAnimator());
        availableRides.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        availableRides.setAdapter(adapter);
        availableRides.addOnItemTouchListener(new RecyclerTouchListener(getContext(), availableRides, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                rideList_customer data = ridesList.get(position);
                activity_showRideDetails_customer details = new activity_showRideDetails_customer();
                Bundle bundle = new Bundle();
                bundle.putString("modelNumber", data.getModelNumber());
                bundle.putString("name", data.getDriverName());
                bundle.putString("noOfSeats", data.getAvailableSeats());
                bundle.putString("chargesPerSeat", data.getPricePerSeat());
                details.setArguments(bundle);
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.viewLayout, details);
                transaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void setUI(DriverRequest driverRequest) {
        if (setflag == true) {
            ridesList.add(new rideList_customer(modelNumber, name, String.valueOf(driverRequest.getNoOfSeatsRequired()), String.valueOf(driverRequest.getChargesPerSeat())));
            InitializeUI();
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians            ::*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees            ::*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
