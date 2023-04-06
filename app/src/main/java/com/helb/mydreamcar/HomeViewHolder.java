package com.helb.mydreamcar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeViewHolder extends RecyclerView.ViewHolder{
    ImageView carImage;
    TextView carMake, carModel, carYear, carType, userName, date;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        carImage = itemView.findViewById(R.id.carImageHome);
        carMake = itemView.findViewById(R.id.carMakeHome);
        carModel = itemView.findViewById(R.id.carModelHome);
        carYear = itemView.findViewById(R.id.carYearHome);
        carType = itemView.findViewById(R.id.carTypeHome);
        userName = itemView.findViewById(R.id.usernameHome);
        date = itemView.findViewById(R.id.dateHome);

    }
}
