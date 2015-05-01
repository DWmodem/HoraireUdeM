package com.mobile.umontreal.schedule.schedule;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.umontreal.schedule.Config;
import com.mobile.umontreal.schedule.NavigationActivity;
import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.navigation.MyDialogFragment;
import com.mobile.umontreal.schedule.objects.MyCourse;

import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Corneliu on 28-Apr-2015.
 */
public class ScheduleCoursesFragment extends ListFragment
        implements MyDialogFragment.Communicator, AdapterView.OnItemClickListener, View.OnFocusChangeListener{

    // Data Base
    private UDMDatabaseManager db;

    private int item_position_clicked;

    // Called when the activity is first created.
    private ArrayList<MyCourse> scheduleList;

    private ScheduleCoursesAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);

          // Connection DataBase
          db = new UDMDatabaseManager(getActivity());
          scheduleList = new ArrayList<MyCourse>();

          String sessionName = getArguments().getString(Config.SCHEDULE_KEY_PAGE);

          if (sessionName != null) {

              Cursor c = db.getCourses(new String[]{
                      UDMDatabaseManager.C_ID,
                      UDMDatabaseManager.C_SIGLE,
                      UDMDatabaseManager.C_COURSNUM,
                      UDMDatabaseManager.C_SECTION,
                      UDMDatabaseManager.C_TITRE,
                      UDMDatabaseManager.C_TRIMESTRE,
                      UDMDatabaseManager.C_PROFESSOR,
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
                      course.setProfessor(c.getString(c.getColumnIndex(UDMDatabaseManager.C_PROFESSOR)));
                      course.setDrop(c.getString(c.getColumnIndex(UDMDatabaseManager.C_ABANDON)));

                      scheduleList.add(course);

                  } while (c.moveToNext());
              }
              c.close();
          }
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Adapter instance
        listAdapter = new ScheduleCoursesAdapter(getActivity().getApplicationContext(),getActivity(),
                        android.R.layout.simple_list_item_1, scheduleList);

        setListAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        getListView().setOnItemClickListener(this);

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                ToastMsg("onLongPress detected");
            }

            @Override
            public void onShowPress(MotionEvent e) {
                ToastMsg("onShowPress detected");
                super.onShowPress(e);
            }

            @Override
            public boolean onDown(MotionEvent e) {
                ToastMsg("onDown detected");
                return super.onDown(e);
            }
        });


    }

    @Override
    public void message(int data) {
        switch (data) {
            case 0:

                // Show Item Position
                Toast.makeText(getActivity(), "Item " + item_position_clicked + " was clicked",
                        Toast.LENGTH_LONG).show();
                break;
            case 1:
                // Show Item Name
                Toast.makeText(getActivity(), "Name " + scheduleList.get(item_position_clicked).getTitle(),
                        Toast.LENGTH_LONG).show();
                break;

            case 2:
                // Delete Item
                ToastMsg("deleted item...");
                break;
        }
    }


    public void deleteItem (final int position) {
        final String item_name = scheduleList.get(item_position_clicked).getTitle();
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete " + item_name + "?");

        // Setting Positive "Yes" Button
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        listAdapter.remove(scheduleList.get(position));
                        listAdapter.notifyDataSetChanged();
                        ToastMsg(item_name + " was deleted");

                    }
                });

        // Setting Negative "Cancel" Button
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void ToastMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        item_position_clicked = position;
        LinearLayout edit = (LinearLayout) view.findViewById(R.id.toolbar_edit);
        edit.setVisibility(edit.VISIBLE);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        v.setVisibility(v.GONE);
    }


}