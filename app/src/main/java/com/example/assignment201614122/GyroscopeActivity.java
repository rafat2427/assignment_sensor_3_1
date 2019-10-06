package com.example.assignment201614122;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GyroscopeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private TextView xGyro,yGyro,zGyro;
    private Sensor mySensor;
    private SensorManager SM;

    GyroscopeData g_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyro_layout);

        drawerLayout = findViewById(R.id.gyro_drawerid);
        navigationView = findViewById(R.id.gyro_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(GyroscopeActivity.this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SM=(SensorManager)getSystemService(SENSOR_SERVICE);

        mySensor=SM.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);


        xGyro= findViewById(R.id.gyro_t1);
        yGyro= findViewById(R.id.gyro_t2);
        zGyro= findViewById(R.id.gyro_t3);

        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Gyroscope Data");
        g_data=new GyroscopeData();
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
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];


        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orient= display.getOrientation();

        xGyro.setText("X: " + (int) x);
        yGyro.setText("Y: " + (int) y);
        zGyro.setText("Z: " + (int) z);


        if (orient == 1 || orient == 3 ) {
            Toast.makeText(this, "Gyroscope data inserted", Toast.LENGTH_SHORT).show();
            InsertData();
        }
    }

    private void InsertData() {
        String key=myRef.push().getKey();
        myRef.child(key).setValue(g_data);
        goToActivity(MainActivity.class);
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
