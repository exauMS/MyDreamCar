package com.helb.mydreamcar.scenario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.helb.mydreamcar.Apis.CarDataAPI;
import com.helb.mydreamcar.MainActivity;
import com.helb.mydreamcar.R;
import com.helb.mydreamcar.RecommendationFragment;
import com.helb.mydreamcar.model.Scenario;

import java.util.List;

public class ScenarioActivity extends AppCompatActivity {

    private final int Q1_POS=1;
    private final int Q2_POS=2;
    private final int Q3_POS=3;
    private final int Q4_POS=4;
    private final int Q5_POS=5;

    private Button btnValidation;
    private Button btnNext;
    private int position=Q1_POS;
    private TextView scenarioCounter;
    private Question1Fragment q1 = new Question1Fragment();
    private Question2Fragment q2 = new Question2Fragment();
    private Question3Fragment q3 = new Question3Fragment();
    private Question4Fragment q4 = new Question4Fragment();
    private Question5Fragment q5 = new Question5Fragment();

    String accurateUrl, abstractUrl, accurateResult,abstractResult;

    //Variables for the final scenario
    private String favoriteMake;
    private String bigStorage;
    private String numberOfPassenger;
    private String useOfCar;
    private String favoriteType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenario);
        btnValidation = findViewById(R.id.btnValidationScenario);
        btnNext = findViewById(R.id.btnNextQuestion);
        scenarioCounter = findViewById(R.id.scenarioCounterText);




        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(position<Q5_POS){
                   switch (position){
                       case Q1_POS:
                           favoriteMake=q1.getFavoriteMake();
                           break;
                       case Q2_POS:
                          bigStorage = q2.getBigStorageValue();
                           break;
                       case Q3_POS:
                           numberOfPassenger = q3.getNumberOfPassenger();
                           break;
                       case Q4_POS:
                           useOfCar = q4.getUseOfCar();
                           break;
                   }

                   position+=1;
                   fragmentSelection(position);
                   scenarioCounter.setText(position+"/5");

               }if(position==Q5_POS){
                    fragmentSelection(position);
                    scenarioCounter.setText(position+"/5");
                    btnValidation.setEnabled(true);
                    btnValidation.setVisibility(View.VISIBLE);
                    btnNext.setEnabled(false);
                    btnNext.setVisibility(View.GONE);
               }else{

                }

            }
        });

        btnValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnValidation.setEnabled(false);
                favoriteType=q5.getFavoriteType();
                urlPreparation();
                try {
                    Thread.sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                saveScenario();
            }
        });

    }
    private void fragmentSelection(int pos){
        switch (pos){
            case Q1_POS:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q1).commit();
                break;
            case Q2_POS:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q2).commit();
                break;
            case Q3_POS:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q3).commit();
                break;
            case Q4_POS:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q4).commit();
                break;
            case Q5_POS:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q5).commit();
                break;

        }
    }

    private void urlPreparation(){
        accurateUrl = "https://car-data.p.rapidapi.com/cars?limit=5&page=0&make="+favoriteMake+"&type="+favoriteType+"&year=20";

        if(Integer.parseInt(numberOfPassenger)>5){
            abstractUrl= "https://car-data.p.rapidapi.com/cars?limit=5&page=0&year=20&type=van";
        }
        else if(Integer.parseInt(numberOfPassenger)==5){
            abstractUrl= "https://car-data.p.rapidapi.com/cars?limit=5&page=0&year=20&type=sedan";
        }
        else{
            abstractUrl= "https://car-data.p.rapidapi.com/cars?limit=5&page=0&year=20&type=hatchback";
        }

        Toast.makeText(this, "Waiting for your recommendations...", Toast.LENGTH_LONG).show();
        CarDataAPI.setRequest(accurateUrl);
        //waiting for the api to set the result and then get it
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        accurateResult=CarDataAPI.getRequestResult();


        CarDataAPI.setRequest(abstractUrl);
        //waiting for the api to set the result and then get it
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }

        abstractResult=CarDataAPI.getRequestResult();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

    private void saveScenario(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Scenarios").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.setValue(new Scenario(favoriteMake,bigStorage,numberOfPassenger,useOfCar,favoriteType, FirebaseAuth.getInstance().getCurrentUser().getEmail(),abstractResult, accurateResult));
    }

    @Override
    protected void onResume() {
        //the default position of the scenario (in q1)
        fragmentSelection(position);
        super.onResume();
    }
}