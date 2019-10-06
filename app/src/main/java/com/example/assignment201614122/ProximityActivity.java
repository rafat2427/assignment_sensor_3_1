package com.example.assignment201614122;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ProximityActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    private Sensor mySensor;
    private SensorManager SM;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prox_layout);

        drawerLayout = (DrawerLayout) findViewById(R.id.prox_drawerid);
        navigationView =(NavigationView) findViewById(R.id.prox_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(ProximityActivity.this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SM=(SensorManager)getSystemService(SENSOR_SERVICE);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mySensor=SM.getDefaultSensor(Sensor.TYPE_PROXIMITY);

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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.values[0]<mySensor.getMaximumRange()) {
            vibrator.vibrate(1000);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onResume()
    {
        super.onResume();
        SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause()
    {
        super.onPause();
        SM.unregisterListener(this);
    }
}
