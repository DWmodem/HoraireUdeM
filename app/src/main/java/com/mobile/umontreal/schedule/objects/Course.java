package com.mobile.umontreal.schedule.objects;

import com.mobile.umontreal.schedule.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Justin on 2015-03-12.
 */
public class Course {

    private Department department;
    private int courseNumber;
    private String title;
    private Session session;
    private List<CourseSection> courseSection;


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<CourseSection> getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(List<CourseSection> courseSection) {
        this.courseSection = courseSection;
    }

    public String getCourseId(){
        return getDepartment().getSigle() + " " + getCourseNumber();
    }

    public Course() {

    }

    public Course(JSONObject json) throws JSONException {
        setTitle(json.getString(Config.JSON_COURSE_TITLE));
        setCourseNumber(json.getInt(Config.JSON_COURSE_NUM));
    }

    @Override
    public String toString(){
        return getCourseNumber() + " - " + getTitle();
    }
}
