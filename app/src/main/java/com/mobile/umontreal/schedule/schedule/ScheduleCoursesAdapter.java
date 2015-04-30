package com.mobile.umontreal.schedule.schedule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.objects.MyCourse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Corneliu on 07-Apr-2015.
 */
public class ScheduleCoursesAdapter extends ArrayAdapter<MyCourse> {

    private List<MyCourse> courseList;
    private Activity activity;

    private AdapterView.OnItemSelectedListener listener;

    public ScheduleCoursesAdapter(Context context,
                                  Activity activity, int textViewResourceId,
                                  List<MyCourse> givenCourses) {
        super(context, textViewResourceId, givenCourses);
        this.courseList = new ArrayList<MyCourse>();
        this.courseList.addAll(givenCourses);
        this.activity = activity;

    }

    private class ViewHolder {
        TextView    sigle_cours;
        TextView    group;
        TextView    teacher;
        TextView    next_class;
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
            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyCourse course = courseList.get(position);

        holder.sigle_cours.setText(course.getSigle() + "-"
                + course.getCourseNumber() + " " + course.getTitle());

        holder.group.setText(getContext().getString(R.string.group) + " " + course.getSection());

        holder.teacher.setText(getContext().getString(R.string.teacher) + " " + course.getProfessor());

//        UDMDatabaseManager db = new UDMDatabaseManager(getContext());
//
//        Cursor cursor = db.getNextClass(
//            new String[]{
//                    Config.printDateDefault(new Date()),
//                    Integer.toString(course.getCourseNumber()),
//                    course.getSession()
//            });
//
//        if (cursor != null) {
//
//            String next = "" + cursor.getString(0);
//            holder.next_class.setText(next);
//        }
//
//        cursor.close();
//        db.close();

        return convertView;
    }

    private static long compareTo( Date date1, Date date2 ) {
        return date1.getTime() - date2.getTime();
    }

}
