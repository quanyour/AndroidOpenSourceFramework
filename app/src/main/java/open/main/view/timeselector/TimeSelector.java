package open.main.view.timeselector;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import org.feezu.liuli.timeselector.Utils.DateUtil;
import org.feezu.liuli.timeselector.Utils.ScreenUtil;
import org.feezu.liuli.timeselector.Utils.TextUtil;
import org.feezu.liuli.timeselector.view.PickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cree on 2016/5/20.
 */
public class TimeSelector {
    public interface ResultHandler {
        void handle(String time);
    }

    public enum SCROLLTYPE {

        HOUR(1),
        MINUTE(2);

        private SCROLLTYPE(int value) {
            this.value = value;
        }

        public int value;

    }

    public enum MODE {
        YM(0),
        YMD(1),
        YMDHM(2);

        private MODE(int value) {
            this.value = value;
        }

        public int value;

    }


    private int scrollUnits = SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value;
    private ResultHandler handler;
    private Context context;
    private final String FORMAT_STR = "yyyy-MM-dd HH:mm";

    private Dialog seletorDialog;
    private PickerView year_pv;
    private PickerView month_pv;
    private PickerView day_pv;
    private PickerView hour_pv;
    private PickerView minute_pv;

    private final int MAXMINUTE = 59;
    private int MAXHOUR = 23;
    private final int MINMINUTE = 0;
    private int MINHOUR = 0;
    private final int MAXMONTH = 12;

    private ArrayList<String> year, month, day, hour, minute;
    private int startYear, startMonth, startDay, startHour, startMininute, endYear, endMonth, endDay, endHour, endMininute, minute_workStart, minute_workEnd, hour_workStart, hour_workEnd;
    private boolean spanYear, spanMon, spanDay, spanHour, spanMin;
    private Calendar selectedCalender = Calendar.getInstance();
    private final long ANIMATORDELAY = 200L;
    private final long CHANGEDELAY = 90L;
    private String workStart_str;
    private String workEnd_str;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private TextView tv_cancle;
    private TextView tv_select, tv_title;
    private TextView hour_text;
    private TextView minute_text;


    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate) {
        this.context = context;
        this.handler = resultHandler;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        initDialog();
        initView();
    }

    public TimeSelector(Context context, ResultHandler resultHandler, MODE mode, String startDate, String endDate) {
        this.context = context;
        this.handler = resultHandler;
        this.mode = mode;
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtil.parse(startDate, FORMAT_STR));
        endCalendar.setTime(DateUtil.parse(endDate, FORMAT_STR));
        initDialog();
        initView();
    }

    public TimeSelector(Context context, ResultHandler resultHandler, String startDate, String endDate, String workStartTime, String workEndTime) {
        this(context, resultHandler, startDate, endDate);
        this.workStart_str = workStartTime;
        this.workEnd_str = workEndTime;
    }

    public void show() {
        if (startCalendar.getTime().getTime() >= endCalendar.getTime().getTime()) {
            Toast.makeText(context, "start>end", Toast.LENGTH_LONG).show();
            return;
        }

        if (!excuteWorkTime()) return;
        initParameter();
//        initSelect();
        initTimer();
        addListener();
        seletorDialog.show();


    }

    private Calendar currentCalendar = Calendar.getInstance();

    public void initSelect() {
        int intYear = currentCalendar.get(Calendar.YEAR);
        int intMonth = currentCalendar.get(Calendar.MONTH) + 1;
        int intDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        if (year.contains(intYear + "")) {
            selectedCalender.set(Calendar.YEAR, intYear);
            int yearPosition = year.indexOf(intYear + "");
            this.yearPosition = yearPosition;
            changeMonth();
        }
        if (month.contains(addZero(intMonth, 2))) {
            selectedCalender.set(Calendar.MONTH, intMonth - 1);
            int monthPosition = month.indexOf(addZero(intMonth, 2));
            this.monthPosition = monthPosition;
            dayChange();
        }
        if (day.contains(addZero(intDay, 2))) {
            selectedCalender.set(Calendar.DAY_OF_MONTH, intDay);
            int dayPosition = day.indexOf(addZero(intDay, 2));
            this.dayPosition = dayPosition;
        }
    }

    /**
     * 前面加0
     *
     * @param num 数字
     * @param len 格式化后的长度
     * @return
     */
    private String addZero(int num, int len) {
        StringBuffer s = new StringBuffer();
        s.append(num);
        while (s.length() < len) {
            s.insert(0, "0");           // 在第1个位置处补0
        }
        return s.toString();
    }

    /**
     * @param ymd
     */
    public void initCurrentSelectTime(String ymd) {
        if (TextUtils.isEmpty(ymd)) {
            return;
        }
        Date date = TimeUtil.stringToDate(ymd);
        if (date == null) return;
        if (currentCalendar == null)
            currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(date);
    }

    /**
     *
     * @param ymd
     * @param formatStr
     */
    public void initCurrentSelectTime(String ymd,String formatStr) {
        if (TextUtils.isEmpty(ymd)) {
            return;
        }
        Date date = TimeUtil.stringToDate(ymd,formatStr);
        if (date == null) return;
        if (currentCalendar == null)
            currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(date);
    }

    public void dismiss() {
        if (seletorDialog != null)
            seletorDialog.dismiss();
    }

    // TODO: 2016/5/24 添加加载的布局
    ViewGroup contentView;

    private void initDialog() {
        if (seletorDialog == null) {
            seletorDialog = new Dialog(context, org.feezu.liuli.timeselector.R.style.time_dialog);
            seletorDialog.setCancelable(false);
            seletorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            contentView = (ViewGroup) LayoutInflater.from(context).inflate(org.feezu.liuli.timeselector.R.layout.dialog_selector, null);
            if (mode == MODE.YM) {
                findDayTextView(contentView);
                if (dayView != null) {
                    dayView.setVisibility(View.GONE);
                }
            }
            seletorDialog.setContentView(contentView);
            Window window = seletorDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = window.getAttributes();
            int width = ScreenUtil.getInstance(context).getScreenWidth();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    TextView dayView;

    private View findDayTextView(View vg) {
        if (vg instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) vg).getChildCount(); i++) {
                findDayTextView(((ViewGroup) vg).getChildAt(i));
            }
        } else {
            if (vg instanceof TextView) {
                if (((TextView) vg).getText().toString().equals("日")) {
                    dayView = (TextView) vg;
                    return vg;
                }
            }
        }
        return vg;
    }

    private void initView() {
        year_pv = (PickerView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.year_pv);
        month_pv = (PickerView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.month_pv);
        day_pv = (PickerView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.day_pv);
        hour_pv = (PickerView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.hour_pv);
        minute_pv = (PickerView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.minute_pv);
        tv_cancle = (TextView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.tv_cancle);
        tv_select = (TextView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.tv_select);
        tv_title = (TextView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.tv_title);
        hour_text = (TextView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.hour_text);
        minute_text = (TextView) seletorDialog.findViewById(org.feezu.liuli.timeselector.R.id.minute_text);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seletorDialog.dismiss();
            }
        });
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.handle(DateUtil.format(selectedCalender.getTime(), FORMAT_STR));
                seletorDialog.dismiss();
            }
        });

    }

    private void initParameter() {
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH) + 1;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        startHour = startCalendar.get(Calendar.HOUR_OF_DAY);
        startMininute = startCalendar.get(Calendar.MINUTE);
        endYear = endCalendar.get(Calendar.YEAR);
        endMonth = endCalendar.get(Calendar.MONTH) + 1;
        endDay = endCalendar.get(Calendar.DAY_OF_MONTH);
        endHour = endCalendar.get(Calendar.HOUR_OF_DAY);
        endMininute = endCalendar.get(Calendar.MINUTE);
        spanYear = startYear != endYear;
        spanMon = (!spanYear) && (startMonth != endMonth);
        spanDay = (!spanMon) && (startDay != endDay);
        spanHour = (!spanDay) && (startHour != endHour);
        spanMin = (!spanHour) && (startMininute != endMininute);
        selectedCalender.setTime(startCalendar.getTime());
    }

    private void initTimer() {
        initArrayList();

        if (spanYear) {
            for (int i = startYear; i <= endYear; i++) {
                year.add(String.valueOf(i));
            }
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
            if ((scrollUnits & SCROLLTYPE.HOUR.value) != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAXHOUR; i++) {
                    hour.add(fomatTimeUnit(i));
                }
            }

            if ((scrollUnits & SCROLLTYPE.MINUTE.value) != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute));
            } else {
                for (int i = startMininute; i <= MAXMINUTE; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            }

        } else if (spanMon) {
            year.add(String.valueOf(startYear));
            for (int i = startMonth; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i));
            }
            for (int i = startDay; i <= startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
            if ((scrollUnits & SCROLLTYPE.HOUR.value) != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAXHOUR; i++) {
                    hour.add(fomatTimeUnit(i));
                }
            }

            if ((scrollUnits & SCROLLTYPE.MINUTE.value) != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute));
            } else {
                for (int i = startMininute; i <= MAXMINUTE; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            }
        } else if (spanDay) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            for (int i = startDay; i <= endDay; i++) {
                day.add(fomatTimeUnit(i));
            }
            if ((scrollUnits & SCROLLTYPE.HOUR.value) != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= MAXHOUR; i++) {
                    hour.add(fomatTimeUnit(i));
                }
            }

            if ((scrollUnits & SCROLLTYPE.MINUTE.value) != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute));
            } else {
                for (int i = startMininute; i <= MAXMINUTE; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            }

        } else if (spanHour) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            day.add(fomatTimeUnit(startDay));

            if ((scrollUnits & SCROLLTYPE.HOUR.value) != SCROLLTYPE.HOUR.value) {
                hour.add(fomatTimeUnit(startHour));
            } else {
                for (int i = startHour; i <= endHour; i++) {
                    hour.add(fomatTimeUnit(i));
                }

            }

            if ((scrollUnits & SCROLLTYPE.MINUTE.value) != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute));
            } else {
                for (int i = startMininute; i <= MAXMINUTE; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            }


        } else if (spanMin) {
            year.add(String.valueOf(startYear));
            month.add(fomatTimeUnit(startMonth));
            day.add(fomatTimeUnit(startDay));
            hour.add(fomatTimeUnit(startHour));


            if ((scrollUnits & SCROLLTYPE.MINUTE.value) != SCROLLTYPE.MINUTE.value) {
                minute.add(fomatTimeUnit(startMininute));
            } else {
                for (int i = startMininute; i <= endMininute; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            }
        }

        loadComponent();

    }

    private boolean excuteWorkTime() {
        boolean res = true;
        if (!TextUtil.isEmpty(workStart_str) && !TextUtil.isEmpty(workEnd_str)) {
            String[] start = workStart_str.split(":");
            String[] end = workEnd_str.split(":");
            hour_workStart = Integer.parseInt(start[0]);
            minute_workStart = Integer.parseInt(start[1]);
            hour_workEnd = Integer.parseInt(end[0]);
            minute_workEnd = Integer.parseInt(end[1]);
            Calendar workStartCalendar = Calendar.getInstance();
            Calendar workEndCalendar = Calendar.getInstance();
            workStartCalendar.setTime(startCalendar.getTime());
            workEndCalendar.setTime(endCalendar.getTime());
            workStartCalendar.set(Calendar.HOUR_OF_DAY, hour_workStart);
            workStartCalendar.set(Calendar.MINUTE, minute_workStart);
            workEndCalendar.set(Calendar.HOUR_OF_DAY, hour_workEnd);
            workEndCalendar.set(Calendar.MINUTE, minute_workEnd);


            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            Calendar startWorkTime = Calendar.getInstance();
            Calendar endWorkTime = Calendar.getInstance();

            startTime.set(Calendar.HOUR_OF_DAY, startCalendar.get(Calendar.HOUR_OF_DAY));
            startTime.set(Calendar.MINUTE, startCalendar.get(Calendar.MINUTE));
            endTime.set(Calendar.HOUR_OF_DAY, endCalendar.get(Calendar.HOUR_OF_DAY));
            endTime.set(Calendar.MINUTE, endCalendar.get(Calendar.MINUTE));

            startWorkTime.set(Calendar.HOUR_OF_DAY, workStartCalendar.get(Calendar.HOUR_OF_DAY));
            startWorkTime.set(Calendar.MINUTE, workStartCalendar.get(Calendar.MINUTE));
            endWorkTime.set(Calendar.HOUR_OF_DAY, workEndCalendar.get(Calendar.HOUR_OF_DAY));
            endWorkTime.set(Calendar.MINUTE, workEndCalendar.get(Calendar.MINUTE));


            if (startTime.getTime().getTime() == endTime.getTime().getTime() || (startWorkTime.getTime().getTime() < startTime.getTime().getTime() && endWorkTime.getTime().getTime() < startTime.getTime().getTime())) {
                Toast.makeText(context, "Wrong parames!", Toast.LENGTH_LONG).show();
                return false;
            }
            startCalendar.setTime(startCalendar.getTime().getTime() < workStartCalendar.getTime().getTime() ? workStartCalendar.getTime() : startCalendar.getTime());
            endCalendar.setTime(endCalendar.getTime().getTime() > workEndCalendar.getTime().getTime() ? workEndCalendar.getTime() : endCalendar.getTime());
            MINHOUR = workStartCalendar.get(Calendar.HOUR_OF_DAY);
            MAXHOUR = workEndCalendar.get(Calendar.HOUR_OF_DAY);

        }
        return res;


    }

    private String fomatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

    private void initArrayList() {
        if (year == null) year = new ArrayList<>();
        if (month == null) month = new ArrayList<>();
        if (day == null) day = new ArrayList<>();
        if (hour == null) hour = new ArrayList<>();
        if (minute == null) minute = new ArrayList<>();
        year.clear();
        month.clear();
        day.clear();
        hour.clear();
        minute.clear();
    }


    private void addListener() {
        year_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.YEAR, Integer.parseInt(text));
                monthChange();


            }
        });
        month_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, 1);
                selectedCalender.set(Calendar.MONTH, Integer.parseInt(text) - 1);
                dayChange();


            }
        });
        day_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(text));
                hourChange();

            }
        });
        hour_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(text));
                minuteChange();


            }
        });
        minute_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectedCalender.set(Calendar.MINUTE, Integer.parseInt(text));


            }
        });

    }

    private void loadComponent() {
        initSelect();
        year_pv.setData(year);
        month_pv.setData(month);
        day_pv.setData(day);
        hour_pv.setData(hour);
        minute_pv.setData(minute);
        year_pv.setSelected(yearPosition);
        month_pv.setSelected(monthPosition);
        day_pv.setSelected(dayPosition);
        hour_pv.setSelected(0);
        minute_pv.setSelected(0);
        excuteScroll();
    }

    private void excuteScroll() {
        year_pv.setCanScroll(year.size() > 1);
        month_pv.setCanScroll(month.size() > 1);
        day_pv.setCanScroll(day.size() > 1);
        hour_pv.setCanScroll(hour.size() > 1 && (scrollUnits & SCROLLTYPE.HOUR.value) == SCROLLTYPE.HOUR.value);
        minute_pv.setCanScroll(minute.size() > 1 && (scrollUnits & SCROLLTYPE.MINUTE.value) == SCROLLTYPE.MINUTE.value);
    }

    private int yearPosition = 0;
    private int monthPosition = 0;
    private int dayPosition = 0;

    public void setYearSelect(int position) {
        this.yearPosition = position;
    }

    private void changeMonth() {
        month.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        if (selectedYear == startYear) {
            for (int i = startMonth; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear) {
            for (int i = 1; i <= endMonth; i++) {
                month.add(fomatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= MAXMONTH; i++) {
                month.add(fomatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.MONTH, Integer.parseInt(month.get(0)) - 1);
        month_pv.setData(month);
        month_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, month_pv);
    }

    private void changeDay() {
        day.clear();
        int selectedYear = selectedCalender.get(Calendar.YEAR);
        int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
        if (selectedYear == startYear && selectedMonth == startMonth) {
            for (int i = startDay; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
        } else if (selectedYear == endYear && selectedMonth == endMonth) {
            for (int i = 1; i <= endDay; i++) {
                day.add(fomatTimeUnit(i));
            }
        } else {
            for (int i = 1; i <= selectedCalender.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                day.add(fomatTimeUnit(i));
            }
        }
        selectedCalender.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day.get(0)));
        day_pv.setData(day);
        day_pv.setSelected(0);
        excuteAnimator(ANIMATORDELAY, day_pv);
    }

    private void monthChange() {
        changeMonth();
        month_pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                dayChange();
            }
        }, CHANGEDELAY);

    }

    private void dayChange() {
        changeDay();
        day_pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                hourChange();
            }
        }, CHANGEDELAY);
    }

    private void hourChange() {
        if ((scrollUnits & SCROLLTYPE.HOUR.value) == SCROLLTYPE.HOUR.value) {
            hour.clear();
            int selectedYear = selectedCalender.get(Calendar.YEAR);
            int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
            int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);

            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay) {
                for (int i = startHour; i <= MAXHOUR; i++) {
                    hour.add(fomatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay) {
                for (int i = MINHOUR; i <= endHour; i++) {
                    hour.add(fomatTimeUnit(i));
                }
            } else {

                for (int i = MINHOUR; i <= MAXHOUR; i++) {
                    hour.add(fomatTimeUnit(i));
                }

            }
            selectedCalender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.get(0)));
            hour_pv.setData(hour);
            hour_pv.setSelected(0);
            excuteAnimator(ANIMATORDELAY, hour_pv);
        }
        hour_pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                minuteChange();
            }
        }, CHANGEDELAY);

    }

    private void minuteChange() {
        if ((scrollUnits & SCROLLTYPE.MINUTE.value) == SCROLLTYPE.MINUTE.value) {
            minute.clear();
            int selectedYear = selectedCalender.get(Calendar.YEAR);
            int selectedMonth = selectedCalender.get(Calendar.MONTH) + 1;
            int selectedDay = selectedCalender.get(Calendar.DAY_OF_MONTH);
            int selectedHour = selectedCalender.get(Calendar.HOUR_OF_DAY);

            if (selectedYear == startYear && selectedMonth == startMonth && selectedDay == startDay && selectedHour == startHour) {
                for (int i = startMininute; i <= MAXMINUTE; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            } else if (selectedYear == endYear && selectedMonth == endMonth && selectedDay == endDay && selectedHour == endHour) {
                for (int i = MINMINUTE; i <= endMininute; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            } else if (selectedHour == hour_workStart) {
                for (int i = minute_workStart; i <= MAXMINUTE; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            } else if (selectedHour == hour_workEnd) {
                for (int i = MINMINUTE; i <= minute_workEnd; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            } else {
                for (int i = MINMINUTE; i <= MAXMINUTE; i++) {
                    minute.add(fomatTimeUnit(i));
                }
            }
            selectedCalender.set(Calendar.MINUTE, Integer.parseInt(minute.get(0)));
            minute_pv.setData(minute);
            minute_pv.setSelected(0);
            excuteAnimator(ANIMATORDELAY, minute_pv);

        }
        excuteScroll();


    }

    private void excuteAnimator(long ANIMATORDELAY, View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                1.3f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                1.3f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(ANIMATORDELAY).start();
    }


    public void setNextBtTip(String str) {
        tv_select.setText(str);
    }

    public void setTitle(String str) {
        tv_title.setText(str);
    }

    public int disScrollUnit(SCROLLTYPE... scrolltypes) {
        if (scrolltypes == null || scrolltypes.length == 0)
            scrollUnits = SCROLLTYPE.HOUR.value + SCROLLTYPE.MINUTE.value;
        for (SCROLLTYPE scrolltype : scrolltypes) {
            scrollUnits ^= scrolltype.value;
        }
        return scrollUnits;
    }

    MODE mode;

    public void setMode(MODE mode) {
        this.mode = mode;
        switch (mode.value) {
            case 0:
                disScrollUnit(SCROLLTYPE.HOUR, SCROLLTYPE.MINUTE);
                day_pv.setVisibility(View.GONE);
                hour_pv.setVisibility(View.GONE);
                minute_pv.setVisibility(View.GONE);
                hour_text.setVisibility(View.GONE);
                minute_text.setVisibility(View.GONE);
                break;
            case 1:
                disScrollUnit(SCROLLTYPE.HOUR, SCROLLTYPE.MINUTE);
                hour_pv.setVisibility(View.GONE);
                minute_pv.setVisibility(View.GONE);
                hour_text.setVisibility(View.GONE);
                minute_text.setVisibility(View.GONE);
                break;
            case 2:
                disScrollUnit();
                hour_pv.setVisibility(View.VISIBLE);
                minute_pv.setVisibility(View.VISIBLE);
                hour_text.setVisibility(View.VISIBLE);
                minute_text.setVisibility(View.VISIBLE);
                break;

        }
    }

    public void setIsLoop(boolean isLoop) {
        this.year_pv.setIsLoop(isLoop);
        this.month_pv.setIsLoop(isLoop);
        this.day_pv.setIsLoop(isLoop);
        this.hour_pv.setIsLoop(isLoop);
        this.minute_pv.setIsLoop(isLoop);
    }
}
