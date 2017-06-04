package open.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
            case R.id.zxing:

                break;
            case R.id.http:

                break;
        }
    }
}
