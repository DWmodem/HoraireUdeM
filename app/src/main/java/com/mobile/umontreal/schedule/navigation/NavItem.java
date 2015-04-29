package com.mobile.umontreal.schedule.navigation;

import android.graphics.drawable.Drawable;

public class NavItem {

    private String text;
    private Drawable drawable;
    private String session;

    public NavItem(String text, Drawable drawable, String session) {

        this.drawable = drawable;
        this.session = setSession(text, session);
        this.text = text;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getText() {
        return session;
    }

    public void setText(String text) {
        this.session = text;
    }

    public String getSession() {
        return text;
    }

    private String setSession(String text, String session) {
        return session + " 20" + text.substring(1);
    }
}
