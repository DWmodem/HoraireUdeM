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
public class CourseSection {

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

    private ArrayList<String> sectionList;

    public ArrayList<String> getSectionList() {
        return sectionList;
    }

    public String getType() { return type; }

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

    public List<String> getCoursesSection() { return sectionList; }

    public CourseSection(JSONObject json) throws JSONException {

        sectionList = new ArrayList<String>();

        // Getting JSON Array node
        JSONArray sections = json.getJSONArray(Config.JSON_SECTIONS);

        try {

            // looping through All Contacts
            for (int i = 0; i < sections.length(); i++) {

                JSONObject c = sections.getJSONObject(i);

                if (i == 0) {

                    String dateCacncel = c.getString(Config.JSON_SECTION_CANCELLATION);
                    String dateDropped = c.getString(Config.JSON_SECTION_DROP);
                    String dateDroppedLimit = c.getString(Config.JSON_SECTION_DROP_LIMIT);

                    Date dateCancellation = Config.parsingDate(dateCacncel,
                            Config.PARSING_DATE_FORMAT);
                    Date dateDrop = Config.parsingDate(dateDropped,
                            Config.PARSING_DATE_FORMAT);
                    Date dateDropLimit = Config.parsingDate(dateDroppedLimit,
                            Config.PARSING_DATE_FORMAT);

                    this.setType(c.getString(Config.JSON_SECTION_TYPE));
                    this.setCancel(dateCancellation);
                    this.setDrop(dateDrop);
                    this.setDropLimit(dateDropLimit);
                    this.setDescription(c.getString(Config.JSON_SECTION_DESCRIPTION));

                 }

                sectionList.add(c.getString(Config.JSON_SECTION_TITLE));
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
