package open.main.activity.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.utils.AppUtils;
import open.main.utils.ToastUtil;
import open.main.view.WheelView;
import open.main.view.timeselector.TimeSelectUtil;
import open.main.view.timeselector.TimeSelector;
import open.main.view.timeselector.TimeUtil;

public class TimeSelectorActivity extends BaseActivity {

    DatePickerDialog datePickerDialog;//时间选择弹框
    Dialog dialog;
    private static final String[] items = new String[]{"0000", "1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999"};

    @InjectView(R.id.timeDialog)
    TextView timeDialog;
    @InjectView(R.id.wheelView)
    TextView wheelView;
    @InjectView(R.id.timeSelector_getDate1)
    TextView timeSelectorGetDate1;
    @InjectView(R.id.timeSelector_getDate2)
    TextView timeSelectorGetDate2;
    @InjectView(R.id.timeSelector_ymd)
    TextView timeSelectorYmd;
    @InjectView(R.id.timeSelector_ym)
    TextView timeSelectorYm;


    String firstTime;//用来标识 结束时间的起始时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_selector);
        ButterKnife.inject(this);

        try {
            initDatePickerDialog();//Google 原生时间选择
            initWheelViewDialog();//WheelView dialog 控件初始化
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.timeDialog, R.id.wheelView, R.id.timeSelector_getDate1,R.id.timeSelector_getDate2,
            R.id.timeSelector_ymd,R.id.timeSelector_ym})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.timeDialog:
                if (datePickerDialog.isShowing()) {
                    datePickerDialog.dismiss();
                } else {
                    datePickerDialog.show();
                }
                break;
            case R.id.wheelView:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }
                break;
            case R.id.timeSelector_getDate1:
                TimeSelectUtil.getDate(new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        timeSelectorGetDate1.setText(TimeUtil.format(TimeUtil.parse(time, TimeUtil.FORMAT_YYYYMM), TimeUtil.FORMAT_YYYYMM));
                        firstTime=time;
                    }
                },"开始日期","2010-1-1 00:00",TimeUtil.format(new Date().getTime(), "yyyy-MM-dd HH:mm"),this);
                break;
            case R.id.timeSelector_getDate2:
                TimeSelectUtil.getDate(new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        timeSelectorGetDate2.setText(TimeUtil.format(TimeUtil.parse(time, TimeUtil.FORMAT_YYYYMM), TimeUtil.FORMAT_YYYYMM));
                    }
                },"结束日期","2010-1-1 00:00",firstTime,this);
                break;
            case R.id.timeSelector_ymd:
                TimeSelectUtil.getDate_YMD(new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        timeSelectorYmd.setText(TimeUtil.format(TimeUtil.parse(time, TimeUtil.FORMAT_yyyy_MM_dd), TimeUtil.FORMAT_yyyy_MM_dd));
                    }
                },this,"年月日","2010-1-1 00:00","2025-12-31 00:00",TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_yyyy_MM_dd));
                break;
            case R.id.timeSelector_ym:
                TimeSelectUtil.getDate_YM(new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        timeSelectorYm.setText(TimeUtil.format(TimeUtil.parse(time, TimeUtil.FORMAT_YYYYMM), TimeUtil.FORMAT_YYYYMM));
                    }
                },this,"年月","2010-1-1 00:00","2025-12-31 00:00",TimeUtil.format(System.currentTimeMillis(), TimeUtil.FORMAT_YYYYMM));
                break;
        }
    }


    public void initDatePickerDialog() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int mon = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ToastUtil.showToast(TimeSelectorActivity.this, "年:" + year + ",月:" + month + ",日:" + dayOfMonth);
            }
        }, year, mon, day);
    }

    public void initWheelViewDialog() {
        dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.date_wheel_dialog, null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = AppUtils.getScreenWidth(this);
        window.setAttributes(params);
        window.setWindowAnimations(R.style.AnimBottom);
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        WheelView wheelView = (WheelView) view.findViewById(R.id.wheel);
        wheelView.setOffset(2);
        wheelView.setItems(Arrays.asList(items));
        wheelView.setSeletion(3);

        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                ToastUtil.showToast(TimeSelectorActivity.this, item);
            }
        });
    }

    /**
     * 根据年月获得相应的天
     */
    public void getMonOfDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2010); // 2010年
        c.set(Calendar.MONTH, 5); // 6 月
        System.out.println("------------" + c.get(Calendar.YEAR) + "年" +
                (c.get(Calendar.MONTH) + 1) + "月的天数和周数-------------");
        System.out.println("天数：" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("周数：" + c.getActualMaximum(Calendar.WEEK_OF_MONTH));
    }

}
