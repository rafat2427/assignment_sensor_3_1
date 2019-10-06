package com.example.assignment201614122;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AccelerometerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    private TextView xAcc,yAcc,zAcc;
    private Sensor mySensor;
    private SensorManager SM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acc_layout);

        drawerLayout = findViewById(R.id.acc_drawerid);
        navigationView = findViewById(R.id.acc_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(AccelerometerActivity.this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SM=(SensorManager)getSystemService(SENSOR_SERVICE);

        mySensor=SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);

        xAcc= findViewById(R.id.acc_t1);
        yAcc= findViewById(R.id.acc_t2);
        zAcc= findViewById(R.id.acc_t3);
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
        float x=sensorEvent.values[0];
        float y=sensorEvent.values[1];
        float z=sensorEvent.values[2];
        xAcc.setText("X: " + (int)x);
        yAcc.setText("Y: " + (int)y);
        zAcc.setText("Z: " + (int)z);

        if (y<=0){
            getWindow().getDecorView().setBackgroundColor(Color.CYAN);
        }
        else{
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    protected void onResume()
    {
        super.onResume();
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);    }
    protected void onPause()
    {
        super.onPause();
        SM.unregisterListener(this);
    }
}
