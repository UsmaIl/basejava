package com.dofler.webapp.util;

import com.dofler.webapp.model.Place;

public class HtmlUtil {
    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static String formatDates(Place place) {
        return DateUtil.format(place.getStartDate()) + " - " + DateUtil.format(place.getEndDate());
    }
}
