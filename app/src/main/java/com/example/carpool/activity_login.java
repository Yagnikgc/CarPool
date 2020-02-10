package com.example.carpool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activity_login extends AppCompatActivity {
    TextView lbl_registerNow;
    Database db_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeControls();
    }

    private void InitializeControls() {
        db_handler = new Database(getApplicationContext());
        lbl_registerNow = findViewById(R.id.lbl_registerNow);
        lbl_registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getApplicationContext(), userRegistration.class);
                startActivity(intent_register);
            }
        });
    }
}
