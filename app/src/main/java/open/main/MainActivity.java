package open.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.activity.htttp.HttpActivity;
import open.main.activity.ui.UiActivity;
import open.main.activity.video.VideoConfigActivity;
import open.main.activity.zxing.ZxingActivity;
import open.main.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.zxing)
    TextView zxing;
    @InjectView(R.id.http)
    TextView http;
    @InjectView(R.id.ui)
    TextView ui;
    @InjectView(R.id.video)
    TextView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSwipeBackEnable(false);//主界面不允许滑动退出

        setToolbarTitle("主界面");//添加主界面bar文字
    }


    @OnClick({R.id.zxing, R.id.http, R.id.ui,R.id.video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zxing://Zxing 扫码界面
                startActivity(new Intent(MainActivity.this, ZxingActivity.class));
                break;
            case R.id.http://网络请求
                startActivity(new Intent(MainActivity.this, HttpActivity.class));
                break;
            case R.id.ui://UI控件
                startActivity(new Intent(MainActivity.this, UiActivity.class));
                break;
            case R.id.video://短视频录制
                startActivity(new Intent(MainActivity.this, VideoConfigActivity.class));
                break;
        }
    }



}
