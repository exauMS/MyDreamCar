package com.helb.mydreamcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.helb.mydreamcar.model.Post;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> implements Serializable{
    Context context;
    List<Post> posts;

    public HomeAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_car_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        int pos = position;
        holder.carMake.setText(posts.get(position).getMake());
        holder.carModel.setText(posts.get(position).getModel());
        holder.carYear.setText(posts.get(position).getYear());
        holder.carType.setText(posts.get(position).getType());
        holder.userName.setText(posts.get(position).getCreator());
        holder.date.setText(posts.get(position).getDate());
        HomeFragment.getCarImageFromStorage(posts.get(position).getUrl(), holder.carImage);
        holder.carImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              HashMap<String,String> postInfoMap = new HashMap<>();
              postInfoMap.put("url",posts.get(pos).getUrl());
              postInfoMap.put("creator",posts.get(pos).getCreator());
              postInfoMap.put("date",posts.get(pos).getDate());
              postInfoMap.put("make",posts.get(pos).getMake());
              postInfoMap.put("model",posts.get(pos).getModel());
              postInfoMap.put("year",posts.get(pos).getYear());
              postInfoMap.put("type",posts.get(pos).getType());

              Bundle bundle = new Bundle();
              bundle.putSerializable("postInfo", (Serializable) postInfoMap);
              context.startActivity(new Intent(context.getApplicationContext(), PostDetailsActivity.class).putExtras(bundle));
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
