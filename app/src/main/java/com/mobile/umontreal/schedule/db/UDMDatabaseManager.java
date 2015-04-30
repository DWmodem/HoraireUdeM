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

    static final String DATABASE_NAME = "UDEM_DB_COURSES";
    static final int DATABASE_VERSION = 3;

    //Schema

    //DEPARTMENT
    public static final String TABLE_DEPARTMENT = "DEPARTMENTS";
    public static final String D_SIGLE = "_sigle";
    public static final String D_TITRE = "titre";
    public static final String D_NBCOURS = "nbcours";

    //COURSE
    public static final String TABLE_COURS = "COURSES";
    public static final String C_ID = "_id";
    public static final String C_SIGLE = "_sigle";
    public static final String C_COURSNUM = "_coursnum";
    public static final String C_SECTION = "_section";
    public static final String C_TYPE = "type";
    public static final String C_TITRE = "titre";
    public static final String C_TRIMESTRE = "trimestre";
    public static final String C_PROFESSOR = "prof";
    public static final String C_SESSION = "session";
    public static final String C_CREDITS = "credits";
    public static final String C_ANNULATION = "annulation";
    public static final String C_ABANDON = "abandon";
    public static final String C_DESCRIPTION = "description";

    //PERIOD COURSE
    public static final String TABLE_PERIODECOURS = "PERIOD_COURSES";
    public static final String P_ID = "_id";
    public static final String P_DATE = "date";
    public static final String P_JOUR = "jour";
    public static final String P_SIGLE = "_sigle";
    public static final String P_COURSNUM = "_coursnum";
    public static final String P_SECTION = "_section";
    public static final String P_SESSION = "_session";
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

        String departementTableCreationQuery = "CREATE TABLE "+ TABLE_DEPARTMENT +" ("
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
                + C_PROFESSOR +" text, "
                +C_SESSION+" text, "
                +C_CREDITS+" text, "
                +C_ANNULATION+" text, "
                +C_ABANDON+" text, "
                +C_DESCRIPTION+" text"+" );";

        //Sigle, coursnum, section, type sert a rattacher la periode de cours a son cours
        String periodecoursTableCreationQuery = "CREATE TABLE "+TABLE_PERIODECOURS+" ("
                +P_ID+" integer, "
                +P_DATE+" INTEGER, "
                +P_JOUR+" text, "
                +P_SIGLE + " text, "
                +P_COURSNUM +" text, "
                +P_SECTION+" text, "
                +P_SESSION +" text, "
                +P_HEUREDEBUT+" INTEGER, "
                +P_HEUREFIN+" INTEGER, "
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

        db.execSQL("drop table if exists "+ TABLE_DEPARTMENT);
        db.execSQL("drop table if exists "+TABLE_COURS);
        db.execSQL("drop table if exists "+TABLE_PERIODECOURS);
        onCreate(db);

    }

    // Adding new Course
    public void addCourse(CourseSectionSchedule course) {

        SQLiteDatabase db = this.getWritableDatabase();

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
        values.put(this.C_PROFESSOR, course.getProf());
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

    public Cursor getCourse(String title, String courseNumber){

        SQLiteDatabase db = this.getReadableDatabase();

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
                String limit){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_COURS,            // The table to query
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

    public Cursor getNextClass(String[] args) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + P_DATE + " FROM " + TABLE_PERIODECOURS +
                " WHERE " + P_DATE + " >= ? " +
                " AND   " + P_COURSNUM + " = ? " +
                " AND   " + P_SESSION  + " LIKE ? " +
                " ORDER BY " + P_DATE + " DESC", args);
        return c;
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

    public Cursor getRow(int id, String tableName){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery( "SELECT * FROM " + tableName + " WHERE "
                        + tableName.substring(6,1)+"_ID = ? ",
                        new String[] { Integer.toString(id) });
        return result;
    }

    public boolean isEmpty(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =  db.rawQuery("SELECT * FROM " + TABLE_COURS, null);
        return cursor.getCount() == 0 ? true : false;
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
    public int getCoursePeriodID(){

        SQLiteDatabase db = this.getReadableDatabase();

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

    public long addCoursePeriod(CourseSectionSchedule course) {

        SQLiteDatabase db = this.getWritableDatabase();

        long err = 0;
        Schedule HoraireCours;
        for (int index = 0; index < course.getSchedule().size(); index++) {

            HoraireCours = course.getSchedule().get(index);
            int nextID = getCoursePeriodID() + 1;
            ContentValues cv = new ContentValues();
            cv.put(UDMDatabaseManager.P_ID, nextID);
            cv.put(UDMDatabaseManager.P_SIGLE, course.getSigle());
            cv.put(UDMDatabaseManager.P_COURSNUM, course.getCoursnum());
            cv.put(UDMDatabaseManager.P_SECTION, course.getCSection());
            cv.put(UDMDatabaseManager.P_SESSION, course.getCType());
            cv.put(UDMDatabaseManager.P_DATE, HoraireCours.getStartDate().getTime());
            cv.put(UDMDatabaseManager.P_JOUR, HoraireCours.getDay());
            cv.put(UDMDatabaseManager.P_HEUREDEBUT, HoraireCours.getStartHour().getTime());
            cv.put(UDMDatabaseManager.P_HEUREFIN, HoraireCours.getEndHour().getTime());
            cv.put(UDMDatabaseManager.P_LOCAL, HoraireCours.getLocation());
            cv.put(UDMDatabaseManager.P_PROF, HoraireCours.getProfessor());
            cv.put(UDMDatabaseManager.P_DESCRIPTION, HoraireCours.getDescription());

            db.insert(UDMDatabaseManager.TABLE_PERIODECOURS, null, cv);
        }

        return err;
    }
}
