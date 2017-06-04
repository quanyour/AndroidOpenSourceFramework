package open.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.activity.ZxingActivity;
import open.main.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.zxing)
    TextView zxing;
    @InjectView(R.id.http)
    TextView http;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.zxing, R.id.http})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zxing://Zxing 扫码界面
                startActivity(new Intent(MainActivity.this, ZxingActivity.class));
                break;
            case R.id.http://Okhttp3 网络请求

                break;
        }
    }
}
