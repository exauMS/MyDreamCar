package com.helb.mydreamcar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helb.mydreamcar.model.Car;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecommendationFragment extends Fragment {

    private List<Car> carList;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://car-data.p.rapidapi.com/cars?limit=10&page=0")
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
                            System.out.println(myResponse);
                            ObjectMapper mapper = new ObjectMapper();
                            carList = new ArrayList<>();
                            try {
                                carList = Arrays.asList(mapper.readValue(myResponse, Car[].class));
                            }
                            catch (IOException e){
                                e.printStackTrace();
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
                            recyclerView.setAdapter(new RecommendationAdapter(getActivity(), carList));
                        }
                    });
                }
            }
        });

        return view;
    }

    public static void getCarImageFromAPI(String _make,String _model, ImageView view){
        String url = "https://cdn.imagin.studio/getImage?&customer=behelb&make="+_make+"&modelFamily="+_model+"&modelRange=&modelVariant=&modelYear=&powerTrain=&transmission=&bodySize=&trim=&paintId=imagin-grey&angle=01";

        Picasso.get().load(url).into(view);
    }
}