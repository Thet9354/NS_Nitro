package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Main_MenuPage_Activity extends AppCompatActivity {

    private androidx.cardview.widget.CardView cardView_fitnessTracker, cardView_calendar, cardView_bunkMate_Location, cardView_setting;
    private ImageView img_fitnessTracker, img_calendar, img_bunMate_Location, img_setting;
    private TextView txtView_fitnessTracker, txtView_calendar, txtView_bunkMate_Location, txtView_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_page);

        initView();

        pageDirectories();
    }

    private void pageDirectories() {
        /** OnClickListener for Fitness Tracker **/
        cardView_fitnessTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, FitnessTracker_Activity.class);
                startActivity(intent);
            }
        });

        img_fitnessTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, FitnessTracker_Activity.class);
                startActivity(intent);
            }
        });

        txtView_fitnessTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, FitnessTracker_Activity.class);
                startActivity(intent);
            }
        });


        /** OnClickListener for Calendar **/
        cardView_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, CalendarEvent_Activity.class);
                startActivity(intent);
            }
        });

        img_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, CalendarEvent_Activity.class);
                startActivity(intent);
            }
        });

        txtView_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, CalendarEvent_Activity.class);
                startActivity(intent);
            }
        });


        /** OnClickListener for Bunk mate Location **/
        cardView_bunkMate_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, GPS_Activity.class);
                startActivity(intent);
            }
        });

        img_bunMate_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, GPS_Activity.class);
                startActivity(intent);
            }
        });

        txtView_bunkMate_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, GPS_Activity.class);
                startActivity(intent);
            }
        });


        /** OnCLickListener for Setting page **/
        cardView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, SettingsPage_Activity.class);
                startActivity(intent);
            }
        });

        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, SettingsPage_Activity.class);
                startActivity(intent);
            }
        });

        txtView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_MenuPage_Activity.this, SettingsPage_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        /** CardView **/
        cardView_fitnessTracker = findViewById(R.id.cardView_fitnessTracker);
        cardView_calendar = findViewById(R.id.cardView_calendar);
        cardView_bunkMate_Location = findViewById(R.id.cardView_bunkMate_Location);
        cardView_setting = findViewById(R.id.cardView_setting);

        /** ImageView **/
        img_fitnessTracker = findViewById(R.id.img_fitnessTracker);
        img_calendar = findViewById(R.id.img_calendar);
        img_bunMate_Location = findViewById(R.id.img_bunMate_Location);
        img_setting = findViewById(R.id.img_setting);

        /** TextView **/
        txtView_fitnessTracker = findViewById(R.id.txtView_fitnessTracker);
        txtView_calendar = findViewById(R.id.txtView_calendar);
        txtView_bunkMate_Location = findViewById(R.id.txtView_bunkMate_Location);
        txtView_setting = findViewById(R.id.txtView_setting);
    }
}