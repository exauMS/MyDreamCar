package com.helb.mydreamcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;

public class PostDetailsActivity extends AppCompatActivity implements Serializable {

    private Intent intent;
    private Bundle bundle;
    private HashMap<String, String> postInfoMap;

    private ImageButton btnAddFavorite;
    private ImageView carImage;
    private TextView userNameAndDate, make, model, year, type;

    private int colorCount=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        intent = this.getIntent();
        bundle = intent.getExtras();
        postInfoMap = (HashMap<String, String>)bundle.getSerializable("postInfo");

        btnAddFavorite = findViewById(R.id.imageBtnAddFavorite);
        carImage = findViewById(R.id.imageCarPostDetails);
        userNameAndDate = findViewById(R.id.userAndDatePostDetails);
        make = findViewById(R.id.makeValuePostDetails);
        model = findViewById(R.id.modelValuePostDetails);
        year = findViewById(R.id.yearValuePostDetails);
        type = findViewById(R.id.typeValuePostDetails);

        userNameAndDate.setText("Posted by "+postInfoMap.get("creator")+" on "+postInfoMap.get("date"));
        make.setText(postInfoMap.get("make"));
        model.setText(postInfoMap.get("model"));
        year.setText(postInfoMap.get("year"));
        type.setText(postInfoMap.get("type"));
        HomeFragment.getCarImageFromStorage(postInfoMap.get("url"), carImage);

        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(colorCount==1){
                    btnAddFavorite.setBackgroundColor(getResources().getColor(R.color.yellow));
                    colorCount=0;
                }else{
                    btnAddFavorite.setBackgroundColor(getResources().getColor(R.color.white));
                    colorCount+=1;
                }
            }
        });

        System.out.println(postInfoMap.get("make"));

    }
}