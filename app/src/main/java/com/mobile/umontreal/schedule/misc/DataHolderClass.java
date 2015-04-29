package com.mobile.umontreal.schedule.misc;

import com.mobile.umontreal.schedule.navigation.NavItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 28-Apr-2015.
 */
public class DataHolderClass {

    private static DataHolderClass dataObject = null;
    private List<NavItem> distributor;

    private DataHolderClass() {
        // left blank intentionally
    }

    public static DataHolderClass getInstance() {
        if (dataObject == null)
            dataObject = new DataHolderClass();
        return dataObject;
    }

    public List<NavItem> getDistributor() {
        return distributor;
    }

    public void setDistributor(List<NavItem> distributor) {

        this.distributor = new ArrayList<NavItem>();
        this.distributor.addAll(distributor);

    }
}
