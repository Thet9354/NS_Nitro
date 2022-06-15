package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nsapplication.AgeInventory.ageData;
import com.example.nsapplication.IPPTInventory.Data;

public class IPPT_Tracker_Activity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener{

    private CustomSpinner spinner_vocation, spinner_age;

    private VocationAdapter vocationAdapter;

    private AgeAdapter ageAdapter;

    private TextView txtView_pushUp_points, txtView_sitUp_points, txtView_running_points, txtView_total_points;

    private Button btn_save;

    private com.google.android.material.slider.Slider slider_pushUp, slider_sitUp, slider_running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ippt_tracker);

        initView();

        vocationSpinner();

        ageSpinner();

        pageDirectories();
;    }

    private void pageDirectories() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValidation();
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
        if (!confirmInput())
        {
            pageDirectories();
        }
        else
        {
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
        String vocationInput = spinner_vocation.toString().trim();
        System.out.println(vocationInput);
        // if vocation field is empty
        // it will display error message "Field cannot be empty
        if (vocationInput.isEmpty())
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
        spinner_age.setSpinnerEventsListener(this);
        ageAdapter = new AgeAdapter(IPPT_Tracker_Activity.this, ageData.getAgeList());
        spinner_age.setAdapter(ageAdapter);
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
    }

    private void vocationSpinner() {
        spinner_vocation.setSpinnerEventsListener(this);
        vocationAdapter = new VocationAdapter(IPPT_Tracker_Activity.this, Data.getVocationList());
        spinner_vocation.setAdapter(vocationAdapter);

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