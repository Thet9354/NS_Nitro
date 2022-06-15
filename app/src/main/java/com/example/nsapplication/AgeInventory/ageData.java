package com.example.nsapplication.AgeInventory;

import com.example.nsapplication.IPPTInventory.Vocation;

import java.util.ArrayList;
import java.util.List;

public class ageData {

    public static List<Age> getAgeList()
    {
        List<Age> ageList = new ArrayList<>();

        Age eighteen = new Age();
        eighteen.setAge("18");
        ageList.add(eighteen);

        Age twenty = new Age();
        twenty.setAge("20");
        ageList.add(twenty);

        Age twentyFive = new Age();
        twentyFive.setAge("25");
        ageList.add(twentyFive);

        Age thirty = new Age();
        thirty.setAge("30");
        ageList.add(thirty);

        Age thirtyFive = new Age();
        thirtyFive.setAge("35");
        ageList.add(thirtyFive);

        Age forty = new Age();
        forty.setAge("40");
        ageList.add(forty);

        Age fortyFive = new Age();
        fortyFive.setAge("45");
        ageList.add(fortyFive);

        Age fifty = new Age();
        fifty.setAge("50");
        ageList.add(fifty);

        Age fiftyFive = new Age();
        fiftyFive.setAge("55");
        ageList.add(fiftyFive);

        Age sixty = new Age();
        sixty.setAge("60");
        ageList.add(sixty);

        return ageList;
    }
}
