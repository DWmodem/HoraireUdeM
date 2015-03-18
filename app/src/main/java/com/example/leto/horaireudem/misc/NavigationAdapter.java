package com.example.leto.horaireudem.misc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leto.horaireudem.R;

import java.util.ArrayList;

/**
 * Created by Corneliu on 16-Mar-2015.
 */
public class NavigationAdapter extends BaseAdapter {

    private TextView txtTitle;
    private ArrayList<SpinnerNavItem> spinnerNavItem;
    private Context context;


    public NavigationAdapter(Context context,
                                  ArrayList<SpinnerNavItem> spinnerNavItem) {
        this.spinnerNavItem = spinnerNavItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return spinnerNavItem.size();
    }

    @Override
    public Object getItem(int index) {
        return spinnerNavItem.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.trimester_item, null);
        }

        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(spinnerNavItem.get(position).getTitle()
                + " " + spinnerNavItem.get(position).getYear());
        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.trimester_item, null);
        }

        txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(spinnerNavItem.get(position).getTitle()
                + " " + spinnerNavItem.get(position).getYear());
        return convertView;
    }
}
