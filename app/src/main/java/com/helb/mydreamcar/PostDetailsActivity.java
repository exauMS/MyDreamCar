package com.helb.mydreamcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.HashMap;

public class PostDetailsActivity extends AppCompatActivity implements Serializable {

    private Intent intent;
    private Bundle bundle;
    private HashMap<String, String> postInfoMap;
    private ImageView logo;
    private LinearLayout contactLL;

    private ImageButton btnOpenMap;
    private Button btnDeletePost;
    private ImageView carImage;
    private TextView userNameAndDate, make, model, year, type, location, creatorEmail;



    private int colorCount=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        //handle notification closing
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(CreatePostActivity.getNotificationId());
        //--------------


        intent = this.getIntent();
        bundle = intent.getExtras();
        postInfoMap = (HashMap<String, String>)bundle.getSerializable("postInfo");//get data passed through the intent

        btnOpenMap = findViewById(R.id.imageBtnOpenMap);
        carImage = findViewById(R.id.imageCarPostDetails);
        btnDeletePost =  findViewById(R.id.deletePostBtn);
        userNameAndDate = findViewById(R.id.userAndDatePostDetails);
        make = findViewById(R.id.makeValuePostDetails);
        model = findViewById(R.id.modelValuePostDetails);
        year = findViewById(R.id.yearValuePostDetails);
        type = findViewById(R.id.typeValuePostDetails);
        location = findViewById(R.id.locationPostDetails);
        creatorEmail = findViewById(R.id.creatorContactPostDetails);
        logo = findViewById(R.id.logoDetailsPage);
        contactLL = findViewById(R.id.contactLinearLayout);

        userNameAndDate.setText("Posted by "+postInfoMap.get("creator")+" on "+postInfoMap.get("date"));
        make.setText(postInfoMap.get("make"));
        model.setText(postInfoMap.get("model"));
        year.setText(postInfoMap.get("year"));
        type.setText(postInfoMap.get("type"));
        location.setText(postInfoMap.get("location"));
        creatorEmail.setText(postInfoMap.get("creatorEmail"));
        HomeFragment.getCarImageFromStorage(postInfoMap.get("url"), carImage);

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            contactLL.setVisibility(View.GONE);
        }


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send data to the target activity through the intent
                Bundle bundle = new Bundle();
                bundle.putSerializable("cityName", (Serializable) postInfoMap.get("location"));
                startActivity(new Intent(getApplicationContext(),MapsPageActivity.class).putExtras(bundle));

            }
        });

        //display delete button in the postDetailsPage if the post was created by the current user
        if(FirebaseAuth.getInstance().getCurrentUser()!=null && FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(postInfoMap.get("creatorEmail"))){
            btnDeletePost.setVisibility(View.VISIBLE);

            btnDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //get the ref of the post and the ref of the image linked to the post, then delete both

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(postInfoMap.get("url"));
                    Query postQuery = ref.orderByChild("url").equalTo(postInfoMap.get("url"));

                    if(storageRef!=null) {
                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(PostDetailsActivity.this, "Image deleted!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PostDetailsActivity.this, "Image does not deleted...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    postQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    System.out.println(postSnapshot.getValue().toString());
                                    postSnapshot.getRef().removeValue();
                                }
                                Toast.makeText(PostDetailsActivity.this, "Post deleted!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(PostDetailsActivity.this, databaseError.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });


        }







    }
}