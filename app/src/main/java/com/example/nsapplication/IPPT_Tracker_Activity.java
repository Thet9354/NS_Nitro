package com.example.nsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nsapplication.AgeInventory.ageData;
import com.example.nsapplication.IPPTInventory.Data;
import com.google.android.material.slider.Slider;

public class IPPT_Tracker_Activity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener{

    private CustomSpinner spinner_vocation, spinner_age;
    private VocationAdapter vocationAdapter;
    private AgeAdapter ageAdapter;
    private TextView txtView_pushUp_points, txtView_sitUp_points, txtView_running_points, txtView_total_points;
    private Button btn_save;
    private ImageView btn_back;
    private com.google.android.material.slider.Slider slider_pushUp, slider_sitUp, slider_running;

    private String mVocation;
    private String mAge;

    private String currentProgress_pushUp;
    private String currentProgress_sitUp;
    private String currentProgress_run;
    private String currentProgress_total;
    private String IPPTStatus;

    private int val_pushUp;
    private int val_sitUp;
    private int val_run;
    private int val_total;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ippt_tracker);

        initView();

        vocationSpinner();

        ageSpinner();

        pageDirectories();

        sliderBar();
    }

    private void sliderBar() {
        /** OnSliderTouchListener for Push Up**/
        slider_pushUp.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                val_pushUp = (int) slider.getValue();

                val_total = val_pushUp + val_sitUp + val_run;
                totalScore_calculation();

                Log.d("value= ", val_pushUp + "");
                currentProgress_pushUp = String.valueOf(val_pushUp);
                txtView_pushUp_points.setText(currentProgress_pushUp);


            }
        });

        /** OnSliderTouchListener for Push Up**/
        slider_sitUp.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                val_sitUp = (int) slider.getValue();

                val_total = val_pushUp + val_sitUp + val_run;
                totalScore_calculation();

                Log.d("value= ", val_sitUp + "");
                currentProgress_sitUp = String.valueOf(val_sitUp);
                txtView_sitUp_points.setText(currentProgress_sitUp);
            }
        });

        /** OnSliderTouchListener for 2.4km run**/
        slider_running.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                val_run = (int) slider.getValue();

                Log.d("value= ", val_run + "");
                if (val_run >= 0 && val_run < 10)
                    val_run = 50;
                else if (val_run >= 10 && val_run < 15)
                    val_run = 30;
                else if (val_run >=15 && val_run < 20)
                    val_run = 10;
                else
                    val_run = 5;

                val_total = val_pushUp + val_sitUp + val_run;
                totalScore_calculation();

                currentProgress_run = String.valueOf(val_run);
                txtView_running_points.setText(currentProgress_run);
            }
        });

    }

    private void totalScore_calculation() {
        val_total = val_pushUp + val_sitUp + val_run;
        System.out.println(val_total);


        if (val_total >= 0 && val_total < 50)
            IPPTStatus = "Bronze";
        else if (val_total >= 50 && val_total < 100)
            IPPTStatus = "Silver";
        else
            IPPTStatus = "Gold";

        currentProgress_total = String.valueOf(val_total);
        txtView_total_points.setText("Total: " + currentProgress_total + " - " + IPPTStatus);
    }

    private void pageDirectories() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValidation();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FitnessTracker_Activity.class));
            }
        });
    }

    private void inputValidation() {
        validateVocation();
        validateAge();
        confirmInput();
        addData();
    }

    private void addData() {

        IPPTModel ipptModel;

        if (!confirmInput())
        {
            return;
        }
        else
        {
            try {
                ipptModel = new IPPTModel(-1, mVocation, mAge, currentProgress_pushUp, currentProgress_sitUp, currentProgress_run, currentProgress_total, IPPTStatus);
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Error saving information, please try again", Toast.LENGTH_SHORT).show();
                ipptModel = new IPPTModel(-1, "error", "error", "error", "error", "error", "error", "error");
            }

            IPPTDataBaseHelper ipptDataBaseHelper = new IPPTDataBaseHelper(IPPT_Tracker_Activity.this);
            boolean success = ipptDataBaseHelper.addOneData(ipptModel);

            Toast.makeText(this, "Information saved successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(IPPT_Tracker_Activity.this, FitnessTracker_Activity.class);
            startActivity(intent);
            //TODO: Add to database ltr
        }
    }

    private boolean confirmInput()
    {
        if (!validateAge() | !validateVocation())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean validateAge() {
        String ageInput = spinner_age.toString().trim();
        System.out.println(ageInput);
        // if vocation field is empty
        // it will display error message "Field cannot be empty
        if (ageInput.isEmpty())
        {
            Toast.makeText(this, "Age Field can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean validateVocation() {
        System.out.println(mVocation);
        // if vocation field is empty
        // it will display error message "Field cannot be empty
        if (mVocation.isEmpty())
        {
            Toast.makeText(this, "Vocation Field can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            return true;
        }
    }

    private void ageSpinner() {
        ageAdapter = new AgeAdapter(IPPT_Tracker_Activity.this, ageData.getAgeList());
        spinner_age.setAdapter(ageAdapter);
        mAge = spinner_age.getSelectedItem().toString();
    }

    private void initView() {
        /** CustomSpinner **/
        spinner_vocation = findViewById(R.id.spinner_vocation);
        spinner_age = findViewById(R.id.spinner_age);

        /** TextView **/
        txtView_pushUp_points = findViewById(R.id.txtView_pushUp_points);
        txtView_sitUp_points = findViewById(R.id.txtView_sitUp_points);
        txtView_running_points = findViewById(R.id.txtView_running_points);
        txtView_total_points = findViewById(R.id.txtView_total_points);

        /** com.google.android.material.slider.Slider **/
        slider_pushUp = findViewById(R.id.slider_pushUp);
        slider_sitUp = findViewById(R.id.slider_sitUp);
        slider_running = findViewById(R.id.slider_running);

        /** Button **/
        btn_save = findViewById(R.id.btn_save);

        /** ImageView **/
        btn_back = findViewById(R.id.btn_back);
    }

    private void vocationSpinner() {
        vocationAdapter = new VocationAdapter(IPPT_Tracker_Activity.this, Data.getVocationList());
        spinner_vocation.setAdapter(vocationAdapter);
        spinner_vocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                mVocation = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mVocation = spinner_vocation.getSelectedItem().toString();

    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        spinner_vocation.setBackground(getResources().getDrawable(R.drawable.bg_spinner_vocation_up));
        spinner_age.setBackground(getResources().getDrawable(R.drawable.bg_spinner_vocation_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        spinner_vocation.setBackground(getResources().getDrawable(R.drawable.bg_spinner_vocation));
        spinner_age.setBackground(getResources().getDrawable(R.drawable.bg_spinner_vocation_up));
    }
}