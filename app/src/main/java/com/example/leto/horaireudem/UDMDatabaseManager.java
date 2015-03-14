package com.example.leto.horaireudem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    static final String P_HEUREDEBUT = "heuredebut";
    static final String P_HEUREFIN = "heurefin";
    static final String P_LOCAL = "local";
    static final String P_PROF = "prof";
    static final String P_DESCRIPTION = "description";

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
}
