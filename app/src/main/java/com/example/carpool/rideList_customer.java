package com.example.carpool;

public class rideList_customer {
    private String parentKey;
    private String modelNumber;
    private String driverName;
    private String availableSeats;
    private String pricePerSeat;
    public rideList_customer(){

    }

    public rideList_customer(String parentKey, String modelNumber, String driverName, String availableSeats, String pricePerSeat) {
        this.parentKey = parentKey;
        this.modelNumber = modelNumber;
        this.driverName = driverName;
        this.availableSeats = availableSeats;
        this.pricePerSeat = pricePerSeat;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
}

