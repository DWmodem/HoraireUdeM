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
public class CourseSection {

    public static final String JSON_COURSE_SECTIONS = "sections";

    public static final String JSON_SECTION_TITLE = "section";
    public static final String JSON_SECTION_TYPE = "type";
    public static final String JSON_SECTION_CANCELLATION = "annulation";
    public static final String JSON_SECTION_DROP = "abandon";
    public static final String JSON_SECTION_DROP_LIMIT = "abandonlimite";
    public static final String JSON_SECTION_DESCRIPTION = "description";

    private Course course;
    private CourseSectionStatus status;
    private int credit;
    private String section;
    private SectionType sectionType;
    private Date cancel;
    private Date drop;
    private Date dropLimit;
    private String description;
    private String type;

    private List<CourseSection> sectionList;

    public List<CourseSection> getSectionList() {
        return sectionList;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public CourseSectionStatus getStatus() {
        return status;
    }

    public void setStatus(CourseSectionStatus status) {
        this.status = status;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public Date getCancel() {
        return cancel;
    }

    public void setCancel(Date cancel) {
        this.cancel = cancel;
    }

    public Date getDrop() {
        return drop;
    }

    public void setDrop(Date drop) {
        this.drop = drop;
    }

    public Date getDropLimit() {
        return dropLimit;
    }

    public void setDropLimit(Date dropLimit) {
        this.dropLimit = dropLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public CourseSection(){

    }

    public List<CourseSection> getCoursesSection() { return sectionList; }

    public CourseSection(JSONObject json) throws JSONException {

        sectionList = new ArrayList<CourseSection>();

        // Getting JSON Array node
        JSONArray sections = json.getJSONArray(JSON_COURSE_SECTIONS);
        SimpleDateFormat format = new SimpleDateFormat(Config.DATE_FORMAT_PARSING);

        try {

            // looping through All Contacts
            for (int i = 0; i < sections.length(); i++) {

                JSONObject c = sections.getJSONObject(i);

                Date dateCancellation = format.parse(c.getString(JSON_SECTION_CANCELLATION));
                Date dateDrop = format.parse(c.getString(JSON_SECTION_DROP));
                Date dateDropLimit = format.parse(c.getString(JSON_SECTION_DROP_LIMIT));

                CourseSection courseSection = new CourseSection();

                courseSection.setSection(c.getString(JSON_SECTION_TITLE));
                courseSection.setType(c.getString(JSON_SECTION_TYPE));
                courseSection.setCancel(dateCancellation);
                courseSection.setDrop(dateDrop);
                courseSection.setDropLimit(dateDropLimit);
                courseSection.setDescription(c.getString(JSON_SECTION_DESCRIPTION));
                sectionList.add(courseSection);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString(){
        //Example IFT 1144 - Introduction Ã  la programmation internet (A)
        return String.format("%s (%s)", getCourse().toString(), getSection());
    }



}
