package open.main.activity.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;

public class UiActivity extends BaseActivity {

    @InjectView(R.id.timeSelector)
    TextView timeSelector;
    @InjectView(R.id.other)
    TextView other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.timeSelector, R.id.other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.timeSelector:
                break;
            case R.id.other:
                break;
        }
    }
}
