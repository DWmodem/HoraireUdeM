package com.mobile.umontreal.schedule.schedule;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;

import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.objects.MyCourse;

import java.util.ArrayList;

/**
 * Created by Corneliu on 28-Apr-2015.
 */
public class ScheduleCoursesFragment extends ListFragment {

    // Data Base
    private UDMDatabaseManager dbase;

    // Called when the activity is first created.
    private ArrayList<MyCourse> scheduleList;

      @Override
    public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          // Connection DataBase
          dbase = new UDMDatabaseManager(getActivity());
          scheduleList = new ArrayList<MyCourse>();

          String sessionName = getArguments().getString("page");

          if (sessionName != null) {

              Cursor c = dbase.getCourses(new String[]{
                      UDMDatabaseManager.C_ID,
                      UDMDatabaseManager.C_SIGLE,
                      UDMDatabaseManager.C_COURSNUM,
                      UDMDatabaseManager.C_SECTION,
                      UDMDatabaseManager.C_TITRE,
                      UDMDatabaseManager.C_TRIMESTRE,
                      UDMDatabaseManager.C_STATUS,
                      UDMDatabaseManager.C_ABANDON
              },
              UDMDatabaseManager.C_TRIMESTRE + " LIKE ?", // The columns for the WHERE clause
              new String[]{sessionName},                  // The values for the WHERE clause
              null,                                       // Group the rows
              null,                                       // Filter by row groups
              UDMDatabaseManager.C_TITRE + " ASC",        // The sort order
              null);

              if (c != null && c.getCount() > 0) {

                  c.moveToFirst();

                  do {
                      // Add the course in the list
                      MyCourse course = new MyCourse();
                      course.set_id(c.getInt(c.getColumnIndex(UDMDatabaseManager.C_ID)));
                      course.setSigle(c.getString(c.getColumnIndex(UDMDatabaseManager.C_SIGLE)));
                      course.setCourseNumber(c.getInt(c.getColumnIndex(UDMDatabaseManager.C_COURSNUM)));
                      course.setSection(c.getString(c.getColumnIndex(UDMDatabaseManager.C_SECTION)));
                      course.setTitle(c.getString(c.getColumnIndex(UDMDatabaseManager.C_TITRE)));
                      course.setSession(c.getString(c.getColumnIndex(UDMDatabaseManager.C_TRIMESTRE)));
                      course.setStatus(c.getString(c.getColumnIndex(UDMDatabaseManager.C_STATUS)));
                      course.setDrop(c.getString(c.getColumnIndex(UDMDatabaseManager.C_ABANDON)));

                      scheduleList.add(course);

                  } while (c.moveToNext());
              }
              c.close();
          }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ScheduleCoursesAdapter(getActivity().getApplicationContext(),getActivity(),
                android.R.layout.simple_list_item_1, scheduleList));
    }
}