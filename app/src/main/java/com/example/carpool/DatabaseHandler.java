package com.example.carpool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Set Database version
    private static final int DATABASE_VERSION = 1;
    // Set Database name
    private static final String DATABASE_NAME = "DB_CarPool";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CustomerRequest.CREATE_RIDE_REQUEST_TABLE);
        db.execSQL(User.CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /* ------ START ALL OPERATION FOR USER TABLE ----- */
    // Method to perform insert operation
    // OnClick of insert button, it will store all data into database
    public long insertUser(String fname, String lname, String contact, String email, String password, String userType, String userStatus) {
        // get writable database to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // id will be inserted automatically.
        // add all fields we want to insert in table
        values.put(User.KEY_FNAME, fname);
        values.put(User.KEY_LNAME, lname);
        values.put(User.KEY_CONTACT, contact);
        values.put(User.KEY_MAIL, email);
        values.put(User.KEY_PASSWORD, password);
        values.put(User.KEY_USER_TYPE, userType);
        values.put(User.KEY_USER_STATUS, userStatus);

        // insert row
        long id = db.insert(User.TABLE_USER, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    // Method to perform Select operation
    // OnClick of ViewData, it will fetch all data from database and will store in List of User
    public List<User> getAllUsers() {
        // List to store User data
        List<User> Users = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + User.TABLE_USER + " ORDER BY " +
                User.KEY_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        // Execute select command and store all data into Cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                // Fetch data from Cursor and store into User object
                user.setId(cursor.getInt(cursor.getColumnIndex(User.KEY_ID)));
                user.setFname(cursor.getString(cursor.getColumnIndex(User.KEY_FNAME)));
                user.setLname(cursor.getString(cursor.getColumnIndex(User.KEY_LNAME)));
                user.setContact(cursor.getString(cursor.getColumnIndex(User.KEY_CONTACT)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.KEY_PASSWORD)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.KEY_MAIL)));
                user.setUserStatus(cursor.getString(cursor.getColumnIndex(User.KEY_USER_TYPE)));
                user.setUserType(cursor.getString(cursor.getColumnIndex(User.KEY_USER_STATUS)));
                // Add User object to User List
                Users.add(user);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return Users list
        return Users;
    }
    // Method to perform Delete operation
    // OnClick of DeleteRecord, it will check that data exists or not and than will delete that record
    public String deleteUser(int id) {
        // Query to check data exists or not
        String countQuery = "SELECT  * FROM " + User.TABLE_USER + " WHERE id = "+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // count how many data are there with provided ID
        int count = cursor.getCount();
        cursor.close();
        // If data found, than perform delete
        if(count>0) {
            db = this.getWritableDatabase();
            // Delete record from database with given ID
            db.delete(User.TABLE_USER, User.KEY_ID + " = ?",
                    new String[]{String.valueOf(id)});
            db.close();
            // Return success message
            return "Record deleted successfully.";
        }else{
            // return that data not found with given ID
            return "No data found";
        }
    }
    // Method to perform Update operation
    // OnClick of UpdateRecord, it will check that data exists or not and than it will update that record
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // variable to count how many updates are given
        int countUpdates = 0;
        ContentValues values = new ContentValues();
        // If value is empty, it means no need to update that field
        // Check empty fields before updates and increase counter that says update is given
        if(!user.getFname().equals("") || user.getFname().equals(null)) {
            values.put(User.KEY_FNAME, user.getFname());
            countUpdates++;
        }
        if(!user.getLname().equals("") || user.getFname().equals(null)){
            values.put(User.KEY_LNAME, user.getLname());
            countUpdates++;
        }
        if(!user.getPassword().equals("") || user.getPassword().equals(null)){
            values.put(User.KEY_PASSWORD, user.getLname());
            countUpdates++;
        }
        if(!user.getContact().equals("") || user.getContact().equals(null)){
            values.put(user.KEY_CONTACT, user.getLname());
            countUpdates++;
        }
        if(!user.getEmail().equals("") || user.getEmail().equals(null)){
            values.put(user.KEY_MAIL, user.getLname());
            countUpdates++;
        }
        if(!user.getUserType().equals("") || user.getUserType().equals(null)){
            values.put(User.KEY_USER_TYPE, user.getLname());
            countUpdates++;
        }
        if(!user.getUserStatus().equals("") || user.getUserStatus().equals(null)){
            values.put(User.KEY_USER_STATUS, user.getLname());
            countUpdates++;
        }
        // Return value that says no updates given
        if(countUpdates==0)
            return -1;
        // updating row
        return db.update(User.TABLE_USER, values, User.KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }
    /* ------ END ALL OPERATION FOR USER TABLE ----- */

    /* ------ START ALL OPERATION FOR USER TABLE ----- */
    // Method to perform insert operation
    // OnClick of insert button, it will store all data into database
    public long insertCustomerRequest(int customerID, Double sourceLat, Double sourceLong, Double destinationLat, Double destinationLong,
                                      String rideDate, String rideTime, String rideRequestDate, String rideRequestTime, int noOfSeats) {
        // get writable database to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // id will be inserted automatically.
        // add all fields we want to insert in table
        values.put(CustomerRequest.KEY_CustomerID, customerID);
        values.put(CustomerRequest.KEY_SOURCE_LAT, sourceLat);
        values.put(CustomerRequest.KEY_SOURCE_LONG, sourceLong);
        values.put(CustomerRequest.KEY_DEST_LAT, destinationLat);
        values.put(CustomerRequest.KEY_DEST_LONG, destinationLong);
        values.put(CustomerRequest.KEY_RIDE_DATE, rideDate);
        values.put(CustomerRequest.KEY_RIDE_TIME, rideTime);
        values.put(CustomerRequest.KEY_REQUEST_DATE, rideRequestDate);
        values.put(CustomerRequest.KEY_REQUEST_TIME, rideRequestTime);
        values.put(CustomerRequest.KEY_NO_OF_SEATS, noOfSeats);
        // insert row
        long id = db.insert(CustomerRequest.TABLE_CUSTOMER_REQUEST, null, values);
        // close db connection
        db.close();
        // return newly inserted row id
        return id;
    }
    /* ------ END ALL OPERATION FOR USER TABLE ----- */
}