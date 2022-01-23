package com.example.carpoolbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdaptor extends RecyclerView.Adapter<VehicleActivityHolder>
{
        private ArrayList<Vehicle> allVehicles;

        public VehicleAdaptor (ArrayList<Vehicle> myVehicles)
        {
            allVehicles = myVehicles;
        }

        @NonNull
        @Override

    public VehicleActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vehicle_rec_view, parent, false);
            VehicleActivityHolder holder = new VehicleActivityHolder(myView);
            return holder;
        }

        @Override
    public void onBindViewHolder(@NonNull VehicleActivityHolder holder, int position) {
            Vehicle v = allVehicles.get(position);
            holder.ModelText.setText((v.getModel()));
            holder.CapacityText.setText(v.getCapacity().toString());
        }

        @Override
    public int getItemCount()
    {
        return allVehicles.size();
    }


}
