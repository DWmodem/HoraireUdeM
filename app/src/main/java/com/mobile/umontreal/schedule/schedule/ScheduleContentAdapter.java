package com.mobile.umontreal.schedule.schedule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.umontreal.schedule.Config;
import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.objects.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 07-Apr-2015.
 */

//Adapter to contain the schedule's content
//Takes the schedule objects and lays them out
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

    //One list item containing the relevant information
    private class ViewHolder {
        TextView schedule_date;
        TextView schedule_hours;
        TextView schedule_local;
        TextView schedule_prof;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        //If the view does not have associated information
        //Initialise the views for treatment
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_schedule, null);

            holder = new ViewHolder();
            holder.schedule_date = (TextView) convertView.findViewById(R.id.schedule_date);
            holder.schedule_hours = (TextView) convertView.findViewById(R.id.schedule_hours);
            holder.schedule_local = (TextView) convertView.findViewById(R.id.schedule_local);
            holder.schedule_prof = (TextView) convertView.findViewById(R.id.schedule_prof);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Toast.makeText(getContext(), "ACTION DOWN detected", Toast.LENGTH_LONG).show();
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(getContext(), "ACTION UP detected", Toast.LENGTH_LONG).show();
                        return false;
                    default:
                        break;
                }
                return false;
            }


        });

        //For this position
        Schedule schedule = scheduleList.get(position);

        String description = schedule.getDescription();


        //Set the content for the views from the schedule object
        holder.schedule_date.setText(
                Config.printDateTime(Config.PATTERN_FOR_PRINT_DATA, schedule.getStartDate()));

        holder.schedule_hours.setText(
                Config.printDateTime(Config.SCHEDULE_PATTERN_HOUR, schedule.getStartHour()) + " - " +
                        Config.printDateTime(Config.SCHEDULE_PATTERN_HOUR, schedule.getEndHour()));
        holder.schedule_local.setText(schedule.getLocation());


        //Set color for final exams
        if (description.equals("Examen final")) {
            convertView.setBackgroundResource(R.color.theme_accent_1_light);
            holder.schedule_prof.setText(R.string.schedule_final);
        }

        //Midterm color
        else if (description.equals("Examen intra")) {
            convertView.setBackgroundResource(R.color.theme_accent_2_light);
            holder.schedule_prof.setText(R.string.schedule_midterm);
        }
        else {
            convertView.setBackgroundResource(R.color.window_background);
            holder.schedule_prof.setText(schedule.getProfessor());

        }

        return convertView;

    }



}
