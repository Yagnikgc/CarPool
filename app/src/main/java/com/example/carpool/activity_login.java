package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class activity_login extends AppCompatActivity {
    TextView lbl_registerNow,forgetPassword;
    DatabaseHandler db_handler;
    EditText edt_username,edt_password;
    Button login;
    List<User> getUser;
    Boolean isRegistered= false;
    Boolean loginFound=false;
    // Object to store currently logged in user
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeControls();
        //for forget Password
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser=db_handler.getAllUsers();
                for (User user : getUser)
                {
                    if(user.getEmail().equalsIgnoreCase(edt_username.getText().toString()))
                    {
                        isRegistered = true;
                        break;
                    }
                }
                if(isRegistered)
                {
                    Toast.makeText(getApplicationContext(),"Your password sent on your registered email id",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"User not found",Toast.LENGTH_LONG).show();
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser=db_handler.getAllUsers();
                for (User user : getUser)
                {
                    if(user.getEmail().equalsIgnoreCase(edt_username.getText().toString()) && user.getPassword().equals(md5(edt_password.getText().toString())))
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("userID", user.getId());
                        editor.putString("userName", new StringBuilder().append(user.getFname()).append(" ").append(user.getLname()).toString());
                        editor.putString("userType", user.getUserStatus());
                        editor.commit();
                        loginFound =true;
                        break;
                    }
                }
                if(loginFound)
                {
                    Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();
                    String userType = sharedPreferences.getString("userType", null);
                    if(userType.equalsIgnoreCase("Customer")) {
                        Intent intent_userHome = new Intent(getApplicationContext(), userHome.class);
                        startActivity(intent_userHome);
                    }
                    else if(userType.equalsIgnoreCase("Driver")){
                        Intent intent_registerVehicle = new Intent(getApplicationContext(), registerVehicelDriver.class);
                        startActivity(intent_registerVehicle);
                    }
                    else{
                        Log.i("LoginActivity",userType + "\nError while fetching user type");
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_LONG).show();
                }
            }
        });

        // Create on click event to switch from LogIn to Registration
        lbl_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getApplicationContext(), userRegistration.class);
                startActivity(intent_register);
            }
        });
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

    // all components are initialize here
    private void InitializeControls() {
        db_handler = new DatabaseHandler(getApplicationContext());
        lbl_registerNow = findViewById(R.id.lbl_registerNow);
        edt_username=findViewById(R.id.txt_username_logIn);
        edt_password=findViewById(R.id.txt_password_logIn);
        forgetPassword=findViewById(R.id.txt_forget_password_login);
        login=findViewById(R.id.btn_login);
        sharedPreferences = getSharedPreferences("CarPool", Context.MODE_PRIVATE);
    }
}
