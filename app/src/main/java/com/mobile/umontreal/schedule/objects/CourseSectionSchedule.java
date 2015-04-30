package com.mobile.umontreal.schedule.objects;

import com.mobile.umontreal.schedule.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Justin on 2015-03-12.
 */
public class CourseSectionSchedule {

    private CourseSection section;

    //Pour fin d'identifier le cours auquel cette section appartient
    //Pour retrouver le nom du prof
    private String c_sigle;
    private String c_coursnum;
    private String c_type;
    private String c_section;
    private Date dateDebut;
    private Date dateFin;
    private String local;
    private String prof;

    private String sessionPeriod;

    public String getSessionPeriod() {
        return sessionPeriod;
    }

    public void setSessionPeriod(String sessionPeriod) {
        this.sessionPeriod = sessionPeriod;
    }

    private CourseSectionScheduleType type;

    private List<Schedule> scheduleList;

    public void setSection(CourseSection section) {
        this.section = section;
    }

    public CourseSection getCourseSection() {
        return section;
    }

    public String getSigle(){return c_sigle;}

    public void setSigle(String newSigle){this.c_sigle = newSigle;}

    public String getCoursnum(){return c_coursnum;}

    public void setCoursnum(String newnum){this.c_coursnum = newnum;}

    public String getCType(){return c_type;}

    public void setCType(String type){this.c_type = type;}

    public String getCSection(){return c_section;}

    public void setCSection(String section){this.c_section= section;}

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

    public List<Schedule> getSchedule() {
        return scheduleList;
    }

    public CourseSectionSchedule(){

    }

    public CourseSectionSchedule(JSONObject json) throws JSONException {

        // Getting JSON data node
        String title                = json.getString(Config.JSON_COURSE_TITLE);
        String sectionTitle         = json.getString(Config.JSON_SECTION_TITLE);
        String acronym              = json.getString(Config.JSON_SIGLE);
        int courseNum               = json.getInt(Config.JSON_COURSE_NUM);

        String status               = json.getString(Config.JSON_COURSE_STATUS);
        String credits              = json.getString(Config.JSON_COURSE_CREDITS);
        String sectionType          = json.getString(Config.JSON_SECTION_TYPE);
        String sessionType          = json.getString(Config.JSON_SESSION_TYPE);
        String dateCancel           = json.getString(Config.JSON_SECTION_CANCELLATION);
        String dateDropValue        = json.getString(Config.JSON_SECTION_DROP);
        String dateDropLimitValue   = json.getString(Config.JSON_SECTION_DROP_LIMIT);
        String description          = json.getString(Config.JSON_COURSE_DESCRIPTION);
        String GroupSection         = json.getString(Config.JSON_GROUP_SECTION);


        try {

            scheduleList = new ArrayList<Schedule>();

            // Create a department
            Department department = new Department();
            department.setSigle(acronym);

            // Create a course field
            Course course = new Course();
            course.setDepartment(department);
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

            section.setCredit(Integer.parseInt("" + credits.charAt(0)));
            section.setType(sessionType);

            section.setCancel(Config.parsingDate(dateCancel,
                    Config.SCHEDULE_PATTERN_DATE));

            section.setDrop(Config.parsingDate(dateDropValue,
                    Config.SCHEDULE_PATTERN_DATE));

            section.setDropLimit(Config.parsingDate(dateDropLimitValue,
                    Config.SCHEDULE_PATTERN_DATE));

            // Set description
            section.setDescription(description);
            section.setSection(sectionTitle);

            // set Group
            section.setSection(GroupSection);

            // Set section
            setSection(section);

            // Create a schedule fields
            JSONArray scheduleFields = json.getJSONArray(Config.JSON_COURSE_SCHEDULE);

            // looping through All Contacts
            for (int i = 0; i < scheduleFields.length(); i++) {

                JSONObject c = scheduleFields.getJSONObject(i);

                String startDate            = c.getString(Config.JSON_SCHEDULE_DATE);
                String startHour            = c.getString(Config.JSON_SCHEDULE_STARTED_HOUR);
                String endDate              = c.getString(Config.JSON_SCHEDULE_DATE);
                String endHour              = c.getString(Config.JSON_SCHEDULE_FINISHED_HOUR);
                String day                  = c.getString(Config.JSON_SCHEDULE_DAY);
                String local                = c.getString(Config.JSON_SCHEDULE_LOCAL);
                String prof                 = c.getString(Config.JSON_SCHEDULE_PROF);
                String scheduleDescription  = c.getString(Config.JSON_SCHEDULE_DESCRIPTION);

                Schedule schedule = new Schedule();

                schedule.setDay(day);

                schedule.setStartDate(Config.parsingDate(startDate,
                        Config.SCHEDULE_PATTERN_DATE));

                schedule.setStartHour(Config.parsingDate(startHour,
                        Config.SCHEDULE_PATTERN_HOUR));

                schedule.setEndDate(Config.parsingDate(endDate,
                        Config.SCHEDULE_PATTERN_DATE));

                schedule.setEndHour(Config.parsingDate(endHour,
                        Config.SCHEDULE_PATTERN_HOUR));

                schedule.setProfessor(prof);
                schedule.setLocation(local);
                schedule.setDescription(scheduleDescription);
                scheduleList.add(schedule);

            }

            setDateDebut(scheduleList.get(0).getStartDate());
            setDateDebut(scheduleList.get(scheduleList.size()).getStartDate());

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
