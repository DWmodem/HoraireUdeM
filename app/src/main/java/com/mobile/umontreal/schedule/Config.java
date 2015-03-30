package com.mobile.umontreal.schedule;

/**
 * Created by Corneliu on 25-Mar-2015.
 * General configuration
 */
public class Config {

    // Application name
    public static final String APP_NAME = "HoraireUdeM";

    // Base URL for API
    public static final String URL_API_UDEM = "http://www-labs.iro.umontreal.ca/~roys/horaires/json/";

    // JSON Node names

    // http://www-labs.iro.umontreal.ca/~roys/horaires/json/sigles.json
    public static final String TAG_TRIMESTER        = "trimestre";
    public static final String TAG_SIGLE            = "sigle";
    public static final String TAG_COURSE_NUM       = "coursnum";

    // http://www-labs.iro.umontreal.ca/~roys/horaires/json/H15-ift.json
    public static final String TAG_COURSE_TITLE     = "titre";

    //http://www-labs.iro.umontreal.ca/~roys/horaires/json/H15-ift-2905.json
    public static final String TAG_SECTIONS         = "sections";
    public static final String TAG_SECTION          = "section";
    public static final String TAG_TYPE             = "type";
    public static final String TAG_ANNULATION       = "annulation";
    public static final String TAG_ABANDON          = "abandon";
    public static final String TAG_ABANDON_LIMIT    = "abandonlimite";
    public static final String TAG_DESCRIPTION      = "description";

    public static final String TAG_SESSION          = "trimestre";

    // Shorthand for some units of time
    public static final String DATE_FORMAT_PARSING  = "yyyy-mm-dd";
    public static final String DATE_FORMAT_OUT      = "dd MMMM yyyy";

    public static final long SECOND_MILLIS          = 1000;
    public static final long MINUTE_MILLIS          = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS            = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS             = 24 * HOUR_MILLIS;

    //Google login status
    public static boolean isLoggedIn                = false;

    //Google login constants
    public static final int REQUEST_CODE_PICK_ACCOUNT      = 1000;
    public static final int RESULT_OK                      = -1;
    public static final int RESULT_CANCELED                = 0;

    //User's google account email
    public static String userEmail;


}
