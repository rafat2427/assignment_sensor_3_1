package com.example.assignment201614122;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GyroscopeRetrieve  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference myRef1;
    GridView grid;
    ArrayAdapter<String> adapter;
    List<String> slist;
    GyroscopeData ret_data;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    TextView gt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ret_gyro_layout);

        drawerLayout = (DrawerLayout) findViewById(R.id.ret_gyro_drawerid);
        navigationView =(NavigationView) findViewById(R.id.ret_gyro_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(GyroscopeRetrieve.this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        gt1= findViewById(R.id.ret_t1);
        Calendar c=Calendar.getInstance();
        final String dd= DateFormat.getDateTimeInstance().format(c.getTime());
        gt1.setText(dd); slist = new ArrayList<>();

        ret_data=new GyroscopeData();
        grid = findViewById(R.id.grid);
        //Initializing the Headings of the columns
        slist.add("X-axis");
        slist.add("Y-axis");
        slist.add("Z-axis");

        //The gridView is using the sample_layout.xml for Build up
        adapter = new ArrayAdapter<String>(this, R.layout.sample_layout, R.id.t1, slist);

        //Getting the value from database
        myRef1= FirebaseDatabase.getInstance().getReference("Gyroscope Data");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    GyroscopeData res =dataSnapshot1.getValue(GyroscopeData.class);
                    //The values are added to the list
                    slist.add(Float.toString(res.x_val));
                    slist.add(Float.toString(res.y_val));
                    slist.add(Float.toString(res.z_val));
                }
                //the values are added to the adapter and subsequently to the List
                grid.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
