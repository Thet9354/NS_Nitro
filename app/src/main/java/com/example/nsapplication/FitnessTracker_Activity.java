package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FitnessTracker_Activity extends AppCompatActivity {

    private LinearLayout linearLayout_IPPT_Tracker, linearLayout_BMI_Tracker;
    private androidx.cardview.widget.CardView cardView_IPPT_Tracker, cardView_BMI_Tracker;
    private TextView txtView_IPPT_Tracker, txtView_IPPT_Tracker_numeral, txtView_BMI_Tracker, txtView_BMI_Tracker_numeral;
    private ImageView imgView_IPPT_Tracker, imgView_BMI_Tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_tracker);

        initView();

        pageDirectories();
    }

    private void pageDirectories() {
        /** OnClickListener for IPPT Tracker **/
        linearLayout_IPPT_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, IPPT_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        cardView_IPPT_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, IPPT_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        txtView_IPPT_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, IPPT_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        txtView_IPPT_Tracker_numeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, IPPT_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        imgView_IPPT_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, IPPT_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        /** BMI Tracker **/
        linearLayout_BMI_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, BMI_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        cardView_BMI_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, BMI_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        txtView_BMI_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, BMI_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        txtView_BMI_Tracker_numeral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, BMI_Tracker_Activity.class);
                startActivity(intent);
            }
        });

        imgView_BMI_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FitnessTracker_Activity.this, BMI_Tracker_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        /** Linear Layout **/
        linearLayout_IPPT_Tracker = findViewById(R.id.linearLayout_IPPT_Tracker);
        linearLayout_BMI_Tracker = findViewById(R.id.linearLayout_BMI_Tracker);

        /** androidx.cardview.widget.CardView **/
        cardView_IPPT_Tracker = findViewById(R.id.cardView_IPPT_Tracker);
        cardView_BMI_Tracker = findViewById(R.id.cardView_BMI_Tracker);

        /** TextView **/
        txtView_IPPT_Tracker = findViewById(R.id.txtView_IPPT_Tracker);
        txtView_IPPT_Tracker_numeral = findViewById(R.id.txtView_IPPT_Tracker_numeral);
        txtView_BMI_Tracker = findViewById(R.id.txtView_BMI_Tracker);
        txtView_BMI_Tracker_numeral = findViewById(R.id.txtView_BMI_Tracker_numeral);

        /** ImageView **/
        imgView_IPPT_Tracker = findViewById(R.id.imgView_IPPT_Tracker);
        imgView_BMI_Tracker = findViewById(R.id.imgView_BMI_Tracker);
    }
}