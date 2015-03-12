package com.example.leto.horaireudem.objects;

/**
 * Created by Justin on 2015-03-12.
 */
public class Session {
    private SessionSeason season;
    private int year;


    public SessionSeason getSeason() {
        return season;
    }

    public void setSeason(SessionSeason season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Session(){

    }

    @Override
    public String toString(){
        int twoDigitYear = getYear() % 100;

        switch (getSeason()) {
            case Winter:
                return "H" + twoDigitYear;
            case Summer:
                return "E" + twoDigitYear;
            case Autumn:
                return "A" + twoDigitYear;
            default:
                throw new NullPointerException("The season is null");
        }
    }
}
