package com.helb.mydreamcar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.helb.mydreamcar.model.Post;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private static StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    private List<Post> postList;
    private Post post;
    private RecyclerView recyclerView;
    private static View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewHome);
        floatingActionButton = view.findViewById(R.id.add_button);
        postList = new ArrayList<>();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),CreatePostActivity.class));
            }
        });


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
                valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            post = new Post(
                                    ds.child("make").getValue(String.class),
                                    ds.child("model").getValue(String.class),
                                    ds.child("year").getValue(String.class),
                                    ds.child("type").getValue(String.class),
                                    ds.child("creator").getValue(String.class),
                                    ds.child("date").getValue(String.class),
                                    ds.child("url").getValue(String.class));

                            postList.add(post);
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
                        recyclerView.setAdapter(new HomeAdapter(getActivity(), postList));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                databaseReference.addListenerForSingleValueEvent(valueEventListener);

            }
        });

        return view;
    }

    public static void getCarImageFromStorage(String url, ImageView view){
        storageReference = FirebaseStorage.getInstance().getReference().child(url);
        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                String carImageUrl = task.getResult().toString();
                Picasso.get().load(carImageUrl).into(view);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("FAILED");
            }
        });
    }


}