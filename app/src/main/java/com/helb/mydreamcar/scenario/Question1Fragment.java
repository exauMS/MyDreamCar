package com.helb.mydreamcar.scenario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.helb.mydreamcar.Apis.CarDataAPI;
import com.helb.mydreamcar.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Question1Fragment extends Fragment {

    private List<String> makeList;
    private MaterialAutoCompleteTextView autoCompleteTextView;
    private String myResponse="", url="https://car-data.p.rapidapi.com/cars/makes";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question1, container, false);

        autoCompleteTextView = view.findViewById(R.id.makeSearchAutoCompleteTextView);

        CarDataAPI.setRequest(url);
        //waiting for the api to set the result and then get it
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        myResponse=CarDataAPI.getRequestResult();

        System.out.println(myResponse);

        ObjectMapper mapper = new ObjectMapper();
        makeList = new ArrayList<>();
        try {
            makeList = Arrays.asList(mapper.readValue(myResponse, String[].class));
        }catch (IOException e){
            e.printStackTrace();
        }

        autoCompleteTextView.setAdapter(new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,makeList));


        return view;
    }

    public String getFavoriteMake(){
        return autoCompleteTextView.getText().toString();
    }
}