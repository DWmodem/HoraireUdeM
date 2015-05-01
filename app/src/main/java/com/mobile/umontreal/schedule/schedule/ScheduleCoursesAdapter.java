package com.mobile.umontreal.schedule.schedule;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.umontreal.schedule.Config;
import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.objects.MyCourse;

import java.util.ArrayList;
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
        TextView    end_class;
        LinearLayout editToolbar;
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
            holder.next_class  = (TextView) convertView.findViewById(R.id.start_class);
            holder.end_class  = (TextView) convertView.findViewById(R.id.end_class);
            holder.editToolbar = (LinearLayout) convertView.findViewById(R.id.toolbar_edit);
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

        UDMDatabaseManager dbHelper = new UDMDatabaseManager(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(UDMDatabaseManager.TABLE_PERIODECOURS,
                new String[]{UDMDatabaseManager.P_DATE, UDMDatabaseManager.P_HEUREDEBUT},
                UDMDatabaseManager.P_COURSNUM + " LIKE ? AND " +
                UDMDatabaseManager.P_SESSION + " LIKE ? ",  // The columns for the WHERE clause
                new String[]{ ""+course.getCourseNumber(),
                course.getSession()},                       // The values for the WHERE clause
                null,                                       // Group the rows
                null,                                       // Filter by row groups
                UDMDatabaseManager.P_DATE + " ASC",         // The sort order
                "1");                                       // Limit

        cursor.moveToFirst();
        String startedDate;
        String startedHour;

        while (cursor.isAfterLast() == false) {

            // Get date of current row
            startedDate = Config.printDateTime(
                    Config.SCHEDULE_PATTERN_TIME_S, new java.util.Date(cursor.getLong(0)));
            startedHour = Config.printDateTime(
                    Config.SCHEDULE_PATTERN_HOUR, new java.util.Date(cursor.getLong(1)));

            // Display in a Holder
            holder.next_class.setText(
                    getContext().getString(R.string.class_started_at)+ " " + startedHour + " " + startedDate);

            // Move to next row
            cursor.moveToNext();
        }
        cursor.close();

        Cursor max = db.query(UDMDatabaseManager.TABLE_PERIODECOURS,
                new String[]{UDMDatabaseManager.P_DATE, UDMDatabaseManager.P_HEUREFIN},
                UDMDatabaseManager.P_COURSNUM + " LIKE ? AND " +
                UDMDatabaseManager.P_SESSION + " LIKE ? ",  // The columns for the WHERE clause
                new String[]{ ""+course.getCourseNumber(),
                course.getSession()},                       // The values for the WHERE clause
                null,                                       // Group the rows
                null,                                       // Filter by row groups
                UDMDatabaseManager.P_DATE + " DESC",        // The sort order
                "1");                                       // Limit

        max.moveToFirst();
        String endedDate;
        String endedHour;

        while (max.isAfterLast() == false) {

            // Get date of current row
            endedDate = Config.printDateTime(
                    Config.SCHEDULE_PATTERN_TIME_S, new java.util.Date(max.getLong(0)));
            endedHour = Config.printDateTime(
                    Config.SCHEDULE_PATTERN_HOUR, new java.util.Date(max.getLong(1)));

            // Display in a Holder
            holder.end_class.setText(
                    getContext().getString(R.string.class_ended_at) + " " + endedHour + " " + endedDate);

            // Move to next row
            max.moveToNext();
        }
        max.close();
        return convertView;
    }

    public void ToastMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
