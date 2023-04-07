package com.helb.mydreamcar.scenario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.helb.mydreamcar.R;


public class Question2Fragment extends Fragment {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String selectedValue="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question2, container, false);

        radioGroup = view.findViewById(R.id.radioGroupYesNo);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = view.findViewById(checkedId);

                if(checkedId==R.id.radioBtnNo)
                   selectedValue=radioButton.getText().toString();
                if(checkedId==R.id.radioBtnYES)
                    selectedValue=radioButton.getText().toString();

            }
        });





        return view;
    }

    public String getBigStorageValue(){

        return selectedValue;
    }
}