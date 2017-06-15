package open.main.view.timeselector;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间处理工具类
 * <p>
 * Created by YuXiaoLong
 */
@SuppressLint("SimpleDateFormat")
public final class TimeUtil {
    static Calendar cal = Calendar.getInstance();

    // 获取当前时间可以用来给图片命名或者记录聊聊天和评论时间
    public static long getCurrentTime() {

        long time = System.currentTimeMillis();

        return time;
    }


    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getCurrentMonth() {
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前是几号
     *
     * @return
     */
    public static int getCurrentDay() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 此方法用于格式化时间格式
     *
     * @param
     * @return
     */
    public static String formatTimeDiff(long date) {
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());//将时间格式化成的格式
        Date day = new Date();//当前时间
        String mtime = st.format(day);//将时间格式化成为yyyy-MM-dd

        Date time = stringToDate(mtime);//将yyyy-MM-dd型的时间的字符串转变成Date类型
        long timelong = dateToLong(time);//当前日期的长型 yyyy-MM-dd 类型的时间戳

        long diff = 0;
        String str = "";
        diff = (date - timelong) / 1000;//时间差


        if (diff > 0) {
            str = "今天   " + hhmmss(Long.valueOf(date));
        } else if (diff < 0 && diff >= -24 * 60 * 60) {
            str = "昨天   " + hhmmss(Long.valueOf(date));

        } else if (diff < -24 * 60 * 60 && diff >= -2 * 24 * 60 * 60) {
            str = "前天  " + hhmmss(Long.valueOf(date));
        } else if (diff <= -2 * 24 * 60 * 60) {
            str = longToTime(date);
        }
        return str;
    }

    public static String formatTimeYMD(long date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);
    }

    /**
     * 此方法用于格式化时间格式
     *
     * @param
     * @return
     */
    public static String getChatTimeDiffHM(String date) {

        long mDate = Long.parseLong(date + "000");// 将String转化成Long


        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());//将时间格式化成的格式
        Date day = new Date();//当前时间

        Date time = stringToDate(st.format(day));//将yyyy-MM-dd型的时间的字符串转变成Date类型

        long diff = 0;
        String str = "";
        diff = mDate - dateToLong(time);//时间差

        if (diff > 0) {
            str = "今天   " + hhmm(Long.valueOf(date));
        } else if (diff < 0 && diff >= -24 * 60 * 60) {
            str = "昨天   " + hhmm(Long.valueOf(date));

        } else if (diff < -24 * 60 * 60 * 1000 && diff >= -2 * 24 * 60 * 60) {
            str = "前天  " + hhmm(Long.valueOf(date));
        } else if (diff <= -2 * 24 * 60 * 60) {
            str = longToTime(mDate);
        }
        return str;
    }

    /**
     * @param date 这里需要传入的是13为的时间戳
     * @return 但会的是   yyyy-MM-dd 类型的时间字符串
     */
    public static String longToTime(Long date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);

    }

    /**
     * @param date 这里需要传入的是13为的时间戳
     * @return 但会的是   yyyy-MM-dd 类型的时间字符串
     */
    public static String longToTime2(Long date) {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);

    }

    /**
     * 此方法用于获取  HH:mm:ss 格式的时间
     *
     * @param date 这里需要传入的是13为的时间戳
     * @return 返回的是  HH:mm:ss  类型的时间字符串
     */
    public static String hhmmss(Long date) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);

    }

    /**
     * 此方法用于获取  HH:mm 格式的时间
     *
     * @param date 这里需要传入的是13为的时间戳
     * @return 返回的是  HH:mm  类型的时间字符串
     */
    public static String hhmm(Long date) {
        SimpleDateFormat time = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date day = new Date(date);
        return time.format(day);

    }

    /**
     * 此方法用于获取  HH:mm 格式的时间
     *
     * @return 返回的是  HH:mm  类型的时间
     */
    public static int mm() {
        SimpleDateFormat formatter = new SimpleDateFormat("mm", Locale.getDefault());
        Date day = new Date(getCurrentTime());
        String m = formatter.format(day);
        int minute;
        if (m.startsWith("0")) {
            minute = Integer.decode(m.substring(1, 2));
        } else {
            minute = Integer.decode(m);
        }
        return minute;
    }

    /**
     * 此方法用于获取  HH格式的时间
     *
     * @return 返回的是  HH 24小时制的小时时间类型
     */
    public static int HH() {
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 此方法用于获取传入时间的小时
     *
     * @return 返回的是  hh 类型的时间
     */
    public static int hh(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH", Locale.getDefault());
        Date day = new Date(time);
        String h = formatter.format(day);
        int hour;
        if (h.startsWith("0")) {
            hour = Integer.decode(h.substring(1, 2));
        } else {
            hour = Integer.decode(h);
        }
        return hour;
    }


    /**
     * 将date时间转换成long类型的时间戳
     *
     * @param date date 时间类型
     * @return 返回的是13为时间戳
     */
    public static Long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 将当前时间格式化为  yyyy-MM-dd  年-月-日，其目的适用于获取当日  凌晨 0点的时间戳
     *
     * @param dateString 13为的时间戳
     * @return 返回的是date时间
     */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDate(String dateString, String formatStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr, Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将当前时间转换成  yyyy-MM-dd 类型
     *
     * @return
     */
    public static String getYMD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date day = new Date(getCurrentTime());
        return formatter.format(day) + " ";
    }


    public static Date getTime(String time) {

        String tim = getYMD() + time;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date date = null;
        try {
            date = fmt.parse(tim);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取明天的这个（传入的时间）时间
     *
     * @param todayTime
     * @return
     */
    public static long getTomorrowTime(long todayTime) {
        long oneDay = 24 * 60 * 60 * 1000;
        return todayTime + oneDay;
    }


    public static String TormatTimeHMDHM(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm", Locale.getDefault());
        Date day = new Date(time);
        return formatter.format(day) + " ";
    }

    /**
     * 转换Oracle的date时间
     * 如果转换失败返回null
     *
     * @param time
     * @return
     */
    public static Date formatOracleDateString(String time) {
        //String str="20151201000000000+0800";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSSZ");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
        }
        return date;
    }

    public static final String FORMAT_YYYYMM = "yyyy-MM";
    public static final String FORMAT_YYYYMM_CHINESE = "yyyy年MM月";
    public static final String FORMAT_YYYYMMDD = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMM01 = "yyyy-MM-01";
    public static final String FORMAT_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FORMAT_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_ORACLE_TIME = "yyyyMMddHHmmssSSS+0800";
    public static final String FORMAT_SYSTEM_TIME = "yyyyMMddHHmmssSSS";

    public static String format(long date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date(date));
    }

    public static long parse(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(str).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static final String FORMAT_YYYY_M_DD = "yyy-M-dd";

    /**
     * yyyy-M-dd  To yyyy-MM-dd
     *
     * @param date 需要转换的时间字符串
     */
    public static String mToMM(String date) {
        return TimeUtil.format(TimeUtil.parse(date, FORMAT_YYYY_M_DD), TimeUtil.FORMAT_yyyy_MM_dd);

    }

    /**
     * yyyy-MM-dd  To yyyy-M-dd
     *
     * @param date 需要转换的时间字符串
     */
    public static String mmToM(String date) {
        return TimeUtil.format(TimeUtil.parse(date, TimeUtil.FORMAT_yyyy_MM_dd), FORMAT_YYYY_M_DD);
    }




    public static String getDays(long d){
        String str="";
        String year=TimeUtil.format(d,"yyyy");
        Log.w("time_year",year);
        String MM=TimeUtil.format(d,"MM");
        Log.w("time_MM",MM);
        try {
            Log.w("time_dd",TimeUtil.format(d,"dd"));
            if (TextUtils.equals(TimeUtil.format(d,"dd"),"01")){
                if (MM.indexOf("0")==0){//单月02.11
                    String M=MM.substring(1);
                    int m1=Integer.parseInt(M);
                    Log.w("time_m1",m1+"");
                    if (m1==1){//如果当前为1月
                        int year1=Integer.parseInt(year);
                        year1=year1-1;
                        int lastCount=getDaysByYearMonth(year1,12);
                        str=year1+12+"01至"+year1+12+lastCount;
                    }else{
                        int year1=Integer.parseInt(year);
                        m1=m1-1;
                        int count=getDaysByYearMonth(year1,m1);
                        str=year+"0"+m1+"01至"+year+m1+count;
                    }
                }else{
                    int m2=Integer.parseInt(MM);
                    Log.e("time_m2",m2+"");
                    int count=getDaysByYearMonth(Integer.parseInt(year),m2-1);
                    str=year+(m2-1)+"01至"+year+(m2-1)+count;
                }
            }else{
                str=year+MM+"01至"+TimeUtil.format(d,"yyyyMMdd");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     * */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
