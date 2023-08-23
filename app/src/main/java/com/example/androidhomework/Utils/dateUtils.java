package com.example.androidhomework.Utils;

import java.security.PublicKey;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class dateUtils {

    public static List<String> holidayList = Arrays.asList("2022-01-01","2022-01-02","2022-01-03","2022-01-31",
            "2022-02-01", "2022-02-02", "2022-02-03", "2022-02-04", "2022-02-05", "2022-02-06", "2022-04-03",
            "2022-04-04", "2022-04-05", "2022-04-30", "2022-05-01", "2022-05-02", "2022-05-03", "2022-05-04",
            "2022-06-03", "2022-06-04", "2022-06-05", "2022-09-10", "2022-09-11", "2022-09-12", "2022-10-01",
            "2022-10-02", "2022-10-03", "2022-10-04", "2022-10-05", "2022-10-06", "2022-10-07");

    public static List<String> workdayList = Arrays.asList("2022-01-29","2022-01-30","2022-04-02","2022-04-24",
            "2022-05-07","2022-10-08","2022-10-09");

    public static boolean compareDays(String day1,String day2) throws ParseException {

        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date1 = (Date) simpleFormat.parse(day1);
        Date date2 = (Date) simpleFormat.parse(day2);

        if(date1.compareTo(date2) <= 0){
            return true;
            //date1在date2前面或者相等
        }
        else{
            return false;
        }

    }

    public static int differenceInDays(String date) throws ParseException {

        Date fromDate = new Date();

        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date toDate = (Date) simpleFormat.parse(date);

        long from = fromDate.getTime();
        long to = toDate.getTime();
        int days = (int)((to - from) / (1000 * 60 * 60 * 24));

        return days;
    }

    public static long getDate(String date) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date1 = (Date) simpleFormat.parse(date);
        return date1.getTime();
    }

    public static boolean differenceTime(String date) throws ParseException {

        Date fromDate = new Date();

        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date toDate = (Date) simpleFormat.parse(date);

        long from = fromDate.getTime();
        long to = toDate.getTime();
        long days = to - from;

        if(days <= 0)
            return true;
        else
            return false;
    }

    public static String changeFormat(String date) throws ParseException {

        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = (Date) simpleFormat.parse(date);
        date = simpleFormat.format(date1);

        return date;
    }

    public static int daysBetween(int day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        Date date2 = format.parse("2022-2-27");
        int a = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
        a += day;
        return a;
    }

    public static int daysBetween(Date dateEnd) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart = format.parse("2022-2-27");
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000*3600*24));
    }

    public static int getWeekDay(int num) {

        if(num<0)
            num = 0;

        int a;
        a = num%7;
        if(a == 0){
            a = 7;
        }
        return a;
    }

    public static int getWeek(int num) {

        if(num<0)
            num = 0;

        return num/7;
    }

}
