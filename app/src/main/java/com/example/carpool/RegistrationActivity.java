package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    EditText txt_firstName, txt_lastName, txt_email, txt_contact, txt_password, txt_confPassword;
    RadioButton rdb_yes, rdb_no;
    Button btn_signUp;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);
        final String fName = txt_firstName.getText().toString().trim();
        final String lName = txt_lastName.getText().toString().trim();
        final String contact = txt_contact.getText().toString().trim();
        final String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String confPassword = txt_confPassword.getText().toString().trim();
        final String userType;
        if(rdb_yes.isChecked()==true)
            userType = "Driver";
        else if(rdb_no.isChecked()==true)
            userType = "Customer";
        else
            userType = "";
        boolean hasError = false;
        if(TextUtils.isEmpty(fName)){
            txt_firstName.setError("Please enter First Name!");
            hasError = true;
        }
        if(TextUtils.isEmpty(lName)){
            txt_lastName.setError("Please enter Last Name!");
            hasError = true;
        }
        if(TextUtils.isEmpty(contact)){
            txt_contact.setError("Please enter Mobile Number!");
            hasError = true;
        }
        if(TextUtils.isEmpty(email)){
            txt_email.setError("Please enter E-mail Address!");
            hasError = true;
        }
        if(TextUtils.isEmpty(password)){
            txt_password.setError("Please enter Password!");
            hasError = true;
        }
        if(TextUtils.isEmpty(confPassword)){
            txt_confPassword.setError("Please enter Confirm Password!");
            hasError = true;
        }
        if(!password.equals(confPassword)){
            txt_confPassword.setError("Password doesn't match!");
            hasError = true;
        }
        if(TextUtils.isDigitsOnly(contact) && contact.length()!=10){
            txt_contact.setError("Invalid Mobile Number!");
            hasError = true;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txt_email.setError("Invalid E-mail Address!");
            hasError = true;
        }
        if(!hasError) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userID = task.getResult().getUser().getUid();
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                User user = null;
                                if(userType.equals("Customer")) user = new User(fName, lName, contact, email, userType, "Registered");
                                else if(userType.equals("Driver")) user = new User(fName, lName, contact, email, userType, "Registered",null);
                                reference.child(userID).setValue(user);
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
        progressBar.setVisibility(View.GONE);
    }

    private void initializeUI() {
        txt_firstName = findViewById(R.id.txt_firstName_reg);
        txt_lastName = findViewById(R.id.txt_lastName_reg);
        txt_email = findViewById(R.id.txt_email_reg);
        txt_contact = findViewById(R.id.txt_contact_reg);
        txt_password = findViewById(R.id.txt_password_reg);
        txt_confPassword = findViewById(R.id.txt_confPassword_reg);
        rdb_yes = findViewById(R.id.rdb_haveCar_yes_reg);
        rdb_no = findViewById(R.id.rdb_haveCar_no_reg);
        btn_signUp = findViewById(R.id.btn_signUp_reg);
        progressBar = findViewById(R.id.progressBar);
        reference = FirebaseDatabase.getInstance().getReference().child("User");
    }
}