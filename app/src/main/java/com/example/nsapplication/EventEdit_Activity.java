package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nsapplication.EventInventory.CalendarUtils;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class EventEdit_Activity extends AppCompatActivity  {

    private EditText editTxt_eventName;
    private TextView txtView_eventDate, txtView_eventTime;

    public static final int THEME_HOLO_DARK = 2;

    private int hour, minutes;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidget();
        time = LocalTime.now();
        txtView_eventDate.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        txtView_eventTime.setText("Time: " + CalendarUtils.formattedTime(time));

        pageDirectories();

    }

    private void pageDirectories() {
        txtView_eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        hour = selectedHour;
                        minutes = selectedMinute;


                        txtView_eventTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minutes));
                    }
                };


                TimePickerDialog timePickerDialog = new TimePickerDialog(EventEdit_Activity.this, THEME_HOLO_DARK, onTimeSetListener, hour, minutes, true);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
    }

    private void initWidget()
    {
        editTxt_eventName = findViewById(R.id.editTxt_eventName);
        txtView_eventDate = findViewById(R.id.txtView_eventDate);
        txtView_eventTime = findViewById(R.id.txtView_eventTime);
    }

    public void saveEventAction(View view)
    {
        String eventName = editTxt_eventName.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}