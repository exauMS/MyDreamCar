package com.helb.mydreamcar.Apis;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helb.mydreamcar.RecommendationAdapter;
import com.helb.mydreamcar.model.Car;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CarDataAPI {
    private static String myResponse="No Result";

    public static synchronized void setRequest(String url){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)//example : "https://car-data.p.rapidapi.com/cars?limit=10&page=0"
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
                    myResponse = response.body().string();
                    CarDataAPI.setMyResponse(myResponse);

                }
            }
        });
    }

    protected static void setMyResponse(String response){
        myResponse=response;
    }

    public static String getRequestResult(){
        return myResponse;
    }
}
