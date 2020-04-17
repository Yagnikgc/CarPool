package com.example.carpool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_logIn extends AppCompatActivity {
    private EditText emailTV, passwordTV;
    private Button loginBtn;
    private ProgressBar progressBar;
    private TextView lbl_registerNow,forgetPassword;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            CheckUserTypeAndShowUserDashboard();
        }
        initializeUI();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        // Create on click event to switch from LogIn to Registration
        lbl_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getApplicationContext(), activity_registration.class);
                startActivity(intent_register);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent int_forgot=new Intent(getApplicationContext(),ForgetPassword.class);
                    startActivity(int_forgot);
            }
        });
    }

    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();
                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        // To check User type
                        CheckUserTypeAndShowUserDashboard();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.txt_username_logIn);
        passwordTV = findViewById(R.id.txt_password_logIn);

        loginBtn = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);

        lbl_registerNow = findViewById(R.id.lbl_registerNow);
        forgetPassword=findViewById(R.id.txt_forget_password_login);
        preferences = getSharedPreferences("CarPool", Context.MODE_PRIVATE);
    }

    private void CheckUserTypeAndShowUserDashboard() {
        reference = FirebaseDatabase.getInstance().getReference().child("User").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userType = dataSnapshot.child("userType").getValue().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("UserType",userType);
                editor.apply();
                if(userType.equals("Driver")){
                    if(!dataSnapshot.hasChild("vehicle")) {
                        Intent i = new Intent(activity_logIn.this, activity_registerVehicle.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(activity_logIn.this, activity_driverDashboard.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }else if(userType.equals("Customer")){
                    Intent i = new Intent(activity_logIn.this, activity_customerDashboard.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),"An error occured!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}