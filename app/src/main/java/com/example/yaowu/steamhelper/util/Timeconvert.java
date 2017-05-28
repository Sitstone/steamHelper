package com.example.yaowu.steamhelper.util;


import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by yaowu on 2017/5/25.
 */

public class Timeconvert {
    public static String converTime(String unixtime){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date (Long.valueOf(unixtime)*1000);

        return simpleDateFormat.format(date);
    }
}
