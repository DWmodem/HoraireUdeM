package com.example.leto.horaireudem.objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Justin on 2015-03-12.
 */
public class Department {
    public static final String JSON_SIGLE_TAG = "sigle";
    public static final String JSON_TITLE_TAG = "titre";

    private String sigle;
    private String title;
    private List<Course> courses;

    public String getSigle() {
        return sigle == null ? null : sigle.toUpperCase();
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Department(){

    }

    public Department(JSONObject json) throws JSONException{
        setSigle(json.getString(JSON_SIGLE_TAG));
        setTitle(json.getString(JSON_TITLE_TAG));
    }

    @Override
    public String toString(){
        return getSigle() + " - " + getTitle();
    }
}
