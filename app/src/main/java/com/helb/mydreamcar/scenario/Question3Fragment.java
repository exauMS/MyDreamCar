package com.helb.mydreamcar.scenario;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.helb.mydreamcar.R;


public class Question3Fragment extends Fragment {


    private NumberPicker numberPicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question3, container, false);
        numberPicker = view.findViewById(R.id.numberPickerQ3);
        numberPicker.setMaxValue(7);
        numberPicker.setMinValue(1);
        numberPicker.setValue(1);

        return view;
    }

    public String getNumberOfPassenger(){
        return Integer.toString(numberPicker.getValue());
    }
}