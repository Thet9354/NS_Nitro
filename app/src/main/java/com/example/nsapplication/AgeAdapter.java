package com.example.nsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nsapplication.AgeInventory.Age;
import com.example.nsapplication.IPPTInventory.Vocation;

import java.util.List;

/********************************************
 *     Created by Thet Pine on 15-May-21.  *
 ********************************************/

public class AgeAdapter extends BaseAdapter {

    private Context context;
    private List<Age> ageList;

    public AgeAdapter(Context context, List<Age> ageList)
    {
        this.context = context;
        this.ageList = ageList;
    }


    @Override
    public int getCount() {
        return ageList != null ? ageList.size() :0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_age, viewGroup, false);

        TextView txtAge = rootView.findViewById(R.id.age);

        txtAge.setText(ageList.get(i).getAge());

        return rootView;
    }
}
