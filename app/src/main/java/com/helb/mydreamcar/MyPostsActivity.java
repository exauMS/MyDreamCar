package com.helb.mydreamcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.helb.mydreamcar.model.Post;

import java.util.ArrayList;
import java.util.List;

public class MyPostsActivity extends AppCompatActivity {

    private ImageView imageNoContentYetMyPosts;
    private List<Post> myPostList;
    private Post post;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        imageNoContentYetMyPosts = findViewById(R.id.imageNoContentYetMyPosts);
        recyclerView = findViewById(R.id.recyclerViewMyPosts);
        myPostList = new ArrayList<>();

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = dbReference.orderByChild("creatorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    post = new Post(
                            ds.child("make").getValue(String.class),
                            ds.child("model").getValue(String.class),
                            ds.child("year").getValue(String.class),
                            ds.child("type").getValue(String.class),
                            ds.child("creator").getValue(String.class),
                            ds.child("date").getValue(String.class),
                            ds.child("url").getValue(String.class),
                            ds.child("location").getValue(String.class),
                            ds.child("creatorEmail").getValue(String.class));
                    myPostList.add(post);
                }
                if(myPostList.isEmpty()){
                    imageNoContentYetMyPosts.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }else{
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
                    recyclerView.setAdapter(new MyPostsAdapter(getApplicationContext(), myPostList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}