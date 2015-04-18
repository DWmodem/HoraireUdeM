package com.mobile.umontreal.schedule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.misc.MenuHelper;

/**
 * Created by Luds on 2015-04-09./work in progress
 */
public class ScheduleActivity extends ActionBarActivity
        implements  AdapterView.OnItemClickListener  {


    UDMDatabaseManager dbh;
    SQLiteDatabase db;

    ListView listCours;
    //BDAdapter adapter;


    Cursor ListeCours;
    Cursor ListeHoraire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        //listCours= (ListView)findViewById(R.id.expandableListView);
        Log.d("Start horaire","Horaire est partie");
        //on charge la base de donner et on donne les droits d'écrire

        dbh = new UDMDatabaseManager(this);
        db = dbh.getWritableDatabase();
        Log.d("Start horaire","Donner les droit en écriture sur la bd");


        //Insert a row temp






        Log.d("Start horaire","Mettre a jour la bd");
        //start curseur to show the list of cours
        //ListeCours = UDMDatabaseManager.listeCours(db);

        Log.d("Start horaire","Pointeur créer");
        //ListeHoraire= UDMDatabaseManager.listeHoraire(db);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Common create options menu code is in MenuHelper
        MenuInflater inflater = getMenuInflater();
        MenuHelper.onCreateOptionsMenu(menu, inflater);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        //Common prepare options menu code is in MenuHelper
        MenuHelper.onPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return MenuHelper.onOptionsItemSelected(getApplicationContext(), item, this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


 /*   public class BDAdapter extends CursorAdapter {
        LayoutInflater inflater;
        public BDAdapter (Context context,Cursor c){
            super(context,c,true);
            inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public View getView(int position, View convertView,ViewGroup parent){
            View v= convertView;
            if(v== null){
                v=inflater.inflate(R.layout.schedule_inflater_layout,parent,false);
            }
            TextView SigleCours= (TextView)v.findViewById(R.id.SigleCourse);
            TextView Group = (TextView)v.findViewById(R.id.Group);
            TextView Teacher = (TextView)v.findViewById(R.id.Teacher);
            TextView NextClass= (TextView)v.findViewById(R.id.NextClass);
            Cursor c=getCursor();
            c.moveToPosition(position);
            int id = c.getInt(c.getColumnIndex(UDMDatabaseManager.CI_ID));
            // création du titre complet du cour soit  ex:ift1025 programmation 1.
            String InfoCours = c.getString(c.getColumnIndex(UDMDatabaseManager.CI_SIGLE))
                    +c.getString(c.getColumnIndex(UDMDatabaseManager.CI_COURSNUM))+" "
                    +c.getString(c.getColumnIndex(UDMDatabaseManager.CI_TITRE));
            SigleCours.setText(InfoCours);
            Group.setText(c.getString(c.getColumnIndex(UDMDatabaseManager.CI_SECTION)));
            Teacher.setText (c.getString(c.getColumnIndex(UDMDatabaseManager.CI_PROF)));
            return v;
                   }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return null;
        }
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
        }
    }*/

}