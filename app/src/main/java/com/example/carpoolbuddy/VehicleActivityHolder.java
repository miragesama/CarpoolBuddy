package com.example.carpoolbuddy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
/* moved to Adapter to resolve null object reference issue for onClickListener
public class VehicleActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

        protected TextView ModelText;
        protected TextView CapacityText;
        private VehicleAdaptor.RecyclerViewClickListener listener;  // for RV click

        public VehicleActivityHolder(@NonNull View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this); // for RV click, bind the listener

            // this is Activity Holder of the Name and Status fields of recycler view
            ModelText = itemView.findViewById(R.id.vecRecTextModel);
            CapacityText = itemView.findViewById(R.id.vecRecTextCapacity);

        }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition());  // call the onClick in listener
    }

}
*/