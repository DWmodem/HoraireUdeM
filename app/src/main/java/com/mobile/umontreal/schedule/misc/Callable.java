package com.mobile.umontreal.schedule.misc;

import com.mobile.umontreal.schedule.parsing.UDMJsonData;

/**
 * Created by Corneliu on 19-Mar-2015.
 */
public interface Callable {
    void OnCallback(UDMJsonData data);
}
