package com.example.nsapplication;

import com.example.nsapplication.EventInventory.CalendarUtils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EventModel {


    public static ArrayList<EventModel> eventModelArrayList = new ArrayList<>();

//    public static ArrayList<EventModel> eventsForDate(LocalDate date)
//    {
//        ArrayList<EventModel> eventModels = new ArrayList<>();
//
//        for (EventModel eventModel : eventModelArrayList)
//        {
//            if (eventModel.getDate().equals(date))
//                eventModels.add(eventModel);
//        }
//        return eventModels;
//    }

    private int id;
    private String event;
    private String notes;
    private String date;
    private String time;
    private boolean isPrioritized;

    public EventModel(int id, String event, String notes, String date, String time, boolean isPrioritized) {
        this.id = id;
        this.event = event;
        this.notes = notes;
        this.date = date;
        this.time = time;
        this.isPrioritized = isPrioritized;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPrioritized() {
        return isPrioritized;
    }

    public void setPrioritized(boolean prioritized) {
        isPrioritized = prioritized;
    }

}
