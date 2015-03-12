package com.example.leto.horaireudem.objects;

import java.util.Date;

/**
 * Created by Justin on 2015-03-12.
 */
public class CourseSection {
    private Course course;
    private CourseSectionStatus status;
    private int credit;
    private String section;
    private CourseSectionType sectionType;
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

    public CourseSectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(CourseSectionType sectionType) {
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

    @Override
    public String toString(){
        //Example IFT 1144 - Introduction Ã  la programmation internet (A)
        return String.format("%s (%s)", getCourse().toString(), getSection());
    }



}
