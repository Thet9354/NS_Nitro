package com.example.nsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarEvent_Adapter extends RecyclerView.Adapter<CalendarEvent_Adapter.ViewHolder> {

    private ArrayList<EventModel> eventModels = new ArrayList<>();

    private Context context;

    public CalendarEvent_Adapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtView_eventName.setText(eventModels.get(position).getEvent());
        holder.txtView_eventTiming.setText(eventModels.get(position).getTime());
        // might add in sometime abt priority events ltr
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, eventModels.get(holder.getAdapterPosition()).getEvent() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        // Getting size and number of events there are
        return eventModels.size();
    }

    public void setEventModels(ArrayList<EventModel> eventModels)
    {
        this.eventModels = eventModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // Declaring IDs from event_cell xml file.

        private RelativeLayout rel_events;
        private TextView txtView_eventName;
        private TextView txtView_eventTiming;
        private LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            //Extenuating inside constructor
            super(itemView);

            rel_events = itemView.findViewById(R.id.rel_events);
            txtView_eventName = itemView.findViewById(R.id.txtView_eventName);
            txtView_eventTiming = itemView.findViewById(R.id.txtView_eventTiming);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
