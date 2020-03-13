package com.example.carpool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard_Driver extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggle;
    NavigationView nav_View;
    FragmentManager fragmentManager;
    FrameLayout viewLayout;
    PostRide postRide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_driver);
        InitializeUI();
    }
    private void InitializeUI() {
        mDrawer = findViewById(R.id.drawerDriver);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_View = findViewById(R.id.nav_View_Driver);
        nav_View.setNavigationItemSelectedListener(this);
        viewLayout = findViewById(R.id.viewLayout_Driver);
        fragmentManager = getSupportFragmentManager();
        postRide = new PostRide();
        fragmentManager.beginTransaction().replace(R.id.viewLayout_Driver, postRide).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = postRide;
        switch (menuItem.getItemId()) {
            case R.id.action_postRide:
                fragment = postRide;
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_yourRides:
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_signOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                // to make sure user cant go back
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.action_rateUs:
                setTitle(menuItem.getTitle());
                break;
            default:
                fragment = postRide;
                setTitle(menuItem.getTitle());
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.viewLayout_Driver, fragment).commit();
        mDrawer.closeDrawers();
        return true;
    }
}
