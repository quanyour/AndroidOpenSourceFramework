package open.main.activity.htttp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;

public class HttpActivity extends BaseActivity {

    @InjectView(R.id.okhttp3)
    TextView okhttp3;
    @InjectView(R.id.volley)
    TextView volley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htttp);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.okhttp3, R.id.volley})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.okhttp3://Okhttp3 请求
            startActivity(new Intent(HttpActivity.this,Http3Activity.class));
                break;
            case R.id.volley:

                break;
        }
    }
}
