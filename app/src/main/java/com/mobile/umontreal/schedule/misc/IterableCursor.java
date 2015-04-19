package com.mobile.umontreal.schedule.misc;

import android.database.Cursor;

import java.util.Iterator;

/**
 * Created by Corneliu on 18-Apr-2015.
 */
public class IterableCursor implements Iterable<Cursor>, Iterator<Cursor> {

    Cursor cursor;
    int toVisit;

    public IterableCursor(Cursor cursor) {
        this.cursor = cursor;
        toVisit = cursor.getCount();
    }

    public Iterator<Cursor> iterator() {
        cursor.moveToPosition(-1);
        return this;
    }
    public boolean hasNext() {
        return toVisit > 0;
    }

    public Cursor next() {
        cursor.moveToNext();
        toVisit--;
        return cursor;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

}