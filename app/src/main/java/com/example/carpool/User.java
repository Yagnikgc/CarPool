package com.example.carpool;

// class to store details of a user
// To handle all the operations for a user
public class User {
    // variables to store entities of a user
    private String fName;
    private String lName;
    private String contact;
    private String email;
    private String userType;
    private String userStatus;
    private Vehicle vehicle;
    // initialize entities with default value
    public User(){
    }
    // initialize entities with user provided value
    public User(String fName, String lName, String contact, String email, String userType, String userStatus, Vehicle vehicle) {
        this.fName = fName;
        this.lName = lName;
        this.contact = contact;
        this.email = email;
        this.userStatus = userStatus;
        this.userType = userType;
        this.vehicle = vehicle;
    }

    public User(String fName, String lName, String contact, String email, String userType, String userStatus) {
        this.fName = fName;
        this.lName = lName;
        this.contact = contact;
        this.email = email;
        this.userType = userType;
        this.userStatus = userStatus;
    }

    // return user's information using methods(i.e. properties)
    public String getFname() {
        return fName;
    }
    public String getLname() {
        return lName;
    }
    public String getContact() {return contact;}
    public String getEmail(){return email;}
    public String getUserType(){return userType;}
    public String getUserStatus(){return userStatus;}
    public Vehicle getVehicle() { return vehicle; }
    // set user's information using methods(i.e. properties)
    public void setFname(String fName) {
        this.fName = fName;
    }
    public void setLname(String lName) {
        this.lName = lName;
    }
    public void setContact(String contact){this.contact = contact;}
    public void setEmail(String email){this.email=email;}
    public void setUserType(String userType){this.userType=userType;}
    public void setUserStatus(String userStatus){this.userStatus=userStatus;}
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}