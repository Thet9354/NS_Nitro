package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Ad_Preference_Activity extends AppCompatActivity {

    private ImageView btn_back;

    private TextView txtView_Done;

    private androidx.appcompat.widget.SwitchCompat ad_preference_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_preference);

        initView();

        pageDirectories();
    }

    private void pageDirectories() {
        /* onClickListener for back and done btn */
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtView_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingsPage_Activity.class));
            }
        });

        /* onCheckChangedListener for ad_preference_switch */
        ad_preference_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    onResume();
                else
                    Toast.makeText(Ad_Preference_Activity.this, "Personalized ads has been switched off", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        txtView_Done = findViewById(R.id.txtView_Done);
        ad_preference_switch = findViewById(R.id.ad_preference_switch);
    }
}