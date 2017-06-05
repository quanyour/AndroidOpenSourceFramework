package open.main.activity.htttp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.bean.GitBean;
import open.main.config.UrlConfig;
import open.main.utils.GsonUtil;
import open.main.utils.OKHttp3Utils;

public class Http3Activity extends BaseActivity {

    @InjectView(R.id.doHttp)
    TextView doHttp;
    @InjectView(R.id.content)
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http3);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.doHttp, R.id.content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.doHttp:
                doGet();
                break;
            case R.id.content:
                //TODO 暂无操作
                break;
        }
    }

    /**
     * 请求方法  GET
     */
    public void doGet(){
        showProgressView("正在加载");
        new OKHttp3Utils().get(UrlConfig.getUrl(), new OKHttp3Utils.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                GitBean bean= GsonUtil.parseJsonWithGson(data,GitBean.class);
                content.setText(bean.toString());
                dissmissProgressView();
            }

            @Override
            public void onError(String msg) {
                content.setText("请求错误："+msg.toString());
                dissmissProgressView();
            }

        });
    }
}
