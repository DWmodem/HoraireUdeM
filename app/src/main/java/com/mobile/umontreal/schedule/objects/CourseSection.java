package com.mobile.umontreal.schedule.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

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

    public CourseSection(JSONObject json) throws JSONException {
        setSection(json.getString(JSON_SECTION_TITLE));

    }

    @Override
    public String toString(){
        //Example IFT 1144 - Introduction Ã  la programmation internet (A)
        return String.format("%s (%s)", getCourse().toString(), getSection());
    }



}
