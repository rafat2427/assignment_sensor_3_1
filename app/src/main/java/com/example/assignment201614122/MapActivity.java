package com.example.assignment201614122;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MapActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TextWatcher {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    EditText src_et,dst_et;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        drawerLayout = findViewById(R.id.map_drawerid);
        navigationView = findViewById(R.id.map_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(MapActivity.this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        src_et=findViewById(R.id.src_et);
        dst_et=findViewById(R.id.dst_et);
        webView=findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());

        String mapContent=getMapContent("MIST","Signal Base Workshop");
        webView.loadUrl(mapContent);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        src_et.addTextChangedListener(this);
        dst_et.addTextChangedListener(this);
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


    public String getMapContent(String src,String des)
    {
        String mapContent="https://directionsdebug.firebaseapp.com/embed.html?";
        String source="origin=",destination="&destination=";

        for(int i=0;i<src.length();i++)
        {
            if(src.charAt(i) != ' ')
            {
                source=source+src.charAt(i);
            }
            else
            {
                source=source+"%20";
            }
        }

        for(int i=0;i<des.length();i++)
        {
            if(des.charAt(i) != ' ')
            {
                destination=destination+des.charAt(i);
            }
            else
            {
                destination=destination+"%20";
            }
        }

        destination=destination+"&mode=driving";

        mapContent=mapContent+source+destination;

        return mapContent;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        String mapContent=getMapContent(src_et.getText().toString(),dst_et.getText().toString());
        webView.loadUrl(mapContent);

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String mapContent=getMapContent(src_et.getText().toString(),dst_et.getText().toString());
        webView.loadUrl(mapContent);
    }

    @Override
    public void afterTextChanged(Editable s) {
        String mapContent=getMapContent(src_et.getText().toString(),dst_et.getText().toString());
        webView.loadUrl(mapContent);
    }
}
