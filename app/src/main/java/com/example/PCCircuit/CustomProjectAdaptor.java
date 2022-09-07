package com.example.PCCircuit;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

/**
 * This class is used by CustomerProjectInfoActivity's Recycler View, it also contains the ViewHolder class
 * It is called by CustomerProjectInfoActivity to display the projects on RecyclerView
 * It also implements the onClick listener for user to click into a vehicle to see details
 *
 * @author adrianlee
 * @version 1.0
 */
public class CustomProjectAdaptor extends RecyclerView.Adapter<CustomProjectAdaptor.CustomProjectActivityHolder>
{
    // defines local variable
    private ArrayList<Project> allProjects;
    private CustomProjectAdaptor.RecyclerViewClickListener listener;  // for RV click

    // constructor
    public CustomProjectAdaptor(ArrayList<Project> myProjects, CustomProjectAdaptor.RecyclerViewClickListener listener)
    {
        allProjects = myProjects;
        this.listener = listener; // for RV click
        System.out.println("*** at end of CustomProjectAdaptor");
    }

    /**
     * This class is Activity Holder class for RecyclerView, with OnClickListener for click
     */
    public class CustomProjectActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // the two fields to display on RV
        protected TextView ModelText;
        protected TextView CapacityText;

        // Activity Holder for RV with onClick listener
        public CustomProjectActivityHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this); // for RV click, bind the listener

            // this is Activity Holder of the Name and Status fields of recycler view
            ModelText = itemView.findViewById(R.id.custRecViewField1);
            CapacityText = itemView.findViewById(R.id.custRecViewField2);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());  // call the onClick in listener
        }
    }

    @NonNull
    @Override
    public CustomProjectAdaptor.CustomProjectActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.activity_customer_info_rec_view, parent, false);
        CustomProjectAdaptor.CustomProjectActivityHolder holder = new CustomProjectAdaptor.CustomProjectActivityHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomProjectAdaptor.CustomProjectActivityHolder holder, int position) {
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
