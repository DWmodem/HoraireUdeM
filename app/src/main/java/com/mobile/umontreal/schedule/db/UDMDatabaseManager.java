package com.mobile.umontreal.schedule.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobile.umontreal.schedule.Config;
import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;
import com.mobile.umontreal.schedule.objects.Schedule;

import java.util.HashMap;

/**
 * Created by Philippe on 13/03/2015.
 */
public class UDMDatabaseManager extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "UdeMcoursesDatabase";
    static final int DATABASE_VERSION = 2;

    //Schema
    //DEPARTEMENT;              Un département de l'UDM (ex: IFT - Informatique)
    public static final String TABLE_DEPARTEMENT = "DEPARTEMENT";
    public static final String D_SIGLE = "_sigle";       //Primary key
    public static final String D_TITRE = "titre";
    public static final String D_NBCOURS = "nbcours";

    //COURS;                    Un cours de l'UDM (ex: IFT2905, section A, Trimestre H15)
    public static final String TABLE_COURS = "COURS";
    public static final String C_ID = "_id";
    public static final String C_SIGLE = "_sigle";
    public static final String C_COURSNUM = "_coursnum";
    public static final String C_SECTION = "_section";
    public static final String C_TYPE = "type";
    public static final String C_TITRE = "titre";
    public static final String C_TRIMESTRE = "trimestre";
    public static final String C_STATUS = "status";
    public static final String C_SESSION = "session";
    public static final String C_CREDITS = "credits";
    public static final String C_ANNULATION = "annulation";
    public static final String C_ABANDON = "abandon";
    public static final String C_DESCRIPTION = "description";

    //PERIODECOURS
    static final String TABLE_PERIODECOURS = "PERIODECOURS";
    public static final String P_ID = "_pid";
    public static final String P_DATE = "date";
    public static final String P_JOUR = "jour";
    public static final String P_SIGLE = "_sigle";
    public static final String P_COURSNUM = "_coursnum";
    public static final String P_SECTION = "_section";
    public static final String P_TYPE = "_type";
    public static final String P_HEUREDEBUT = "heuredebut";
    public static final String P_HEUREFIN = "heurefin";
    public static final String P_LOCAL = "local";
    public static final String P_PROF = "prof";
    public static final String P_DESCRIPTION = "description";



    //The unique IDs for the database to manage
    public static int periodeCoursID;
    public static int coursID;
    public static int departmentID;

    public UDMDatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String departementTableCreationQuery = "CREATE TABLE "+TABLE_DEPARTEMENT+" ("
                +D_SIGLE+" text PRIMARY KEY, "
                +D_TITRE+" text, "
                +D_NBCOURS+" integer);";

        String coursTableCreationQuery = "CREATE TABLE "+TABLE_COURS+" ("
                +C_ID+" integer PRIMARY KEY AUTOINCREMENT,"
                +C_SIGLE+" text, "
                +C_COURSNUM+" integer, "
                +C_SECTION+" text, "
                +C_TYPE+" text, "
                +C_TITRE+" text, "
                +C_TRIMESTRE+" text, "
                +C_STATUS+" text, "
                +C_SESSION+" text, "
                +C_CREDITS+" text, "
                +C_ANNULATION+" text, "
                +C_ABANDON+" text, "
                +C_DESCRIPTION+" text"+" );";

        //Sigle, coursnum, section, type sert a rattacher la periode de cours a son cours
        String periodecoursTableCreationQuery = "CREATE TABLE "+TABLE_PERIODECOURS+" ("
                +P_ID+" integer, "
                +P_DATE+" text, "
                +P_JOUR+" text, "
                +P_SIGLE + " text, "
                +P_COURSNUM +" text, "
                +P_SECTION+" text, "
                +P_TYPE+" text, "
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

    // Adding new Course
    public void addCourse(CourseSectionSchedule course, SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        values.put(this.C_SIGLE,
                course.getCourseSection().getCourse().getDepartment().getSigle());
        values.put(this.C_COURSNUM,
                course.getCourseSection().getCourse().getCourseNumber());
        values.put(this.C_SECTION,
                course.getCourseSection().getSection());
        values.put(this.C_TYPE,
                course.getCourseSection().getSectionType().toString());
        values.put(this.C_TITRE,
                course.getCourseSection().getCourse().getTitle());
        values.put(this.C_TRIMESTRE, course.getSessionPeriod());
        values.put(this.C_STATUS,
                course.getCourseSection().getStatus().toString());
        values.put(this.C_SESSION,
                course.getCourseSection().getType());
        values.put(this.C_CREDITS,
                course.getCourseSection().getCredit());
        values.put(this.C_ANNULATION,
                Config.printDateDefault(course.getCourseSection().getCancel()));
        values.put(this.C_ABANDON,
                Config.printDateDefault(course.getCourseSection().getDrop()));
        values.put(this.C_DESCRIPTION, course.getCourseSection().getDescription());

        // Inserting Row
        db.insert(TABLE_COURS, null, values);
        db.close(); // Closing database connection
    }

    //Remove a course from the database. Also remove the course periods associated to that course.
    public void removeCourse(CourseSectionSchedule course, SQLiteDatabase db){

        //Delete from TABLE_COURS WHERE COURSE...
        String query = "DELETE FROM " +UDMDatabaseManager.TABLE_COURS+  " WHERE "+
                                                                        UDMDatabaseManager.C_SIGLE+" = " +course.getSigle()+
                                                                        " AND "+
                                                                        UDMDatabaseManager.C_COURSNUM+" = "+course.getCoursnum()+
                                                                        " AND "+
                                                                        UDMDatabaseManager.C_SECTION+" = "+course.getCSection()+
                                                                        " AND "+
                                                                        UDMDatabaseManager.C_TRIMESTRE+" = "+course.getSessionPeriod();

        //Delete from TABLE_COURSEPERIOD WHERE COURSEPERIOD.SIGLE_COURSNUM_SECTION_TRIMESTRE

    }

    public Cursor getCourse(String title, String courseNumber, SQLiteDatabase db){

        Cursor result = db.rawQuery(
                "SELECT * FROM " + TABLE_COURS + " WHERE " +
                C_TITRE + " LIKE ?" + " AND " +
                C_COURSNUM + " =?",
                new String[] {title, courseNumber});
        return result;
    }


    public Cursor getCourses(
                String[] columns,
                String selection,
                String[] selectionArgs,
                String groupBy,
                String having,
                String orderBy,
                String limit,
                SQLiteDatabase db){

        Cursor cursor = db.query(
                TABLE_COURS,              // The table to query
                columns,                // The columns to return
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                groupBy,                // Group the rows
                having,                 // Filter by row groups
                orderBy,                // The sort order
                limit                   // The limit

        );

        return cursor;
    }

    public void insertData(HashMap<String, String> queryValues, String tableName, SQLiteDatabase db) {

        ContentValues contentValues = new ContentValues();

        for (HashMap.Entry<String, String> entry : queryValues.entrySet())
        {
            contentValues.put(entry.getKey(), entry.getValue());
        }

        db.insert(tableName, null, contentValues);
        db.close();
    }

    public Cursor getRow(int id, String tableName, SQLiteDatabase db){

        Cursor result = db.rawQuery( "SELECT * FROM " + tableName + " WHERE "
                        + tableName.substring(6,1)+"_ID = ? ",
                        new String[] { Integer.toString(id) });
        return result;
    }





    public boolean isEmpty(SQLiteDatabase db){

        Cursor cursor =  db.rawQuery("SELECT * FROM " + TABLE_COURS, null);
        return cursor.getCount() == 0 ? true : false;
    }

    public boolean updateData (Integer id, HashMap<String, String> queryValues, String tableName, SQLiteDatabase db)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for (HashMap.Entry<String, String> entry : queryValues.entrySet()) {
            contentValues.put(entry.getKey(), entry.getValue());
        }

        db.update(tableName, contentValues,
                tableName.substring(6,1)+"_ID= ? ",
                new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteData (Integer id, String tableName, SQLiteDatabase db)
    {
        db = this.getWritableDatabase();
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

    public long addCoursePeriod(CourseSectionSchedule course, SQLiteDatabase db) {

        long err = 0;
        Schedule HoraireCours;
        for (int index = 0; index < course.getSchedule().size(); index++) {

            HoraireCours = course.getSchedule().get(index);
            int nextID = getCoursePeriodID(db) + 1;
            ContentValues cv = new ContentValues();
            cv.put(UDMDatabaseManager.P_ID, nextID);
            cv.put(UDMDatabaseManager.P_SIGLE, course.getSigle());
            cv.put(UDMDatabaseManager.P_COURSNUM, course.getCoursnum());
            cv.put(UDMDatabaseManager.P_TYPE, course.getCType());
            cv.put(UDMDatabaseManager.P_SECTION, course.getCSection());

            cv.put(UDMDatabaseManager.P_DATE, HoraireCours.getEndDate().toString());
            cv.put(UDMDatabaseManager.P_JOUR, HoraireCours.getDay());
            cv.put(UDMDatabaseManager.P_HEUREDEBUT, HoraireCours.getStartHour().toString());
            cv.put(UDMDatabaseManager.P_HEUREFIN, HoraireCours.getEndHour().toString());
            cv.put(UDMDatabaseManager.P_LOCAL, HoraireCours.getLocation());
            cv.put(UDMDatabaseManager.P_PROF, HoraireCours.getProfessor());
            cv.put(UDMDatabaseManager.P_DESCRIPTION, HoraireCours.getDescription());

            db.insert(UDMDatabaseManager.TABLE_PERIODECOURS, null, cv);
        }

        return err;
    }
}
