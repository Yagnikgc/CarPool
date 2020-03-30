package com.example.carpool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rideList_customerAdapter extends RecyclerView.Adapter<rideList_customerAdapter.MyViewHolder> {
    private ArrayList<rideList_customer> availableRequests;
    public rideList_customerAdapter(ArrayList<rideList_customer> availableRequests){
        this.availableRequests = availableRequests;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView modelNumber, driverName, availableSeats, pricePerSeat;

        public MyViewHolder(View view) {
            super(view);
            modelNumber =  view.findViewById(R.id.modelNumber);
            driverName =  view.findViewById(R.id.driverName);
            availableSeats =  view.findViewById(R.id.availableSeats);
            pricePerSeat = view.findViewById(R.id.pricesPerSeat);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_rides_customer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        rideList_customer request = availableRequests.get(position);
        holder.modelNumber.setText(request.getModelNumber());
        holder.driverName.setText(request.getDriverName());
        holder.availableSeats.setText(request.getAvailableSeats());
        holder.pricePerSeat.setText(request.getPricePerSeat());
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
