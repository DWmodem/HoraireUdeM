package com.mobile.umontreal.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static final String JSON_COURSE_STATUS_OPEN      = "Ouvert";
    public static final String JSON_COURSE_TYPE_THEORY      = "TH";


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
    public static final String PATTERN_FOR_PRINT_DATA       = "dd MMMM yyyy";

    public static final String SCHEDULE_PATTERN_DATE_TIME   = "yyyy-mm-dd HH:mm";
    public static final String SCHEDULE_PATTERN_DATE        = "yyyy-mm-dd";
    public static final String SCHEDULE_PATTERN_HOUR        = "HH:mm";
    public static final String SCHEDULE_PATTERN_DAY         = "E";

    public static final Locale TIME_LOCALE_FR               = new Locale("fr", "FR");
    public static final Locale TIME_LOCALE_EN               = new Locale("en", "US");

    public static Date parsingDate(String date, String format) throws ParseException {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat(format, TIME_LOCALE_FR);
//        return dateFormat.parse(date);

        SimpleDateFormat originalFormat = new SimpleDateFormat(format, TIME_LOCALE_FR);
        SimpleDateFormat targetFormat = new SimpleDateFormat(format, TIME_LOCALE_EN);
        Date dateOrignial = originalFormat.parse(date);
        String dateTarged = targetFormat.format(dateOrignial);
        return originalFormat.parse(dateTarged);

    }

    public String printDateTime (String pattern, Locale locale) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        return simpleDateFormat.format(new Date());

    }

    public static final long SECOND_MILLIS                  = 1000;
    public static final long MINUTE_MILLIS                  = 60 * SECOND_MILLIS;
    public static final long HOUR_MILLIS                    = 60 * MINUTE_MILLIS;
    public static final long DAY_MILLIS                     = 24 * HOUR_MILLIS;

    //Google login status
    public static boolean isLoggedIn                        = false;

    //Google login constants
    public static final int REQUEST_CODE_PICK_ACCOUNT       = 1000;
    public static final int RESULT_OK                       = -1;
    public static final int RESULT_CANCELED                 = 0;

    //User's google account email
    public static String userEmail;

    //Scope
    public static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

}
