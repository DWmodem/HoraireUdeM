package com.mobile.umontreal.schedule.objects;

import com.mobile.umontreal.schedule.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Justin on 2015-03-12.
 */
public class CourseSectionSchedule {

    public static final String JSON_SECTION_TITLE = "section";

    public static final String JSON_COURSE_TITLE = "titre";
    public static final String JSON_COURSE_TYPE = "type";
    public static final String JSON_COURSE_STATUS = "status";
    public static final String JSON_COURSE_CREDITS = "credits";
    public static final String JSON_COURSE_DESCRIPTION = "description";

    public static final String JSON_COURSE_SCHEDULE = "horaire";

    private CourseSection section;
    private Date dateDebut;
    private Date dateFin;
    private String local;
    private CourseSectionScheduleType type;

    private List<CourseSectionSchedule> courseSectionScheduleList;

    public CourseSection getSection() {
        return section;
    }

    public void setSection(CourseSection section) {
        this.section = section;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public CourseSectionScheduleType getType() {
        return type;
    }

    public void setType(CourseSectionScheduleType type) {
        this.type = type;
    }

    public CourseSectionSchedule(){

    }

    public CourseSectionSchedule(JSONObject json) throws JSONException {

        courseSectionScheduleList = new ArrayList<CourseSectionSchedule>();

        // Getting JSON Array node
        JSONArray sections = json.getJSONArray(JSON_COURSE_SCHEDULE);
        SimpleDateFormat format = new SimpleDateFormat(Config.DATE_FORMAT_PARSING);

        try {

            // looping through All Contacts
            for (int i = 0; i < sections.length(); i++) {

                JSONObject c = sections.getJSONObject(i);

//                Date dateCancellation = format.parse(c.getString(JSON_SECTION_CANCELLATION));
//                Date dateDrop = format.parse(c.getString(JSON_SECTION_DROP));
//                Date dateDropLimit = format.parse(c.getString(JSON_SECTION_DROP_LIMIT));
//
//                CourseSectionSchedule courseShedule = new CourseSectionSchedule();
//
//                courseShedule.setSection(c.getString(JSON_SECTION_TITLE));
//                courseShedule.setType(c.getString(JSON_SECTION_TYPE));
//                courseShedule.setCancel(dateCancellation);
//                courseShedule.setDrop(dateDrop);
//                courseShedule.setDropLimit(dateDropLimit);
//                courseShedule.setDescription(c.getString(JSON_COURSE_DESCRIPTION));
//                courseSectionScheduleList.add(courseShedule);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString(){
        //TODO
        return super.toString();
    }
}
