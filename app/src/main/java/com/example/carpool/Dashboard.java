package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String userType;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggle;
    NavigationView nav_View;
    FragmentManager fragmentManager;
    FrameLayout viewLayout;
    SearchRide searchRide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        InitializeUI();
    }

    private void InitializeUI() {
        mDrawer = (DrawerLayout)findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this,mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_View = (NavigationView)findViewById(R.id.nav_View);
        nav_View.setNavigationItemSelectedListener(this);
        viewLayout = (FrameLayout)findViewById(R.id.viewLayout);
        fragmentManager = getSupportFragmentManager();
        searchRide = new SearchRide();
        fragmentManager.beginTransaction().replace(R.id.viewLayout, searchRide).commit();
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
        Fragment fragment = searchRide;
        switch (menuItem.getItemId()) {
            case R.id.action_findRide:
                fragment = searchRide;
                setTitle(menuItem.getTitle());
                break;
            case R.id.action_yourTrips:
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
                fragment = searchRide;
                setTitle(menuItem.getTitle());
                break;

        }
        fragmentManager.beginTransaction().replace(R.id.viewLayout, fragment).commit();
        mDrawer.closeDrawers();
        return true;
    }
}
