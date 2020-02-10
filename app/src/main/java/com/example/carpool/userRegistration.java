package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class userRegistration extends AppCompatActivity {

    EditText txt_firstName, txt_lastName, txt_email, txt_contact, txt_password, txt_confPassword;
    RadioButton rdb_yes, rdb_no;
    Button btn_signUp;
    DatabaseHandler databaseHandler;
    int errorCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        InitializeControls();
    }

    private void InitializeControls() {
        txt_firstName = findViewById(R.id.txt_firstName_reg);
        txt_lastName = findViewById(R.id.txt_lastName_reg);
        txt_email = findViewById(R.id.txt_email_reg);
        txt_contact = findViewById(R.id.txt_contact_reg);
        txt_password = findViewById(R.id.txt_password_reg);
        txt_confPassword = findViewById(R.id.txt_confPassword_reg);
        rdb_yes = findViewById(R.id.rdb_haveCar_yes_reg);
        rdb_no = findViewById(R.id.rdb_haveCar_no_reg);
        btn_signUp = findViewById(R.id.btn_signUp_reg);
        databaseHandler = new DatabaseHandler(getApplicationContext());
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonOnClickListener();
            }
        });
    }

    private void ButtonOnClickListener() {
        String userStatus, userType;
        // While registration default status of a user is Active
        userStatus = "Active";
        if(rdb_yes.isChecked()==true)
            userType = "Driver";
        else if(rdb_no.isChecked()==true)
            userType = "Customer";
        else
            userType = "";
        ValidateDataValues();
        if(errorCounter==0) {
            databaseHandler.insertUser(txt_firstName.getText().toString(), txt_lastName.getText().toString(), txt_contact.getText().toString(), txt_email.getText().toString(), md5(txt_password.getText().toString()), userType, userStatus);
            Toast.makeText(getApplicationContext(),"Thank you for registration, "+txt_firstName.getText().toString(),Toast.LENGTH_LONG).show();
            Intent intent_logIn = new Intent(getApplicationContext(), activity_login.class);
            startActivity(intent_logIn);
        }
    }

    private void ValidateDataValues(){
        String fName = txt_firstName.getText().toString().trim();
        String lName = txt_lastName.getText().toString().trim();
        String contact = txt_contact.getText().toString().trim();
        String eMail = txt_email.getText().toString().trim();
        String passowrd = txt_password.getText().toString().trim();
        String confPassword = txt_confPassword.getText().toString().trim();
        if(fName.equals("") || fName.isEmpty()){
            txt_firstName.setError("Please enter First Name!");
            errorCounter++;
        }
        if(lName.equals("") || lName.isEmpty()){
            txt_lastName.setError("Please enter Last Name!");
            errorCounter++;
        }
        if(contact.equals("") || contact.isEmpty()){
            txt_contact.setError("Please enter Mobile Number!");
            errorCounter++;
        }
        if(eMail.equals("") || eMail.isEmpty()){
            txt_email.setError("Please enter E-mail Address!");
            errorCounter++;
        }
        if(passowrd.equals("") || passowrd.isEmpty()){
            txt_password.setError("Please enter Password!");
            errorCounter++;
        }
        if(confPassword.equals("") || confPassword.isEmpty()){
            txt_confPassword.setError("Please enter Confirm Password!");
            errorCounter++;
        }
        if(!passowrd.equals(confPassword)){
            txt_confPassword.setError("Password doesn't match!");
            errorCounter++;
        }
    }
    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
