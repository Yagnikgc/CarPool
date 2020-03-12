package com.example.carpool;
// class to store details of a customer request
// To handle all the operations for a customer request
public class CustomerRequest {
    // variables to store entities of a user
    private String customerID;
    private Double sourceLat;
    private Double sourceLong;
    private Double destinationLat;
    private Double destinationLong;
    private String rideDate;
    private String rideTime;
    private String rideRequestDate;
    private String rideRequestTime;
    private String rideRequestStatus;
    private int noOfSeatsRequired;
    // initialize entities with default value
    public CustomerRequest(){
    }
    // initialize entities with user provided value
    public CustomerRequest(String customerID, Double sourceLat, Double sourceLong, Double destinationLat, Double destinationLong, String rideDate, String rideTime, String rideRequestDate, String rideRequestTime, String requestStatus, int noOfSeats) {
        this.customerID = customerID;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.destinationLat = destinationLat;
        this.destinationLong = destinationLong;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.rideRequestDate = rideRequestDate;
        this.rideRequestTime = rideRequestTime;
        this.rideRequestStatus = requestStatus;
        this.noOfSeatsRequired = noOfSeats;
    }
    /* ----- GETTER and SETTER of all properties ----- */
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public Double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(Double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public Double getSourceLong() {
        return sourceLong;
    }

    public void setSourceLong(Double sourceLong) {
        this.sourceLong = sourceLong;
    }

    public Double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(Double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public Double getDestinationLong() {
        return destinationLong;
    }

    public void setDestinationLong(Double destinationLong) {
        this.destinationLong = destinationLong;
    }

    public String getRideDate() {
        return rideDate;
    }

    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public String getRideTime() {
        return rideTime;
    }

    public void setRideTime(String rideTime) {
        this.rideTime = rideTime;
    }

    public String getRideRequestDate() {
        return rideRequestDate;
    }

    public void setRideRequestDate(String rideRequestDate) {
        this.rideRequestDate = rideRequestDate;
    }

    public String getRideRequestTime() {
        return rideRequestTime;
    }

    public void setRideRequestTime(String rideRequestTime) {
        this.rideRequestTime = rideRequestTime;
    }

    public String getRideRequestStatus() {
        return rideRequestStatus;
    }

    public void setRideRequestStatus(String requestStatus) {
        this.rideRequestStatus = requestStatus;
    }

    public int getNoOfSeatsRequired() {
        return noOfSeatsRequired;
    }

    public void setNoOfSeatsRequired(int noOfSeats) {
        this.noOfSeatsRequired = noOfSeats;
    }
    /* ----- END ----- GETTER and SETTER of all properties ----- */
}
