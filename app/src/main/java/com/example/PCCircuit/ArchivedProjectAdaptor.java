package com.example.PCCircuit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This class is used by ArchivedProjectInfoActivity's Recycler View, it also contains the ViewHolder class
 * It is called by ArchivedProjectInfoActivity to display the Projects on RecyclerView
 * It also implements the onClick listener for user to click into a project to see details
 *
 * @author adrianlee
 * @version 1.0
 */
public class ArchivedProjectAdaptor extends RecyclerView.Adapter<ArchivedProjectAdaptor.ArchivedProjectActivityHolder>
{
    // defines local variable
    private ArrayList<Project> allProjects;
    private RecyclerViewClickListener listener;  // for RV click

    // constructor
    public ArchivedProjectAdaptor(ArrayList<Project> myProjects, RecyclerViewClickListener listener)
    {
        allProjects = myProjects;
        this.listener = listener; // for RV click
        System.out.println("*** at end of ArchivedProjectAdaptor");
    }

    /**
     * This class is Activity Holder class for RecyclerView, with OnClickListener for click
     */
    public class ArchivedProjectActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // the two fields to display on RV
        protected TextView ModelText;
        protected TextView CapacityText;

        // Activity Holder for RV with onClick listener
        public ArchivedProjectActivityHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); // for RV click, bind the listener

            // this is Activity Holder of the Name and Status fields of recycler view
            ModelText = itemView.findViewById(R.id.archiveRecField1);
            CapacityText = itemView.findViewById(R.id.archiveRecField2);
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
    public ArchivedProjectActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_archive_rec_view, parent, false);
        ArchivedProjectActivityHolder holder = new ArchivedProjectActivityHolder(myView);
        return holder;
    }

    /**
     * This method pass the vehicle data to ViewHolder to display
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ArchivedProjectActivityHolder holder, int position) {
        Project v = allProjects.get(position);
        holder.ModelText.setText("    "+v.getProjectType()+" - "+v.getProjectID());
        holder.CapacityText.setText("Customer: "+v.getcustomerName().toString());
        //holder.CapacityText.setText("Project info: "+v.getSpreadUrl().toString());
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
