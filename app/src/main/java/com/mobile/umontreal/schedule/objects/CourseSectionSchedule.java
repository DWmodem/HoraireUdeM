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

    private CourseSection section;
    private Date dateDebut;
    private Date dateFin;
    private String local;
    private String prof;

    private CourseSectionScheduleType type;

    private List<CourseSectionSchedule> schedule;

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

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public List<CourseSectionSchedule> getSchedule() {
        return schedule;
    }

    public CourseSectionSchedule(){

    }

    public CourseSectionSchedule(JSONObject json) throws JSONException {

        // Getting JSON data node
        String title                = json.getString(Config.JSON_COURSE_TITLE);
        int courseNum               = json.getInt(Config.JSON_COURSE_NUM);
        String status               = json.getString(Config.JSON_COURSE_STATUS);
        String credits              = json.getString(Config.JSON_COURSE_CREDITS);
        String sectionType          = json.getString(Config.JSON_SECTION_TYPE);
        String sessionType          = json.getString(Config.JSON_SESSION_TYPE);
        String dateCancel           = json.getString(Config.JSON_SECTION_CANCELLATION);
        String dateDropValue        = json.getString(Config.JSON_SECTION_DROP);
        String dateDropLimitValue   = json.getString(Config.JSON_SECTION_DROP_LIMIT);
        String description          = json.getString(Config.JSON_COURSE_DESCRIPTION);

        // Create a course field
        Course course = new Course();
        course.setTitle(title);
        course.setCourseNumber(courseNum);

        // Create a section field
        section = new CourseSection();
        section.setCourse(course);

        // Set course status
        if (status.equals(Config.JSON_COURSE_STATUS_OPEN)) {
            section.setStatus(CourseSectionStatus.Open);

        } else {
            section.setStatus(CourseSectionStatus.Closed);
        }

        // Set section type
        if (sectionType.equals(Config.JSON_COURSE_TYPE_THEORY)) {
            section.setSectionType(SectionType.Theory);
        }

        else {
            section.setSectionType(SectionType.Demo);
        }

        section.setCredit(Integer.valueOf(credits.charAt(0)));
        section.setType(sessionType);

        // Date fields
        SimpleDateFormat format = new SimpleDateFormat(Config.PARSING_DATE_FORMAT);

        try {
            section.setCancel(format.parse(dateCancel));
            section.setDrop(format.parse(dateDropValue));
            section.setDropLimit(format.parse(dateDropLimitValue));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set description
        section.setDescription(description);

        // Set section
        this.setSection(section);

        // Create a schedule fields
        JSONArray scheduleFields = json.getJSONArray(Config.JSON_COURSE_SCHEDULE);

        schedule = new ArrayList<CourseSectionSchedule>();

        // Date fields
        SimpleDateFormat scheduleDateFormat = new SimpleDateFormat(Config.SCHEDULE_DATE_FORMAT);

        // looping through All Contacts
        for (int i = 0; i < scheduleFields.length(); i++) {

            JSONObject c = scheduleFields.getJSONObject(i);

            try {

                String dateDebut            = json.getString(Config.JSON_SCHEDULE_DATE);
                String dateFin              = json.getString(Config.JSON_SCHEDULE_DATE);
                String day                  = json.getString(Config.JSON_SCHEDULE_DAY);
                String local                = json.getString(Config.JSON_SCHEDULE_LOCAL);
                String prof                 = json.getString(Config.JSON_SCHEDULE_PROF);
                String scheduleDescription  = json.getString(Config.JSON_SCHEDULE_DESCRIPTION);

                dateDebut.concat(json.getString(Config.JSON_SCHEDULE_STARTED_HOUR));
                dateFin.concat(json.getString(Config.JSON_SCHEDULE_FINISHED_HOUR));

                this.setDateDebut(scheduleDateFormat.parse(dateDebut));
                this.setDateFin(scheduleDateFormat.parse(dateFin));
                this.setLocal(local);
                this.setProf(prof);

            } catch (Exception e) {
                e.printStackTrace();
            }

            schedule.add(this);
        }

    }

    @Override
    public String toString(){
        //TODO
        return super.toString();
    }
}
