package com.helb.mydreamcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.helb.mydreamcar.model.Car;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationViewHolder> {
    Context context;
    List<Car> cars;

    public RecommendationAdapter(Context context, List<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public RecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.car_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationViewHolder holder, int position) {
        holder.carMake.setText(cars.get(position).getMake());
        holder.carModel.setText(cars.get(position).getModel());
        holder.carYear.setText(Integer.toString(cars.get(position).getYear()));
        holder.carType.setText(cars.get(position).getType());
        RecommendationFragment.getCarImageFromAPI(cars.get(position).getMake(), cars.get(position).getModel(), holder.carImage);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
