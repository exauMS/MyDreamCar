package com.helb.mydreamcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.helb.mydreamcar.model.Post;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder>{
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
        holder.carMake.setText(posts.get(position).getMake());
        holder.carModel.setText(posts.get(position).getModel());
        holder.carYear.setText(posts.get(position).getYear());
        holder.carType.setText(posts.get(position).getType());
        holder.userName.setText(posts.get(position).getCreator());
        holder.date.setText(posts.get(position).getDate());
        HomeFragment.getCarImageFromStorage(posts.get(position).getUrl(), holder.carImage);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
