package com.example.carpool;

public class DriverRequestSummary {
    private String customerRequestID;
    private String driverRequestID;
    private String requestStatus;
    private double billingAmount;

    public DriverRequestSummary(String customerRequestID, String driverRequestID, String requestStatus, double billingAmount) {
        this.customerRequestID = customerRequestID;
        this.driverRequestID = driverRequestID;
        this.requestStatus = requestStatus;
        this.billingAmount = billingAmount;
    }

    public String getCustomerRequestID() {
        return customerRequestID;
    }

    public void setCustomerRequestID(String customerRequestID) {
        this.customerRequestID = customerRequestID;
    }

    public String getDriverRequestID() {
        return driverRequestID;
    }

    public void setDriverRequestID(String driverRequestID) {
        this.driverRequestID = driverRequestID;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public double getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(double billingAmount) {
        this.billingAmount = billingAmount;
    }
}
