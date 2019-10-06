package com.example.assignment201614122;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.squareup.seismic.ShakeDetector;

public class ShakeDetectionActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ShakeDetector.Listener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    TextView number;
    String emg;

    SensorManager SM;
    ShakeDetector SD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake_layout);

        drawerLayout = (DrawerLayout) findViewById(R.id.shake_drawerid);
        navigationView = (NavigationView) findViewById(R.id.shake_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(ShakeDetectionActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        number=findViewById(R.id.shake_t1);
        emg= number.getText().toString();


        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Prompting Alert Dialog to ask for leaving from or staying in the page
                AlertDialog.Builder alert= new AlertDialog.Builder(ShakeDetectionActivity.this);

                alert.setTitle("Emergency Number");
                alert.setMessage("Do you want to Change the Number?");
                final EditText input= new EditText(ShakeDetectionActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                alert.setView(input);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newNo= input.getText().toString();

                        if(newNo.isEmpty()){
                            Toast.makeText(ShakeDetectionActivity.this, "No Number Entered", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        number.setText(newNo);
                        emg=newNo;
                    }
                });

                alert.show();
            }
        });

        SM=(SensorManager)getSystemService(SENSOR_SERVICE);
        SD=new ShakeDetector(this);
        SD.start(SM);
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
        SD.stop();
        startActivity(intent);
    }

    @Override
    public void hearShake() {

        Intent intentCall= new Intent(Intent.ACTION_CALL);
        intentCall.setData(Uri.parse("tel:"+emg));

        //Checks if Phone give permission to make a phone call
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE )!= PackageManager.PERMISSION_GRANTED) {
            //if permission is not granted, requests for permission
            ActivityCompat.requestPermissions(ShakeDetectionActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            //After granting permission checks again and makes the call
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"Calling the Emergency Number", Toast.LENGTH_LONG).show();
                startActivity(intentCall);
            }
        }
        else{
            //if permission is granted, then makes the call
            Toast.makeText(getApplicationContext(),"Calling the Emergency Number", Toast.LENGTH_LONG).show();
            startActivity(intentCall);
        }
    }
}
