package com.helb.mydreamcar.scenario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.helb.mydreamcar.R;


public class Question4Fragment extends Fragment {


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String selectedValue="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question4, container, false);

        radioGroup = view.findViewById(R.id.radioGroupUseOfCar);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);

                if(checkedId==R.id.radioBtnCity)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnRoad)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnOffRoad)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnRural)
                    selectedValue=radioButton.getText().toString();

            }
        });

        return view;
    }

    public String getUseOfCar(){
        return selectedValue;
    }
}