package com.hrt.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTools
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/09/23
 * @since 1.8
 **/
public final class DateTools {

    /**
     * 是否为月初第一天
     * @return
     */
    public static boolean isFirstDay(){
        Calendar currentDate=Calendar.getInstance();
        int day = currentDate.get(Calendar.DATE);
        return day==1;
    }

    /**
     * 月开始时间
     * @param date
     * @return
     */
    public static Date getStartMonth(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTime();
    }

    /**
     * 月结束时间
     * @param date
     * @return
     */
    public static Date getEndtMonth(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,-1);
        return calendar.getTime();
    }

    /**
     * 上个月第一天
     * @param date
     * @return
     */
    public static Date getUpMonthFirst(Date date){
    	Calendar calendar = Calendar.getInstance();    
    	calendar.add(Calendar.MONTH, -1);
    	calendar.set(Calendar.DAY_OF_MONTH,1);
        return calendar.getTime();
    }
    /**
     * 上个月最后一天
     * @param date
     * @return
     */
    public static Date getUpMonthLast(Date date){
    	Calendar calendar = Calendar.getInstance();   
    	calendar.set(Calendar.DAY_OF_MONTH,0);
    	return calendar.getTime();
    }
    /**
     * 上个月当天
     * @param date
     * @return
     */
    public static Date getUpMonth(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,-1);
        return calendar.getTime();
    }

    /**
     * 下个月当天
     * @param date
     * @return
     */
    public static Date getNextMonth(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        return calendar.getTime();
    }
}
