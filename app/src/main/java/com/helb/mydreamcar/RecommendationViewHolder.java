package com.helb.mydreamcar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendationViewHolder extends RecyclerView.ViewHolder{

    ImageView carImage;
    TextView carMake, carModel, carYear, carType;
    public RecommendationViewHolder(@NonNull View itemView) {
        super(itemView);
        carImage = itemView.findViewById(R.id.carImage);
        carMake = itemView.findViewById(R.id.carMake);
        carModel = itemView.findViewById(R.id.carModel);
        carYear = itemView.findViewById(R.id.carYear);
        carType = itemView.findViewById(R.id.carType);

    }
}
