package com.example.carpool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.functions.FirebaseFunctions;

import java.util.ArrayList;

public class ShowRidesList_Customer extends Fragment {

    private RecyclerView availableRides;
    private rideList_customerAdapter adapter;
    ArrayList<rideList_customer> ridesList;
    FirebaseFunctions functions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_rides_list_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ridesList = new ArrayList<>();
        getAvailableRides();
        availableRides = getView().findViewById(R.id.recycler_ridesList_customer);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        availableRides.setLayoutManager(mLayoutManager);
        adapter = new rideList_customerAdapter(ridesList);
        availableRides.setAdapter(adapter);
    }

    private void getAvailableRides() {
        // call Firebase function and bind data to inflater
        functions = FirebaseFunctions.getInstance();
        ridesList.add(new rideList_customer("1","1.1","1.1","1.1"));
        ridesList.add(new rideList_customer("1","1.1","1.1","1.1"));
        ridesList.add(new rideList_customer("1","1.1","1.1","1.1"));
    }
}
