package com.example.nsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalenarEventEdit_Activity extends AppCompatActivity {

    private static final int THEME_HOLO_DARK = 2;

    private TextView txtView_eventTime, txtView_eventDate;
    private RelativeLayout rel_date, rel_notification, rel_time;
    private androidx.appcompat.widget.SwitchCompat priority_Switch, time_switch;
    private Button btn_saveEvent;
    private EditText editTxt_event, editTxt_notes;
    private ImageView btn_back;

    private int hour, minute;
    private boolean isPrioritized = false;


    private String event;
    private String notes;
    private String time;
    private String date;
    private String format;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calenar_event_edit);

        intent = getIntent();

        initView();

        pageDirectories();


    }

    private void pageDirectories() {

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CalendarEvent_Activity.class));
            }
        });

        /** OnChangeListener for time_switch **/
        time_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    // The switch is enabled
                    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                        {
                            hour = selectedHour;
                            minute = selectedMinute;

                            if (hour == 0) {

                                hour += 12;

                                format = "AM";
                            }
                            else if (hour == 12) {

                                format = "PM";

                            }
                            else if (hour > 12) {

                                hour -= 12;

                                format = "PM";

                            }
                            else {

                                format = "AM";
                            }

                            //String am_pm = (selectedHour < 12) ? "AM" : "PM";

                            txtView_eventTime.setVisibility(View.VISIBLE);
                            txtView_eventTime.setText(String.format(Locale.getDefault(), "%02d:%02d" + format, hour, minute, format));
                            time = txtView_eventTime.getText().toString().trim();
                        }
                    };

                    TimePickerDialog timePickerDialog = new TimePickerDialog(CalenarEventEdit_Activity.this, THEME_HOLO_DARK, onTimeSetListener, hour, minute, false);

                    timePickerDialog.setTitle("Select Time");
                    timePickerDialog.show();
                }
                else
                {
                    txtView_eventTime.setVisibility(View.GONE);
                    // The switch is disabled
                }
            }
        });


        /** OnChangeListener for priority_switch **/
        priority_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    // The switch is enabled
                    isPrioritized = true;
                }
                else
                    // The switch is disabled
                    isPrioritized = false;
            }
        });

        /** OnClickedListener for btn_saveEvent **/
        btn_saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validating inputs
                validateEvent();
                validatePriority();
                validateTime();
                addDate();
            }
        });

    }


    private void addDate() {

        EventModel eventModel;
        if (!validateEvent() | !validateTime() | !validatePriority())
        {
            if (!validateEvent())
            {
                // Event creation not approved
                pageDirectories();
            }
            else if (!validateTime())
            {

                try {// Adding to SQLite database


                    eventModel = new EventModel(-1, event, notes, date, time, isPrioritized);

//                    // Adding to EventModel arraylist
//                    EventModel model = new EventModel(-1, event, notes, date, time, isPrioritized);
//                    EventModel.eventModelArrayList.add(model);
//                    finish();
                }
                catch (Exception e)
                {
                    Toast.makeText(this, "Error saving data, please try again", Toast.LENGTH_SHORT).show();
                    eventModel = new EventModel(-1, "error", "error", "error", "error", isPrioritized);
                }

                EventDataBaseHelper eventDataBaseHelper = new EventDataBaseHelper(CalenarEventEdit_Activity.this);
                boolean success = eventDataBaseHelper.addOneEvent(eventModel);

                Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CalenarEventEdit_Activity.this, CalendarEvent_Activity.class);
                startActivity(intent);
            }

        }
        else
        {
            try {
                eventModel = new EventModel(-1, event, notes, date, time, isPrioritized);

//                // Adding to EventModel arraylist
//                EventModel model = new EventModel(-1, event, notes, date, time, isPrioritized);
//                EventModel.eventModelArrayList.add(model);
//                finish();
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Error saving data, please try again", Toast.LENGTH_SHORT).show();
                eventModel = new EventModel(-1, "error", "error", "error", "error", isPrioritized);
            }

            EventDataBaseHelper eventDataBaseHelper = new EventDataBaseHelper(CalenarEventEdit_Activity.this);
            boolean success = eventDataBaseHelper.addOneEvent(eventModel);

            Toast.makeText(this, "Event saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CalenarEventEdit_Activity.this, CalendarEvent_Activity.class);
            startActivity(intent);
        }

    }

    private boolean validatePriority() {
        if (priority_Switch.isChecked())
        {
            isPrioritized = true;
            return true;
        }
        else
        {
            isPrioritized = false;
            return false;
        }
    }

    private boolean validateTime() {
        if (time_switch.isChecked())
        {
            return true;
        }
        else
            time = null;
            return false;
    }

    private boolean validateEvent() {
        event = editTxt_event.getText().toString().trim();
        notes = editTxt_notes.getText().toString().trim();

        if (event.isEmpty())
        {
            editTxt_event.setError("Field cannot be empty");
            return false;
        }
        else
        {
            editTxt_event.setError(null);
            return true;
        }
    }

    private void initView() {
        txtView_eventTime = findViewById(R.id.txtView_eventTime);
        txtView_eventDate = findViewById(R.id.txtView_eventDate);

        editTxt_event = findViewById(R.id.editTxt_event);
        editTxt_notes = findViewById(R.id.editTxt_notes);

        priority_Switch = findViewById(R.id.priority_Switch);
        time_switch = findViewById(R.id.time_switch);

        btn_saveEvent = findViewById(R.id.btn_saveEvent);
        btn_back = findViewById(R.id.btn_back);

        date = intent.getStringExtra("selectedDate");
        txtView_eventDate.setText("Date: " + date);




    }
}