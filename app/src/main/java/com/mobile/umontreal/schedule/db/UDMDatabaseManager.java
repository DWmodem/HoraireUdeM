package com.mobile.umontreal.schedule.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobile.umontreal.schedule.objects.Course;
import com.mobile.umontreal.schedule.objects.CourseSection;
import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;
import com.mobile.umontreal.schedule.objects.Schedule;

import java.util.HashMap;

/**
 * Created by Philippe on 13/03/2015.
 */
public class UDMDatabaseManager extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "UdeMcoursesDatabase";
    static final int DATABASE_VERSION = 1;

    //Schema
    //DEPARTEMENT;              Un département de l'UDM (ex: IFT - Informatique)
    static final String TABLE_DEPARTEMENT = "DEPARTEMENT";
    static final String DEP_SIGLE = "_sigle";       //Primary key
    static final String DEP_TITRE = "titre";
    static final String DEP_NBCOURS = "nbcours";

    //COURS;                    Un cours de l'UDM (ex: IFT2905, section A, Trimestre H15)
    static final String TABLE_COURS = "COURS";
    static final String C_ID = "_id";
    static final String C_SIGLE = "_sigle";
    static final String C_COURSNUM = "_coursnum";
    static final String C_SECTION = "_section";
    static final String C_TYPE = "type";
    static final String C_TITRE = "titre";
    static final String C_TRIMESTRE = "trimestre";
    static final String C_STATUS = "status";
    static final String C_SESSION = "session";
    static final String C_CREDITS = "credits";
    static final String C_ANNULATION = "annulation";
    static final String C_ABANDON = "abandon";
    static final String C_DESCRIPTION = "description";

    //PERIODECOURS
    static final String TABLE_PERIODECOURS = "PERIODECOURS";
    static final String P_ID = "_pid";
    static final String P_DATE = "date";
    static final String P_JOUR = "jour";
    static final String P_SIGLE = "_sigle";
    static final String P_COURSNUM = "_coursnum";
    static final String P_HEUREDEBUT = "heuredebut";
    static final String P_HEUREFIN = "heurefin";
    static final String P_LOCAL = "local";
    static final String P_PROF = "prof";
    static final String P_DESCRIPTION = "description";



    //The unique IDs for the database to manage
    static int periodeCoursID;
    static int coursID;
    static int departmentID;

    public UDMDatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String departementTableCreationQuery = "CREATE TABLE "+TABLE_DEPARTEMENT+" ("
                +DEP_SIGLE+" text PRIMARY KEY, "
                +DEP_TITRE+" text, "
                +DEP_NBCOURS+" integer);";

        String coursTableCreationQuery = "CREATE TABLE "+TABLE_COURS+" ("
                +C_ID+" integer PRIMARY KEY AUTOINCREMENT,"
                +C_SIGLE+" text, "
                +C_COURSNUM+" integer, "
                +C_SECTION+" char, "
                +C_TYPE+" text, "
                +C_TITRE+" text, "
                +C_TRIMESTRE+" text, "
                +C_STATUS+" text, "
                +C_SESSION+" text, "
                +C_CREDITS+" text, "
                +C_ANNULATION+" text, "
                +C_ABANDON+" text, "
                +C_DESCRIPTION+" text"+" );";

        String periodecoursTableCreationQuery = "CREATE TABLE "+TABLE_PERIODECOURS+" ("
                +P_ID+" integer, "
                +P_DATE+" text, "
                +P_JOUR+" text, "
                +P_SIGLE + " text, "
                +P_COURSNUM +" text, "
                +P_HEUREDEBUT+" text, "
                +P_HEUREFIN+" text, "
                +P_LOCAL+" text, "
                +P_PROF+" text, "
                +P_DESCRIPTION+" text, "
                +"PRIMARY KEY("+P_ID+", "+P_DATE+"));";


        db.execSQL(departementTableCreationQuery);
        db.execSQL(coursTableCreationQuery);
        db.execSQL(periodecoursTableCreationQuery);

        Log.d("DB", "DB created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_DEPARTEMENT);
        db.execSQL("drop table if exists "+TABLE_COURS);
        db.execSQL("drop table if exists "+TABLE_PERIODECOURS);
        onCreate(db);
    }

    public void insertData(HashMap<String, String> queryValues, String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (HashMap.Entry<String, String> entry : queryValues.entrySet())
        {
            contentValues.put(entry.getKey(), entry.getValue());
        }

        db.insert(tableName, null, contentValues);
        db.close();
    }

    public Cursor getData(int id, String tableName){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + tableName + " WHERE "
                        + tableName.substring(6,1)+"_ID = ? ",
                        new String[] { Integer.toString(id) });
        return res;
    }

    public boolean updateData (Integer id, HashMap<String, String> queryValues, String tableName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (HashMap.Entry<String, String> entry : queryValues.entrySet()) {
            contentValues.put(entry.getKey(), entry.getValue());
        }

        db.update(tableName, contentValues,
                tableName.substring(6,1)+"_ID= ? ",
                new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteData (Integer id, String tableName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,
                tableName.substring(6,1)+"_ID = ? ",
                new String[] { Integer.toString(id) });
    }

    //Returns the next period ID needed into the database
    public int getCoursePeriodID(SQLiteDatabase db){

        String[] columns = {UDMDatabaseManager.P_ID};
        Cursor cursor = db.query(UDMDatabaseManager.TABLE_PERIODECOURS, columns, null, null, null, null, null);
        int pcID = 0;
        while(cursor.moveToNext()){

            int IDcolumnIndex = cursor.getColumnIndex(UDMDatabaseManager.P_ID);

            //Get the ID of this row
            int currentID = cursor.getInt(IDcolumnIndex);

            //Keep this value if it is the max
            if(currentID >= pcID){
                pcID = currentID;
            }
        }
        return pcID;
    }


    public long addCoursePeriod(CourseSectionSchedule course, SQLiteDatabase db)
    {
        long err =0;
        Schedule HoraireCours ;
        for (int index =0 ; index< course.getSchedule().size() ;index ++)
        {
           HoraireCours= course.getSchedule().get(index);
            int nextID = getCoursePeriodID(db)+1;
            ContentValues cv = new ContentValues();
            cv.put(UDMDatabaseManager.P_ID, nextID);
            cv.put(UDMDatabaseManager.P_DATE, HoraireCours.getEndDate().toString());  //Course.getDate()
            cv.put(UDMDatabaseManager.P_JOUR, HoraireCours.getDay());
            cv.put(UDMDatabaseManager.P_HEUREDEBUT, HoraireCours.getStartHour().toString());
            cv.put(UDMDatabaseManager.P_HEUREFIN, HoraireCours.getEndHour().toString());
            cv.put(UDMDatabaseManager.P_LOCAL, HoraireCours.getLocation());
            cv.put(UDMDatabaseManager.P_PROF, HoraireCours.getProfessor());
            cv.put(UDMDatabaseManager.P_DESCRIPTION, HoraireCours.getDescription());

            db.insert(UDMDatabaseManager.TABLE_PERIODECOURS,null,cv);


        }

        return err;




 /*       +P_ID+" integer, "
                +P_DATE+" text, "
                +P_JOUR+" text, "
                +P_HEUREDEBUT+" text, "
                +P_HEUREFIN+" text, "
                +P_LOCAL+" text, "
                +P_PROF+" text, "
                +P_DESCRIPTION+" text, " */

    }

}
