package com.manage.utils;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间的处理
 * @Author Linyb
 * @Date 2016/12/14.
 */
public class DateUtil {

    /**
     * 把指定的时间转化成默认格式的时间字符串
     * @Author Linyb
     * @Date 2016/12/14 10:26
     */
    public static String dateFormatDefaul(Date date){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Integer getAgeByBirthday(String date) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date mydate= null;
        try {
            mydate = myFormatter.parse(date);
        } catch (ParseException e) {
        }
        Calendar cal = Calendar.getInstance();
        if (cal.before(mydate)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(mydate);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    public static Date changeToDate(String date){
        String d = date.substring(0,date.length()-2);
        String str = date.substring(date.length()-3,date.length()).trim();
        Date mydate= null;
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            mydate = myFormatter.parse(d);
            if("pm".equals(str)){
                Long mytime = mydate.getTime();
                mytime += 12 * 60 * 60 * 1000;
                mydate.setTime(mytime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mydate;
    }
}
