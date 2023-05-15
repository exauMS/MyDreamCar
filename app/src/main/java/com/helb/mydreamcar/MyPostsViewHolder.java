package com.helb.mydreamcar;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class MyPostsViewHolder extends RecyclerView.ViewHolder {
    ImageView carImageMyPosts;
    TextView makeMyPosts, modelMyPosts, yearMyPosts;
    LinearLayout myPostsLinearLayout;

    public MyPostsViewHolder(@NonNull View itemView) {
        super(itemView);
        carImageMyPosts = itemView.findViewById(R.id.carImageMyPosts);
        makeMyPosts = itemView.findViewById(R.id.makeMyPosts);
        modelMyPosts = itemView.findViewById(R.id.modelMyPosts);
        yearMyPosts = itemView.findViewById(R.id.yearMyPosts);
        myPostsLinearLayout = itemView.findViewById(R.id.myPostsLinearLayoutProfile);

    }
}
