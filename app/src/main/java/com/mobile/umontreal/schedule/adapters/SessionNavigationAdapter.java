package com.mobile.umontreal.schedule.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.objects.Session;

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
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
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

        return setViews(position, convertView, parent);
    }

    private View setViews(int position, View convertView, ViewGroup parent) {

        String resKey = "season_" + session.get(position).getSeason().toString().toLowerCase();
        int resId = context.getResources().getIdentifier(resKey,"string",context.getPackageName());
        String season = context.getResources().getString(resId);

        sessionTitle = (TextView) convertView.findViewById(R.id.session_title);
        sessionTitle.setText(season);

        sessionYear = (TextView) convertView.findViewById(R.id.session_year);
        sessionYear.setText("" + session.get(position).getYear());

        convertView.setTag(session.get(position).getSeason());

        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_session_navigation, null);
        }

        return setViews(position, convertView, parent);
    }
}
