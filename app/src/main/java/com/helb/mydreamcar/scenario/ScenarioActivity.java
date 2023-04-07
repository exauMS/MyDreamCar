package com.helb.mydreamcar.scenario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.helb.mydreamcar.R;

public class ScenarioActivity extends AppCompatActivity {

    private Button btnValidation;
    private ImageButton btnNext;
    private int position=1;
    private TextView scenarioCounter;
    private Question1Fragment q1 = new Question1Fragment();
    private Question2Fragment q2 = new Question2Fragment();
    private Question3Fragment q3 = new Question3Fragment();
    private Question4Fragment q4 = new Question4Fragment();
    private Question5Fragment q5 = new Question5Fragment();

    //Variables for the final scenario
    private String favoriteMake;
    private String bigStorage;
    private String numberOfPassenger;
    private String useOfCar;
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
               if(position<5){
                   switch (position){
                       case 1:
                           favoriteMake=q1.getFavoriteMake();
                           break;
                       case 2:
                          bigStorage = q2.getBigStorageValue();
                           System.out.println(bigStorage);
                           break;
                       case 3:
                           numberOfPassenger = q3.getNumberOfPassenger();
                           System.out.println(numberOfPassenger);
                           break;
                       case 4:
                           useOfCar = q4.getUseOfCar();
                           System.out.println(useOfCar);
                           break;
                   }

                   position+=1;
                   fragmentSelection(position);
                   scenarioCounter.setText(position+"/5");

               }if(position==5){
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

    }
    private void fragmentSelection(int pos){
        switch (pos){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q1).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q2).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q3).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q4).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.scenario_container,q5).commit();
                break;

        }
    }

    @Override
    protected void onResume() {
        //the default position of the scenario (in q1)
        fragmentSelection(position);
        super.onResume();
    }
}