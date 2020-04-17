package com.example.carpool;

import com.google.android.gms.maps.model.LatLng;

public class historyList_customer {
    private String sourceCityName;
    private String destinationCityName;
    private String rideDate;
    private LatLng sourceLatLng;
    private LatLng destinationLatLng;

    public historyList_customer(String sourceCityName, String destinationCityName, String rideDate, LatLng sourceLatLng, LatLng destinationLatLng) {
        this.sourceCityName = sourceCityName;
        this.destinationCityName = destinationCityName;
        this.rideDate = rideDate;
        this.sourceLatLng = sourceLatLng;
        this.destinationLatLng = destinationLatLng;
    }

    public String getSourceCityName() {
        return sourceCityName;
    }

    public void setSourceCityName(String sourceCityName) {
        this.sourceCityName = sourceCityName;
    }

    public String getDestinationCityName() {
        return destinationCityName;
    }

    public void setDestinationCityName(String destinationCityName) {
        this.destinationCityName = destinationCityName;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
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
