package com.helb.mydreamcar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.helb.mydreamcar.model.Post;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class CreatePostActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 001;
    private Button addImageBtn, createPostBtn;
    private ImageView previewImage;
    private int SELECT_PICTURE=200;
    private Bitmap selectedImageBitmap;
    private Uri selectedImageUri;
    private TextInputEditText make,model,year,type,location;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private String userName;
    private  Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startActivity(new Intent(CreatePostActivity.this,Login.class));
            finish();
            return;
        }

        post = new Post();
        userName = "username";
        addImageBtn = findViewById(R.id.addImageBtn);
        createPostBtn = findViewById(R.id.createPostBtn);
        previewImage = findViewById(R.id.previewImage);
        make = findViewById(R.id.makeCreateEditText);
        model = findViewById(R.id.modelCreateEditText);
        year = findViewById(R.id.yearCreateEditText);
        type = findViewById(R.id.typeCreateEditText);
        location = findViewById(R.id.locationCreateEditText);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        //get username from db
        FirebaseUser currentUser = mAuth.getCurrentUser();
        databaseReference = database.getReference("Users");
        databaseReference.orderByChild("email").equalTo(currentUser.getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);
                        HashMap<String, Object> userData = (HashMap<String, Object>) data;
                        userName=(userData.get("userName").toString());

                    }




                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }
    private void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(intent);
    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        selectedImageUri = data.getData();
                        selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        previewImage.setImageBitmap(
                                selectedImageBitmap);

                    }
                }
            });

    private void uploadImage(){

        final String randomKey = UUID.randomUUID().toString();//security in case of same file name

        StorageReference carImageRef = storageReference.child("images/"+randomKey);

        carImageRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                post.setUrl("images/"+randomKey);
                uploadPost();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed To Create.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadPost(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());

        post.setMake(make.getText().toString());
        post.setModel(model.getText().toString());
        post.setYear(year.getText().toString());
        post.setType(type.getText().toString());
        post.setCreator(userName);
        post.setDate(currentDateAndTime);
        post.setLocation(location.getText().toString());
        post.setCreatorEmail(mAuth.getCurrentUser().getEmail());

        databaseReference = database.getReference("Posts");
        databaseReference.child("post "+post.getCreator()+ " "+post.getDate()).setValue(post);

        Snackbar.make(findViewById(android.R.id.content), "Post Created!", Snackbar.LENGTH_LONG).show();
        makeNotification();

        startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
        finish();



    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }

    public void makeNotification(){
        databaseReference = database.getReference("Scenarios");
        databaseReference.orderByChild("creatorEmail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String favoriteMake="", favoriteType="";
                final String CHANNEL_ID = "personal notifications";

                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : dataMap.keySet()) {

                        Object data = dataMap.get(key);
                        HashMap<String, Object> scenarioData = (HashMap<String, Object>) data;

                        favoriteMake=(scenarioData.get("favoriteMake").toString());
                        favoriteType=(scenarioData.get("favoriteType").toString());

                    }

                    if(post.getMake().equals(favoriteMake) || post.getType().equals(favoriteType)){

                        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
                            CharSequence name = "personal notifications";
                            String description = "personal notifications description";
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;

                            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                            notificationChannel.setDescription(description);

                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            notificationManager.createNotificationChannel(notificationChannel);
                        }
                        HashMap<String,String> postInfoMap = new HashMap<>();
                        postInfoMap.put("url",post.getUrl());
                        postInfoMap.put("creator",post.getCreator());
                        postInfoMap.put("date",post.getDate());
                        postInfoMap.put("make",post.getMake());
                        postInfoMap.put("model",post.getModel());
                        postInfoMap.put("year",post.getYear());
                        postInfoMap.put("type",post.getType());
                        postInfoMap.put("location",post.getLocation());
                        postInfoMap.put("creatorEmail",post.getCreatorEmail());

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("postInfo", (Serializable) postInfoMap);
                        Intent landingIntent = new Intent(getApplicationContext(),PostDetailsActivity.class).putExtras(bundle);
                        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent landingPendingIntent = PendingIntent.getActivity(getApplicationContext(),0,landingIntent,PendingIntent.FLAG_ONE_SHOT);


                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.splash_logo_mydreamcar)
                                .setContentTitle("A new car matches with your scenario!")
                                .setContentText(post.getMake()+","+post.getModel())
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(landingPendingIntent)
                                .setAutoCancel(true)
                                .setLargeIcon(selectedImageBitmap);

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


    public static int getNotificationId(){
        return NOTIFICATION_ID;
    }
}