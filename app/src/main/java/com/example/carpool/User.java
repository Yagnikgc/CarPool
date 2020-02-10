package com.example.carpool;

// class to store details of a user
// To handle all the operations for a user
public class User {
    // set name of the users
    public static final String TABLE_USER = "User";
    // set field name for the user table
    public static final String KEY_ID = "id";
    public static final String KEY_FNAME = "first_name";
    public static final String KEY_LNAME = "last_name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_MAIL = "email";
    public static final String KEY_USER_TYPE = "user_type";
    public static final String KEY_USER_STATUS = "user_status";
    // variables to store entities of a user
    private int id;
    private String fname;
    private String lname;
    private String password;
    private String contact;
    private String email;
    private String userType;
    private String userStatus;
    // query to create table
    public static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FNAME + " TEXT," + KEY_LNAME + " TEXT,"
            + KEY_PASSWORD + " TEXT," + KEY_CONTACT + " TEXT NOT NULL UNIQUE,"+ KEY_MAIL + " TEXT NOT NULL UNIQUE,"
            + KEY_USER_TYPE + " TEXT," + KEY_USER_STATUS + " TEXT DEFAULT 'registered'" + ")";
    // initialize entities with default value
    public User(){
        id=0;
        fname = "";
        lname = "";
        password = "";
        contact = "";
        email = "";
        userType = "";
        userStatus = "";
    }
    // initialize entities with user provided value
    public User(String fname, String lname, String password, String contact, String email, String userType, String userStatus) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.contact = contact;
        this.email = email;
        this.userStatus = userStatus;
        this.userType = userType;
    }
    // return user's information using methods(i.e. properties)
    public int getId() {
        return id;
    }
    public String getFname() {
        return fname;
    }
    public String getLname() {
        return lname;
    }
    public String getPassword(){return password;}
    public String getContact() {return contact;}
    public String getEmail(){return email;}
    public String getUserType(){return userType;}
    public String getUserStatus(){return userStatus;}
    // set user's information using methods(i.e. properties)
    public void setId(int id) {
        this.id = id;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setPassword(String password){this.password = password;}
    public void setContact(String contact){this.contact = contact;}
    public void setEmail(String email){this.email=email;}
    public void setUserType(String userType){this.userType=userType;}
    public void setUserStatus(String userStatus){this.userStatus=userStatus;}
}