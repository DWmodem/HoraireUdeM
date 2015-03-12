package com.example.leto.horaireudem.objects;

/**
 * Created by Justin on 2015-03-12.
 */
public enum SessionSeason {
    Winter("H"),
    Summer("E"),
    Autumn("A");

    private final String acronym;

    public String getAcronym(){
        return  acronym;
    }

    private SessionSeason(String a){
        acronym = a;
    }
}
