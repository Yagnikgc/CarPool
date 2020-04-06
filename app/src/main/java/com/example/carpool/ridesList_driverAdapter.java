package com.example.carpool;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class ridesList_driverAdapter extends RecyclerView.Adapter<ridesList_driverAdapter.MyViewHolder> {
    private ArrayList<rideList_driver> availableRequests;

    public ridesList_driverAdapter(ArrayList<rideList_driver> availableRequests) {
        this.availableRequests = availableRequests;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rideDate, availableSeats, pricePerSeat;
        public ImageButton directions;

        public MyViewHolder(View view) {
            super(view);
            rideDate = view.findViewById(R.id.rideDate_d);
            availableSeats = view.findViewById(R.id.availableSeats_d);
            pricePerSeat = view.findViewById(R.id.chargesPerSeat_d);
            directions = view.findViewById(R.id.directions_d);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_rides_driver, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final rideList_driver request = availableRequests.get(position);
        holder.rideDate.setText("Ride date: " + request.getRideDate());
        holder.availableSeats.setText("Available Seats: " + request.getAvailableSeats());
        holder.pricePerSeat.setText("Charges/Seat: " + request.getPricePerSeat());
        holder.directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Directions", "SET ROUTE");
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", request.getSourceLatLng().latitude, request.getSourceLatLng().longitude, request.getDestinationLatLng().latitude, request.getDestinationLatLng().longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (availableRequests != null) {
            return availableRequests.size();
        } else {
            return 0;
        }
    }
}
