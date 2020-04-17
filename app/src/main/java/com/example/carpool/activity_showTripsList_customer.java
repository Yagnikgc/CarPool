package com.example.carpool;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class activity_showTripsList_customer extends Fragment {
    private ArrayList<historyList_customer> ridesList;
    RecyclerView availableRides;
    private DatabaseReference reference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_ridehistory_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getAvailableRides();
    }

    private void InitializeUI() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        availableRides.setLayoutManager(mLayoutManager);
        historyList_customerAdapter adapter = new historyList_customerAdapter(ridesList);
        availableRides.setItemAnimator(new DefaultItemAnimator());
        availableRides.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        availableRides.setAdapter(adapter);
    }

    private void getAvailableRides() {
        availableRides = getView().findViewById(R.id.recycler_rideHistory_customer);
        ridesList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("CustomerRideRequest").orderByChild("customerID").equalTo(FirebaseAuth.getInstance().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    final CustomerRequest request = snapshot.getValue(CustomerRequest.class);
                    if (request.getRideRequestStatus().equals("Requested")) {
                        try {
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(request.getSourceLat(), request.getSourceLong(), 1);
                            String sourceCityName = addresses.get(0).getLocality();
                            addresses = geocoder.getFromLocation(request.getDestinationLat(), request.getDestinationLong(), 1);
                            String destinationCityName = addresses.get(0).getLocality();
                            ridesList.add(new historyList_customer(sourceCityName, destinationCityName, request.getRideDate(), new LatLng(request.getSourceLat(), request.getSourceLong()), new LatLng(request.getDestinationLat(), request.getDestinationLong())));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    InitializeUI();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
