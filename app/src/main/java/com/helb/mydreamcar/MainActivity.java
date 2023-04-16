package com.helb.mydreamcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.helb.mydreamcar.scenario.ScenarioActivity;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private HomeFragment homeFragment = new HomeFragment();
    private RecommendationFragment recommendationFragment = new RecommendationFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_nav_bar);
        //bottomNav.setSelectedItemId(R.id.home);




        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.recommendations:

                        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                            if (RecommendationFragment.isScenarioFilled() == false)
                                startActivity(new Intent(MainActivity.this, ScenarioActivity.class));
                            else
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, recommendationFragment).commit();
                        }else{
                            startActivity(new Intent(MainActivity.this, Login.class));
                        }
                        return true;
                    case R.id.profile:
                        if(FirebaseAuth.getInstance().getCurrentUser() != null)
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        else
                            startActivity(new Intent(MainActivity.this, Login.class));
                        return true;
                }
                return false;
            }
        });



    }

    @Override
    protected void onResume() {
        bottomNav.setSelectedItemId(R.id.home);
        super.onResume();
    }
}