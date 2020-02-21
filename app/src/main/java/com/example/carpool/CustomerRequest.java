package com.example.carpool;
// class to store details of a customer request
// To handle all the operations for a customer request
public class CustomerRequest {
    // set name of the users
    public static final String TABLE_CUSTOMER_REQUEST = "CustomerRequest";
    // set field name for the user table
    public static final String KEY_ID = "id"; // Primary Key
    public static final String KEY_CustomerID = "customerID"; // Foreign key
    public static final String KEY_SOURCE_LAT = "sourceLat";
    public static final String KEY_SOURCE_LONG = "sourceLong";
    public static final String KEY_DEST_LAT = "destinationLat";
    public static final String KEY_DEST_LONG = "destinationLong";
    public static final String KEY_RIDE_DATE = "rideDate";
    public static final String KEY_RIDE_TIME = "rideTime";
    public static final String KEY_REQUEST_DATE = "rideRequestDate";
    public static final String KEY_REQUEST_TIME = "rideRequestTime";
    public static final String KEY_RIDE_STATUS = "requestStatus";
    public static final String KEY_NO_OF_SEATS = "noOfSeats";
    // variables to store entities of a user
    private int id;
    private int customerID;
    private Double sourceLat;
    private Double sourceLong;
    private Double destinationLat;
    private Double destinationLong;
    private String rideDate;
    private String rideTime;
    private String rideRequestDate;
    private String rideRequestTime;
    private String requestStatus;
    private int noOfSeats;
    // query to create table
    public static final String CREATE_RIDE_REQUEST_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CUSTOMER_REQUEST + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CustomerID + " INTEGER," + KEY_SOURCE_LAT + " REAL,"
            + KEY_SOURCE_LONG + " REAL," + KEY_DEST_LAT + " REAL,"+ KEY_DEST_LONG + " REAL,"
            + KEY_RIDE_DATE + " TEXT," + KEY_RIDE_TIME + " TEXT,"+ KEY_REQUEST_DATE + " TEXT,"
            + KEY_REQUEST_TIME + " TEXT," + KEY_NO_OF_SEATS + " TEXT," + KEY_RIDE_STATUS + " TEXT DEFAULT 'requested', "
            + "FOREIGN KEY (" + KEY_CustomerID + ") REFERENCES User(id))";
    // initialize entities with default value
    public CustomerRequest(){
        id=0;
        customerID = 0;
        sourceLat = 0.0;
        sourceLong = 0.0;
        destinationLat = 0.0;
        destinationLong = 0.0;
        destinationLong = 0.0;
        rideDate = "";
        rideTime = "";
        rideRequestDate = "";
        rideRequestTime = "";
        requestStatus = "requested";
        noOfSeats = 0;
    }
    // initialize entities with user provided value
    public CustomerRequest(int id, int customerID, Double sourceLat, Double sourceLong, Double destinationLat, Double destinationLong, String rideDate, String rideTime, String rideRequestDate, String rideRequestTime, String requestStatus, int noOfSeats) {
        this.id = id;
        this.customerID = customerID;
        this.sourceLat = sourceLat;
        this.sourceLong = sourceLong;
        this.destinationLat = destinationLat;
        this.destinationLong = destinationLong;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.rideRequestDate = rideRequestDate;
        this.rideRequestTime = rideRequestTime;
        this.requestStatus = requestStatus;
        this.noOfSeats = noOfSeats;
    }
    /* ----- GETTER and SETTER of all properties ----- */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
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

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
    /* ----- END ----- GETTER and SETTER of all properties ----- */
}
