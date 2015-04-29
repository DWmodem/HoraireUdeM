package com.mobile.umontreal.schedule.navigation;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.misc.DataHolderClass;
import com.mobile.umontreal.schedule.misc.IterableCursor;

import java.util.ArrayList;
import java.util.List;

public class NavDrawerFragment extends Fragment implements DrawerCallbacks {

    private DrawerCallbacks mCallbacks;
    private RecyclerView mDrawerList;
    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private int mCurrentSelectedPosition;

    // Data Base
    private UDMDatabaseManager dataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerList = (RecyclerView) view.findViewById(R.id.drawerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDrawerList.setLayoutManager(layoutManager);
        mDrawerList.setHasFixedSize(true);
        final List<NavItem> navigationItems = getMenu();
        NavAdapter adapter = new NavAdapter(navigationItems);
        adapter.setNavigationDrawerCallbacks(this);
        mDrawerList.setAdapter(adapter);
        selectItem(mCurrentSelectedPosition);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection DataBase
        dataBase = new UDMDatabaseManager(getActivity());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (DrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }


    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) return;
                getActivity().invalidateOptionsMenu();
            }
        };


        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public List<NavItem> getMenu() {

        List<NavItem> items = new ArrayList<NavItem>();

        // Get the classes to display in the activity
        Cursor session = dataBase.getCourses(new String[]{
                UDMDatabaseManager.C_TITRE,
                UDMDatabaseManager.C_TRIMESTRE
            },
            null,                                   // The columns for the WHERE clause
            null,                                   // The values for the WHERE clause
            UDMDatabaseManager.C_TRIMESTRE,         // Group the rows
            null,                                   // Filter by row groups
            UDMDatabaseManager.C_ID + " DESC",      // The sort order
            null);                                  // The limit);

        // Build the menu
        if (session.getCount() > 0) {

            //For every session inscribed
            for (Cursor data : new IterableCursor(session)) {

                String sessionName = data.getString(data.getColumnIndex(UDMDatabaseManager.C_TRIMESTRE));
                Character c = sessionName.charAt(0);

                if (c == 'A') {
                    ShapeDrawable icon = new ShapeDrawable(new OvalShape());
                    icon.setIntrinsicHeight(48);
                    icon.setIntrinsicWidth(48);
                    icon.setBounds(0, 0, 48, 48);
                    icon.getPaint().setColor(getResources().getColor(R.color.autumn));
                    items.add(new NavItem(sessionName, icon, getResources().getString(R.string.A)));
                } else if (c == 'E') {
                    ShapeDrawable icon = new ShapeDrawable(new OvalShape());
                    icon.setIntrinsicHeight(48);
                    icon.setIntrinsicWidth(48);
                    icon.setBounds(0, 0, 48, 48);
                    icon.getPaint().setColor(getResources().getColor(R.color.summer));
                    items.add(new NavItem(sessionName, icon, getResources().getString(R.string.E)));
                } else {
                    ShapeDrawable icon = new ShapeDrawable(new OvalShape());
                    icon.setIntrinsicHeight(48);
                    icon.setIntrinsicWidth(48);
                    icon.setBounds(0, 0, 48, 48);
                    icon.getPaint().setColor(getResources().getColor(R.color.winter));
                    items.add(new NavItem(sessionName, icon, getResources().getString(R.string.H)));
                }
            }

            DataHolderClass.getInstance().setDistributor(items);
        }

        session.close();
        return items;
    }

    void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
        ((NavAdapter) mDrawerList.getAdapter()).selectPosition(position);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        selectItem(position);
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }
}
