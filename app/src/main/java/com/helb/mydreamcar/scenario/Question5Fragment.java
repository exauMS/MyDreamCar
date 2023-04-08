package com.helb.mydreamcar.scenario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.helb.mydreamcar.R;


public class Question5Fragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String selectedValue="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question5, container, false);

        radioGroup = view.findViewById(R.id.radioGroupCarType);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);

                if(checkedId==R.id.radioBtnSUV)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnSedan)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnConvertible)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnCoupe)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnWagon)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnHatchback)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnVan)
                    selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnPickup)
                    selectedValue=radioButton.getText().toString();

            }
        });

        return view;
    }

    public String getFavoriteType(){
        return selectedValue;
    }
}