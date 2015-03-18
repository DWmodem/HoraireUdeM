package com.example.leto.horaireudem.misc;

/**
 * Created by Corneliu on 16-Mar-2015.
 */
public class SpinnerNavItem {

        private String title;
        private int year;

        public SpinnerNavItem(String title, int year){
            this.title = title;
            this.year = year;
        }

        public String getTitle(){
            return this.title;
        }

        public int getYear(){
            return this.year;
        }

}
