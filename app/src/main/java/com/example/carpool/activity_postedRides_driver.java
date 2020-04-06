package com.example.carpool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class activity_postedRides_driver extends Fragment {
    private ArrayList<rideList_driver> ridesList;
    private DatabaseReference reference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_show_rides_list_driver, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ridesList = new ArrayList<>();
        getAvailableRides();
    }

    private void getAvailableRides() {
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("DriverRideRequest").orderByChild("driverID").equalTo(FirebaseAuth.getInstance().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    final DriverRequest request = snapshot.getValue(DriverRequest.class);
                    if (request.getRideRequestStatus().equals("Posted")) {
                        ridesList.add(new rideList_driver(request.getRideDate(), request.getRideRequestDate(), String.valueOf(request.getNoOfSeatsRequired()), String.valueOf(request.getChargesPerSeat()), new LatLng(request.getSourceLat(), request.getSourceLong()), new LatLng(request.getDestinationLat(), request.getDestinationLong())));
                    }
                }
                InitializeUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeUI() {
        RecyclerView availableRides = getView().findViewById(R.id.recycler_ridesList_driver);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        availableRides.setLayoutManager(mLayoutManager);
        ridesList_driverAdapter adapter = new ridesList_driverAdapter(ridesList);
        availableRides.setItemAnimator(new DefaultItemAnimator());
        availableRides.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        availableRides.setAdapter(adapter);
    }
}
