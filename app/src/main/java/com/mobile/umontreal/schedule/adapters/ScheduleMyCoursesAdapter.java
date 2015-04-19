package com.mobile.umontreal.schedule.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.objects.MyCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 07-Apr-2015.
 */
public class ScheduleMyCoursesAdapter extends ArrayAdapter<MyCourse> {

    private List<MyCourse> courseList;
    private Activity activity;

    public ScheduleMyCoursesAdapter(Context context,
        Activity activity, int textViewResourceId,
        List<MyCourse> courseList) {
        super(context, textViewResourceId, courseList);
        this.courseList = new ArrayList<MyCourse>();
        this.courseList.addAll(courseList);
        this.activity = activity;
    }

    private class ViewHolder {
        TextView    sigle_cours;
        TextView    group;
        TextView    teacher;
        TextView    next_class;
        ImageButton button_delete_course;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_my_courses, null);

            holder = new ViewHolder();
            holder.sigle_cours  = (TextView) convertView.findViewById(R.id.sigle_cours);
            holder.group = (TextView) convertView.findViewById(R.id.group);
            holder.teacher = (TextView) convertView.findViewById(R.id.teacher);
            holder.next_class  = (TextView) convertView.findViewById(R.id.next_class);
            holder.button_delete_course = (ImageButton) convertView.findViewById(R.id.button_delete_course);
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyCourse course = courseList.get(position);

        holder.sigle_cours.setText(course.getSigle() + "-"
                + course.getCourseNumber() + " " + course.getTitle());

        holder.group.setText(getContext().getString(R.string.group) + " " + course.getSection());

        return convertView;

    }



}
