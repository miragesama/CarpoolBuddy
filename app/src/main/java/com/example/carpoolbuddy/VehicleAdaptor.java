package com.example.carpoolbuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdaptor extends RecyclerView.Adapter<VehicleAdaptor.VehicleActivityHolder>
    // previous extends RecyclerView.Adapter<VehicleActivityHolder>
{
        private ArrayList<Vehicle> allVehicles;
        private RecyclerViewClickListener listener;  // for RV click

        public VehicleAdaptor (ArrayList<Vehicle> myVehicles, RecyclerViewClickListener listener)
        {
            allVehicles = myVehicles;
            this.listener = listener; // for RV click
            System.out.println("*** at end of VehicleAdaptor");
        }

    public class VehicleActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            protected TextView ModelText;
            protected TextView CapacityText;
            //private VehicleAdaptor.RecyclerViewClickListener listener;  // for RV click

            public VehicleActivityHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setOnClickListener(this); // for RV click, bind the listener

                // this is Activity Holder of the Name and Status fields of recycler view
                ModelText = itemView.findViewById(R.id.vecRecTextModel);
                CapacityText = itemView.findViewById(R.id.vecRecTextCapacity);
            }

            @Override
            public void onClick(View view) {
                listener.onClick(view, getAdapterPosition());  // call the onClick in listener
            }
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
            holder.ModelText.setText("    "+v.getVehicleType()+" - "+v.getModel());
            holder.CapacityText.setText("Seats left: "+v.getCapacity().toString());
        }

        @Override
    public int getItemCount()
    {
        return allVehicles.size();
    }


    // for RV click
    public interface RecyclerViewClickListener{
            void onClick(View v, int position);
    }


}
