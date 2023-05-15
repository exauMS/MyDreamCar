package com.helb.mydreamcar.scenario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.helb.mydreamcar.MainActivity;
import com.helb.mydreamcar.PostDetailsActivity;
import com.helb.mydreamcar.R;

import java.util.HashMap;

public class MyScenarioPageActivity extends AppCompatActivity {

    private TextView favoriteMake, bigStorage, numberOfPassenger, useOfCar, favoriteType;
    private Button btnDeleteScenario;
    private LinearLayout llMyScenario;
    private ImageView imgNoContentYet, logoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scenario_page);
        favoriteMake = findViewById(R.id.txtFavoriteMakeMyScenario);
        bigStorage = findViewById(R.id.txtBigStorageMyScenario);
        numberOfPassenger = findViewById(R.id.txtNumbOfPassengerMyScenario);
        useOfCar = findViewById(R.id.textUseOfCarMyScenario);
        favoriteType = findViewById(R.id.txtFavoriteTypeMyScenario);
        btnDeleteScenario = findViewById(R.id.btnDeleteMyScenario);
        imgNoContentYet = findViewById(R.id.imageNoContentYetMyScenario);
        llMyScenario = findViewById(R.id.myScenarioLinearLayout);
        logoHome = findViewById(R.id.logoMyScenarioPage);

        logoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDeleteScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scenarios");
                databaseReference.orderByChild("creatorEmail").equalTo(currentUser.getEmail()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                postSnapshot.getRef().removeValue();
                            }
                            Toast.makeText(MyScenarioPageActivity.this, "Scenario deleted!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        getUserScenarioInfo();

    }

    private void getUserScenarioInfo(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scenarios");
        databaseReference.orderByChild("creatorEmail").equalTo(currentUser.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);
                        HashMap<String, Object> scenarioData = (HashMap<String, Object>) data;

                        favoriteMake.setText(favoriteMake.getText()+scenarioData.get("favoriteMake").toString());
                        bigStorage.setText(bigStorage.getText()+scenarioData.get("bigStorage").toString());
                        numberOfPassenger.setText(numberOfPassenger.getText()+scenarioData.get("numberOfPassenger").toString());
                        useOfCar.setText(useOfCar.getText()+scenarioData.get("useOfCar").toString());
                        favoriteType.setText(favoriteType.getText()+scenarioData.get("favoriteType").toString());

                    }
                }else{
                    imgNoContentYet.setVisibility(View.VISIBLE);
                    llMyScenario.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}