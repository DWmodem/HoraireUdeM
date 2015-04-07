package com.mobile.umontreal.schedule.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.objects.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 07-Apr-2015.
 */
public class ScheduleContentAdapter extends ArrayAdapter<Schedule> {

    private ArrayList<Schedule> scheduleList;
    Activity activity;

    public ScheduleContentAdapter(Context context, Activity activity, int textViewResourceId,
                                  List<Schedule> scheduleList) {
        super(context, textViewResourceId, scheduleList);
        this.scheduleList = new ArrayList<Schedule>();
        this.scheduleList.addAll(scheduleList);
        this.activity = activity;
    }

    private class ViewHolder {
        TextView schedule_date;
        TextView schedule_title;
        TextView schedule_description;
        CheckBox schedule_check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_schedule, null);

            holder = new ViewHolder();
            holder.schedule_date = (TextView) convertView.findViewById(R.id.schedule_date);
            holder.schedule_title = (TextView) convertView.findViewById(R.id.schedule_title);
            holder.schedule_description = (TextView) convertView.findViewById(R.id.schedule_description);
            holder.schedule_check = (CheckBox) convertView.findViewById(R.id.schedule_check);
            convertView.setTag(holder);


        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Schedule schedule = scheduleList.get(position);
        holder.schedule_title.setText(" (" +  schedule.getProfessor() + ")");
        holder.schedule_description.setText(schedule.getLocation());


        return convertView;

    }



}
