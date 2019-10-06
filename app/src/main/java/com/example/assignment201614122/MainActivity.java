package com.example.assignment201614122;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(R.string.home);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerid);
        navigationView =(NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_id:
                goToActivity(MainActivity.class);
                break;
            case R.id.acc_id:
                goToActivity(AccelerometerActivity.class);
                break;
            case R.id.gyro_id:
                goToActivity(GyroscopeActivity.class);
                break;
            case R.id.prox_id:
                goToActivity(ProximityActivity.class);
                break;
            case R.id.shake_id:
                goToActivity(ShakeDetectionActivity.class);
                break;
            case R.id.map_id:
                goToActivity(MapActivity.class);
                break;
            case R.id.ret_id:
                goToActivity(GyroscopeRetrieve.class);
                break;
            default:
                break;
        }
        return false;
    }

    private void goToActivity(Class<?> cls){
        Intent intent = new Intent(this,cls);
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(intent);
    }
}
