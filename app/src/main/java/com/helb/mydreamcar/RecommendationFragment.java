package com.helb.mydreamcar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.helb.mydreamcar.model.Car;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.callback.Callback;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecommendationFragment extends Fragment {

    private List<Car> carList, accurateList, abstractList;
    private RecyclerView recyclerView;
    private String accurateResultFromScenario="", abstractResultFromScenario="";
    private static boolean scenarioFilled=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        setResultFromScenarioDB();


        return view;
    }



    private  void setResultFromScenarioDB(){
        //check if a user has already completed his scenario and then send it to the adapter for the display
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Scenarios");
        Query query = ref.orderByChild("creatorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) snapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);
                        HashMap<String, Object> scenarioData = (HashMap<String, Object>) data;
                        abstractResultFromScenario = (scenarioData.get("abstractScenario").toString());
                        accurateResultFromScenario = (scenarioData.get("accurateScenario").toString());

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
                            System.out.println(abstractResultFromScenario);
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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public static void getCarImageFromAPI(String _make,String _model, ImageView view){
        String url = "https://cdn.imagin.studio/getImage?&customer=behelb&make="+_make+"&modelFamily="+_model+"&modelRange=&modelVariant=&modelYear=&powerTrain=&transmission=&bodySize=&trim=&paintId=imagin-grey&angle=01";

        Picasso.get().load(url).into(view);
    }


}