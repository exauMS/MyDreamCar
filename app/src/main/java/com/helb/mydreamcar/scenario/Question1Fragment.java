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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question1, container, false);

        autoCompleteTextView = view.findViewById(R.id.makeSearchAutoCompleteTextView);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://car-data.p.rapidapi.com/cars/makes")
                .get()
                .addHeader("X-RapidAPI-Key", "355bb8c4c6mshd8c21018d5bb357p14dfa5jsnc0480b6d615f")
                .addHeader("X-RapidAPI-Host", "car-data.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ObjectMapper mapper = new ObjectMapper();
                            makeList = new ArrayList<>();
                            try {
                                makeList = Arrays.asList(mapper.readValue(myResponse, String[].class));
                            }catch (IOException e){
                                e.printStackTrace();
                            }

                            autoCompleteTextView.setAdapter(new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,makeList));

                        }
                    });
                }
            }
        });

        return view;
    }

    public String getFavoriteMake(){
        return autoCompleteTextView.getText().toString();
    }
}