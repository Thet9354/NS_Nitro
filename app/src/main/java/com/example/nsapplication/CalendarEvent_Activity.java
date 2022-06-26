package com.example.nsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.example.nsapplication.EventInventory.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarEvent_Activity extends AppCompatActivity{


    private CalendarView calendarView;
    private RecyclerView RecView_events;
    private Button btn_addEvent;
    private String selectedDate;
    private ImageView btn_back;

    LocalDate date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event);

        initView();

        pageDirectories();

        onResume();

        setEventAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        //ArrayList<EventModel> eventModels = EventModel.eventsForDate(date);

        CalendarEvent_Adapter adapter = new CalendarEvent_Adapter(this);
        //adapter.setEventModels(eventModels);

        RecView_events.setAdapter(adapter);
        RecView_events.setLayoutManager(new LinearLayoutManager(this));
    }

    private void pageDirectories() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Main_MenuPage_Activity.class));
            }
        });

        btn_addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarEvent_Activity.this, CalenarEventEdit_Activity.class);
                intent.putExtra("selectedDate", selectedDate);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                String selectedYear = String.valueOf(year);
                String selectedMonth = String.valueOf(month + 1);
                String selectedDayOfMonth = String.valueOf(dayOfMonth);
                selectedDate = selectedDayOfMonth + "/" + selectedMonth + "/" + selectedYear;

            }
        });
    }

    private void initView() {
        btn_addEvent = findViewById(R.id.btn_addEvent);
        btn_back = findViewById(R.id.btn_back);
        calendarView = findViewById(R.id.calendarView);
        RecView_events = findViewById(R.id.RecView_events);
    }


}