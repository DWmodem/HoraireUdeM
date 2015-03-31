package com.mobile.umontreal.schedule;

/**
 * Created by Corneliu on 25-Mar-2015.
 * General configuration
 */
public class Config {

    // Application name
    public static final String APP_NAME                     = "HoraireUdeM";

    // Base URL for API
    public static final String URL_API_UDEM = "http://www-labs.iro.umontreal.ca/~roys/horaires/json/";

    // JSON Node names

    // http://www-labs.iro.umontreal.ca/~roys/horaires/json/sigles.json
    public static final String JSON_SESSION                 = "trimestre";
    public static final String JSON_SIGLE                   = "sigle";

    public static final String JSON_COURSE_NUM              = "coursnum";
    public static final String JSON_COURSE_TYPE             = "type";
    public static final String JSON_COURSE_STATUS           = "status";
    public static final String JSON_COURSE_CREDITS          = "credits";
    public static final String JSON_COURSE_DESCRIPTION      = "description";

    public static final String JSON_COURSE_SCHEDULE         = "horaire";

    // http://www-labs.iro.umontreal.ca/~roys/horaires/json/H15-ift.json
    public static final String JSON_COURSE_TITLE            = "titre";

    //http://www-labs.iro.umontreal.ca/~roys/horaires/json/H15-ift-2905.json
    public static final String JSON_SECTIONS                = "sections";
    public static final String JSON_SECTION_TITLE           = "section";
    public static final String JSON_SECTION_TYPE            = "type";
    public static final String JSON_SECTION_CANCELLATION    = "annulation";
    public static final String JSON_SECTION_DROP            = "abandon";
    public static final String JSON_SECTION_DROP_LIMIT      = "abandonlimite";
    public static final String JSON_SECTION_DESCRIPTION     = "description";
    public static final String JSON_SESSION_TYPE            = "session";

    // http://www-labs.iro.umontreal.ca/~roys/horaires/json/A14-ift-1015-B02.json
    public static final String JSON_SCHEDULE_DATE           = "date";
    public static final String JSON_SCHEDULE_DAY            = "jour";
    public static final String JSON_SCHEDULE_STARTED_HOUR   = "heuredebut";
    public static final String JSON_SCHEDULE_FINISHED_HOUR  = "heurefin";
    public static final String JSON_SCHEDULE_LOCAL          = "local";
    public static final String JSON_SCHEDULE_PROF           = "prof";
    public static final String JSON_SCHEDULE_DESCRIPTION    = "description";

    // Shorthand for some units of time
    public static final String PARSING_DATE_FORMAT          = "yyyy-mm-dd";
    public static final String PRINT_DATE_FORMAT            = "dd MMMM yyyy";
    public static final String SCHEDULE_DATE_FORMAT         = "yyyy-mm-dd HH:mm";


    public static final long SECOND_MILLIS                  = 1000;
    public static final long MINUTE_MILLIS                  = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS                    = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS                     = 24 * HOUR_MILLIS;

    //Google login status
    public static boolean isLoggedIn                        = false;

    //Google login constants
    public static final int REQUEST_CODE_PICK_ACCOUNT      = 1000;
    public static final int RESULT_OK                      = -1;
    public static final int RESULT_CANCELED                = 0;

    //User's google account email
    public static String userEmail;




}
