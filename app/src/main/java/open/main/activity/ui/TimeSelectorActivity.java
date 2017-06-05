package open.main.activity.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.utils.AppUtils;
import open.main.utils.ToastUtil;
import open.main.view.WheelView;

public class TimeSelectorActivity extends BaseActivity {

    DatePickerDialog datePickerDialog;//时间选择弹框
    Dialog dialog;
    private static final String[] items=new String[]{"0000","1111","2222","3333","4444","5555","6666","7777","8888", "9999"};

    @InjectView(R.id.timeDialog)
    TextView timeDialog;
    @InjectView(R.id.wheelView)
    TextView wheelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_selector);
        ButterKnife.inject(this);

        try {
            initDatePickerDialog();//Google 原生时间选择
            initWheelViewDialog();//WheelView dialog 控件初始化
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @OnClick({R.id.timeDialog, R.id.wheelView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.timeDialog:
                if (datePickerDialog.isShowing()){
                    datePickerDialog.dismiss();
                }else{
                    datePickerDialog.show();
                }
                break;
            case R.id.wheelView:
                if (dialog.isShowing()){
                    dialog.dismiss();
                }else{
                    dialog.show();
                }
                break;
        }
    }


    public void initDatePickerDialog(){
        int year= Calendar.getInstance().get(Calendar.YEAR);
        int mon=Calendar.getInstance().get(Calendar.MONTH);
        int day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ToastUtil.showToast(TimeSelectorActivity.this,"年:"+year+",月:"+month+",日:"+dayOfMonth);
            }
        },year,mon,day);
    }

    public void initWheelViewDialog(){
        dialog=new Dialog(this);
        View view= LayoutInflater.from(this).inflate(R.layout.date_wheel_dialog,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = AppUtils.getScreenWidth(this);
        window.setAttributes(params);
        window.setWindowAnimations(R.style.AnimBottom);
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        WheelView wheelView= (WheelView) view.findViewById(R.id.wheel);
        wheelView.setOffset(2);
        wheelView.setItems(Arrays.asList(items));
        wheelView.setSeletion(3);

        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                ToastUtil.showToast(TimeSelectorActivity.this,item);
            }
        });
    }

    /**
     * 根据年月获得相应的天
     */
    public void getMonOfDay(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2010); // 2010年
        c.set(Calendar.MONTH, 5); // 6 月
        System.out.println("------------" + c.get(Calendar.YEAR) + "年" +
                (c.get(Calendar.MONTH) + 1) + "月的天数和周数-------------");
        System.out.println("天数：" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("周数：" + c.getActualMaximum(Calendar.WEEK_OF_MONTH));
    }

}
