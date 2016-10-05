package com.genericregistrationsystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This is the GRS Utility class for common
 * code processes like handling dates etc.
 *
 * Created by Alan Dimaano on 8/19/2015.
 * Rhiza C. Roque
 */
public class GRSUtility {
    private String getAddDateTimeString() {
        Calendar cad = Calendar.getInstance();
        SimpleDateFormat dfad = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedAddDate = dfad.format(cad.getTime());
        return formattedAddDate;
    }
}
