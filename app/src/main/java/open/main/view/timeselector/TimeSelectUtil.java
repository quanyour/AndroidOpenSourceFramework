package open.main.view.timeselector;

import android.content.Context;


import java.util.Calendar;

/**
 * Created by Cree on 2016/5/30.
 */
public class TimeSelectUtil {
    private TimeSelectUtil() {
    }

    public static void getDate(TimeSelector.ResultHandler resultHandler, String title, String startDate, String firstTime, Context context) {
        TimeSelector timeSelector;
        if (title.equals("结束日期")) {
            timeSelector = new TimeSelector(context, resultHandler, firstTime
                    , TimeUtil.format(TimeUtil.getCurrentTime(), TimeUtil.FORMAT_yyyy_MM_dd_HH_mm_ss));
        } else {
            timeSelector = new TimeSelector(context, resultHandler, startDate
                    , TimeUtil.format(TimeUtil.getCurrentTime(), TimeUtil.FORMAT_yyyy_MM_dd_HH_mm_ss));
            int position = Calendar.getInstance().get(Calendar.YEAR) - 1;
            timeSelector.initCurrentSelectTime(position + "-1-1");
//            timeSelector.setYearSelect(position - 1);
        }
        timeSelector.setTitle(title);
        timeSelector.setMode(TimeSelector.MODE.YMD);
        timeSelector.setIsLoop(false);
        timeSelector.show();
    }

    public static void getDate_YMD(TimeSelector.ResultHandler resultHander, Context context, String title, String startDate, String endDate, String... selectTime) {
        TimeSelector timeSelector = new TimeSelector(context, resultHander, startDate, endDate);
        if (selectTime != null && selectTime.length > 0) {
            timeSelector.initCurrentSelectTime(selectTime[0]);
        }
        timeSelector.setTitle(title);
        timeSelector.setMode(TimeSelector.MODE.YMD);
        timeSelector.setIsLoop(false);
        timeSelector.show();
    }

    /**
     *
     * @param resultHander 回调的方法
     * @param context 上下文
     * @param title 控件标题
     * @param startDate  yyyy-m-d hh:mm
     * @param endDate    yyyy-m-d hh:mm
     * @param selectTime   yyyy-m-d
     */
    public static void getDate_YM(TimeSelector.ResultHandler resultHander, Context context, String title, String startDate, String endDate, String... selectTime) {
        TimeSelector timeSelector = new TimeSelector(context, resultHander, TimeSelector.MODE.YM, startDate, endDate);
        if (selectTime != null && selectTime.length > 0) {
            timeSelector.initCurrentSelectTime(selectTime[0]);
        }
        timeSelector.setTitle(title);
        timeSelector.setMode(TimeSelector.MODE.YM);
        timeSelector.setIsLoop(false);
        timeSelector.show();
    }
}
