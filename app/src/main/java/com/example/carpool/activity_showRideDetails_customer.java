package com.example.carpool;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class activity_showRideDetails_customer extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_show_ride_details_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String driverName = getArguments().getString("name");
        String modelNumber = getArguments().getString("modelNumber");
        String noOfSeats = getArguments().getString("noOfSeats");
        String chargesPerSeat = getArguments().getString("chargesPerSeat");
        Toast.makeText(getContext(), driverName + "\n" + modelNumber + "\n" + noOfSeats + "\n" + chargesPerSeat, Toast.LENGTH_SHORT).show();
    }
}
