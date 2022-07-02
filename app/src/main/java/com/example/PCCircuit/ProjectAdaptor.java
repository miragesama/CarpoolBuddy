package com.example.PCCircuit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This class is used by VehicleInfoActivity's Recycler View, it also contains the ViewHolder class
 * It is called by VehicleInfoActivity to display the vehicle model and capacity on RecyclerView
 * It also implements the onClick listener for user to click into a vehicle to see details
 *
 * @author adrianlee
 * @version 1.0
 */
public class ProjectAdaptor extends RecyclerView.Adapter<ProjectAdaptor.ProjectActivityHolder>
{
    // defines local variable
        private ArrayList<Project> allProjects;
        private RecyclerViewClickListener listener;  // for RV click

    // constructor
        public ProjectAdaptor(ArrayList<Project> myProjects, RecyclerViewClickListener listener)
        {
            allProjects = myProjects;
            this.listener = listener; // for RV click
            System.out.println("*** at end of ProjectAdaptor");
        }

    /**
     * This class is Activity Holder class for RecyclerView, with OnClickListener for click
     */
    public class ProjectActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            // the two fields to display on RV
            protected TextView ModelText;
            protected TextView CapacityText;

            // Activity Holder for RV with onClick listener
            public ProjectActivityHolder(@NonNull View itemView) {
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

    /**
     * This is the ViewHolder for the recycler view
     * @param parent
     * @param viewType
     * @return
     */
        @NonNull
        @Override
    public ProjectActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View myView = LayoutInflater.from(parent.getContext()).inflate
                    (R.layout.activity_vehicle_rec_view, parent, false);
            ProjectActivityHolder holder = new ProjectActivityHolder(myView);
            return holder;
        }

    /**
     * This method pass the vehicle data to ViewHolder to display
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ProjectActivityHolder holder, int position) {
            Project v = allProjects.get(position);
            holder.ModelText.setText("    "+v.getVehicleType()+" - "+v.getModel());
            holder.CapacityText.setText("Seats left: "+v.getCapacity().toString());
        }

        @Override
    public int getItemCount()
    {
        return allProjects.size();
    }


    /**
     * This is the interface for OnClick listener
     */
    public interface RecyclerViewClickListener{
            void onClick(View v, int position);
    }
}
