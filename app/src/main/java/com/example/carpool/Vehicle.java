package com.example.carpool;

public class Vehicle {
    // variables to store entities of a user
    private String vehicleType;
    private String modelNumber;
    private int manufactureYear;
    private String companyName;
    private int noOfSeats;
    private String licenceNumber;
    private String insuranceNumber;
    private Double addressLat;
    private Double addressLong;
    private String vehicleStatus;
    public Vehicle() {
    }
    public Vehicle(String vehicleType, String modelNumber, int manufactureYear, String companyName, int noOfSeats, String licenceNumber, String insuranceNumber, Double addressLat, Double addressLong, String vehicleStatus) {
        this.vehicleType = vehicleType;
        this.modelNumber = modelNumber;
        this.manufactureYear = manufactureYear;
        this.companyName = companyName;
        this.noOfSeats = noOfSeats;
        this.licenceNumber = licenceNumber;
        this.insuranceNumber = insuranceNumber;
        this.addressLat = addressLat;
        this.addressLong = addressLong;
        this.vehicleStatus = vehicleStatus;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public Double getAddressLat() {
        return addressLat;
    }

    public Double getAddressLong() {
        return addressLong;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public void setAddressLat(Double addressLat) {
        this.addressLat = addressLat;
    }

    public void setAddressLong(Double addressLong) {
        this.addressLong = addressLong;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
