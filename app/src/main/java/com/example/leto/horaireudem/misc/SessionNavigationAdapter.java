package com.example.leto.horaireudem.misc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leto.horaireudem.R;
import com.example.leto.horaireudem.objects.Session;

import java.util.ArrayList;

/**
 * Created by Corneliu on 16-Mar-2015.
 */
public class SessionNavigationAdapter extends BaseAdapter {

    private TextView sessionTitle;
    private TextView sessionYear;
    private ArrayList<Session> session;
    private Context context;


    public SessionNavigationAdapter(Context context,
                                    ArrayList<Session> session) {
        this.session = session;
        this.context = context;
    }

    @Override
    public int getCount() {
        return session.size();
    }

    @Override
    public Object getItem(int index) {
        return session.get(index);
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
        convertView = mInflater.inflate(R.layout.item_session_navigation, null);
        }

        sessionTitle = (TextView) convertView.findViewById(R.id.session_title);
        sessionTitle.setText(session.get(position).getSeason().toString());

        sessionYear = (TextView) convertView.findViewById(R.id.session_year);
        sessionYear.setText("" + session.get(position).getYear());

        // Set the selected navigation item
        if(position == 2){
           convertView.setActivated(true);
        }

        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_session_navigation, null);
        }

        sessionTitle = (TextView) convertView.findViewById(R.id.session_title);
        sessionTitle.setText(session.get(position).getSeason().toString());

        sessionYear = (TextView) convertView.findViewById(R.id.session_year);
        sessionYear.setText("" + session.get(position).getYear());

        return convertView;
    }
}
