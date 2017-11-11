package cn.edu.nju.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jiayiwu on 17/11/11.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class DateTimeUtil {

    public static Date toDateTime(String dateTime) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(dateTime);
    }

    public static int compareDateTime(Date date1, Date date2) {
        return date1.compareTo(date2);
    }
}
