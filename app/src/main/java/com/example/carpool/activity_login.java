package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activity_login extends AppCompatActivity {
    TextView lbl_registerNow;
    DatabaseHandler db_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeControls();
        // Create on click event to switch from LogIn to Registration
        lbl_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getApplicationContext(), userRegistration.class);
                startActivity(intent_register);
            }
        });
    }

    private void InitializeControls() {
        db_handler = new DatabaseHandler(getApplicationContext());
        lbl_registerNow = findViewById(R.id.lbl_registerNow);
    }
}
