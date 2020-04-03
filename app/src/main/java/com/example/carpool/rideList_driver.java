package com.example.carpool;

import com.google.android.gms.maps.model.LatLng;

public class rideList_driver {
    private String rideDate;
    private String ridePostedDate;
    private String availableSeats;
    private String pricePerSeat;
    private LatLng sourceLatLng;
    private LatLng destinationLatLng;

    public rideList_driver() {

    }

    public rideList_driver(String rideDate, String ridePostedDate, String availableSeats, String pricePerSeat, LatLng sourceLatLng, LatLng destinationLatLng) {
        this.rideDate = rideDate;
        this.ridePostedDate = ridePostedDate;
        this.availableSeats = availableSeats;
        this.pricePerSeat = pricePerSeat;
        this.sourceLatLng = sourceLatLng;
        this.destinationLatLng = destinationLatLng;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public String getRidePostedDate() {
        return ridePostedDate;
    }

    public void setRidePostedDate(String ridePostedDate) {
        this.ridePostedDate = ridePostedDate;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(String pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public LatLng getSourceLatLng() {
        return sourceLatLng;
    }

    public void setSourceLatLng(LatLng sourceLatLng) {
        this.sourceLatLng = sourceLatLng;
    }

    public LatLng getDestinationLatLng() {
        return destinationLatLng;
    }

    public void setDestinationLatLng(LatLng destinationLatLng) {
        this.destinationLatLng = destinationLatLng;
    }
}

