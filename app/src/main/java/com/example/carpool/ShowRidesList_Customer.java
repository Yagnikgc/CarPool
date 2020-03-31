package com.example.carpool;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class ShowRidesList_Customer extends Fragment {

    private RecyclerView availableRides;
    private rideList_customerAdapter adapter;
    ArrayList<rideList_customer> ridesList;
    DatabaseReference reference;
    Double sourceLat, sourceLong, destLat, destLong;
    String rideDate, rideTime;
    int noOfSeats;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_rides_list_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sourceLat = getArguments().getDouble("sourceLat");
        sourceLong = getArguments().getDouble("sourceLong");
        destLat = getArguments().getDouble("destLat");
        destLong = getArguments().getDouble("destLong");
        rideDate = getArguments().getString("rideDate");
        rideTime = getArguments().getString("rideTime");
        noOfSeats = getArguments().getInt("noOfSeats");
        ridesList = new ArrayList<>();
        getAvailableRides();
    }

    private void getAvailableRides() {
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("DriverRideRequest").orderByChild("rideRequestStatus").equalTo("Posted");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    final DriverRequest request = snapshot.getValue(DriverRequest.class);
                    if(request.getNoOfSeatsRequired() <= noOfSeats && request.getRideDate().equals(rideDate)) {
                        boolean sourceInRadius = distance(sourceLat, sourceLong, request.getSourceLat(), request.getSourceLong()) < 10.0;
                        boolean destinationInRadius = distance(destLat, destLong, request.getDestinationLat(), request.getDestinationLong()) < 10.0;
                        //if (sourceInRadius && destinationInRadius) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(request.getDriverID());
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name = dataSnapshot.child("fname").getValue(String.class);
                                Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        ridesList.add(new rideList_customer("1",name,String.valueOf(request.getNoOfSeatsRequired()),String.valueOf(request.getChargesPerSeat())));
                        //}
                    }
                }
                InitializeUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //ridesList.add(new rideList_customer("1","1.1","1.1","1.1"));
        //ridesList.add(new rideList_customer("1","1.1","1.1","1.1"));
        //ridesList.add(new rideList_customer("1","1.1","1.1","1.1"));
    }

    private void InitializeUI() {
        availableRides = getView().findViewById(R.id.recycler_ridesList_customer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        availableRides.setLayoutManager(mLayoutManager);
        adapter = new rideList_customerAdapter(ridesList);
        availableRides.setAdapter(adapter);
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
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
