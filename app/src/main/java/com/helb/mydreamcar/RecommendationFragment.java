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

    private List<Car> carList, accurateList, abstractList;
    private RecyclerView recyclerView;
    private static String accurateResultFromScenario="", abstractResultFromScenario="";
    private static boolean scenarioFilled=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        ObjectMapper mapper = new ObjectMapper();
        accurateList = new ArrayList<>();
        try {
            accurateList = Arrays.asList(mapper.readValue(accurateResultFromScenario, Car[].class));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        abstractList = new ArrayList<>();
        try {
            abstractList = Arrays.asList(mapper.readValue(abstractResultFromScenario, Car[].class));
        }
        catch (IOException e){
            e.printStackTrace();
        }

        carList = new ArrayList<>();
        carList.addAll(accurateList);
        carList.addAll(abstractList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(new RecommendationAdapter(getActivity(), carList));

        return view;
    }

    public static void setResultFromScenario(String accurateR, String abstractR){
        accurateResultFromScenario=accurateR;
        abstractResultFromScenario=abstractR;
        scenarioFilled=true;
    }

    public static boolean isScenarioFilled(){
        return scenarioFilled;
    }

    public static void getCarImageFromAPI(String _make,String _model, ImageView view){
        String url = "https://cdn.imagin.studio/getImage?&customer=behelb&make="+_make+"&modelFamily="+_model+"&modelRange=&modelVariant=&modelYear=&powerTrain=&transmission=&bodySize=&trim=&paintId=imagin-grey&angle=01";

        Picasso.get().load(url).into(view);
    }


}