package com.example.nsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nsapplication.IPPTInventory.Vocation;

import java.util.List;

/********************************************
 *     Created by Thet Pine on 15-June-22.  *
 ********************************************/

public class VocationAdapter extends BaseAdapter {
    private Context context;
    private List<Vocation> vocationList;

    public VocationAdapter(Context context, List<Vocation> vocationList)
    {
        this.context = context;
        this.vocationList = vocationList;
    }

    @Override
    public int getCount() {
        return vocationList != null ? vocationList.size() :0;
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
                .inflate(R.layout.item_vocation, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);

        txtName.setText(vocationList.get(i).getName());

        return rootView;
    }
}
