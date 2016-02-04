package com.tlab.wish.wishes;

import android.text.format.DateFormat;

import com.tlab.wish.utils.DateUtils;
import com.tlab.wish.utils.ExceptionTracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andranik on 2/4/16.
 */
public class DateFormater {

    private Date date;
    private String dateStr;

    public DateFormater(String dateStr) {
        this.dateStr = dateStr;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            ExceptionTracker.trackException(e);
        }
    }

    public String getFormatedDate(){
        if(date == null){
            return dateStr;
        }

        if(DateUtils.isToday(date)){
            return DateFormat.format("HH:mm", date).toString();
        } else {
            return DateFormat.format("d MMM yyyy", date).toString();
        }
    }
}
