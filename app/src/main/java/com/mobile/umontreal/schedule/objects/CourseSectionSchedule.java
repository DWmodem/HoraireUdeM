package com.mobile.umontreal.schedule.objects;

import java.util.Date;

/**
 * Created by Justin on 2015-03-12.
 */
public class CourseSectionSchedule {
    private CourseSection section;
    private Date dateDebut;
    private Date dateFin;
    private String local;
    private CourseSectionScheduleType type;

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

    @Override
    public String toString(){
        //TODO
        return super.toString();
    }
}
