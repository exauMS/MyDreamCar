package com.helb.mydreamcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.helb.mydreamcar.model.Post;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsViewHolder> implements Serializable {

    Context context;
    List<Post> myPosts;

    public MyPostsAdapter(Context context, List<Post> myPosts){
        this.context = context;
        this.myPosts = myPosts;
    }
    @NonNull
    @Override
    public MyPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPostsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_posts_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsViewHolder holder, int position) {
        int pos = position;
        holder.makeMyPosts.setText(myPosts.get(position).getMake());
        holder.modelMyPosts.setText(myPosts.get(position).getModel());
        holder.yearMyPosts.setText(myPosts.get(position).getYear());
        HomeFragment.getCarImageFromStorage(myPosts.get(position).getUrl(), holder.carImageMyPosts);
        holder.myPostsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> postInfoMap = new HashMap<>();
                postInfoMap.put("url",myPosts.get(pos).getUrl());
                postInfoMap.put("creator",myPosts.get(pos).getCreator());
                postInfoMap.put("date",myPosts.get(pos).getDate());
                postInfoMap.put("make",myPosts.get(pos).getMake());
                postInfoMap.put("model",myPosts.get(pos).getModel());
                postInfoMap.put("year",myPosts.get(pos).getYear());
                postInfoMap.put("type",myPosts.get(pos).getType());
                postInfoMap.put("location",myPosts.get(pos).getLocation());
                postInfoMap.put("creatorEmail",myPosts.get(pos).getCreatorEmail());

                Bundle bundle = new Bundle();
                bundle.putSerializable("postInfo", (Serializable) postInfoMap);
                Intent intent = new Intent(context.getApplicationContext(), PostDetailsActivity.class).putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myPosts.size();
    }
}
