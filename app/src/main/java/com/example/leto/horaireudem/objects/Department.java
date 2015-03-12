package com.example.leto.horaireudem.objects;

import java.util.List;

/**
 * Created by Justin on 2015-03-12.
 */
public class Department {
    private String sigle;
    private String title;
    private List<Course> courses;

    public String getSigle() {
        return sigle;
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

    @Override
    public String toString(){
        return getTitle();
    }
}
