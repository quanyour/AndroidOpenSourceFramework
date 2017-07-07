package open.main.activity.ui;

import android.os.Bundle;
import android.util.Log;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import open.main.R;
import open.main.base.BaseActivity;

/**
 * XWALK 官方文档：
 * https://crosswalk-project.org/documentation/getting_started_zh.html
 * <p>
 * XWALK下载页：
 * https://crosswalk-project.org/documentation/downloads_zh.html
 * <p>
 * 实体机请下载ARM
 *
 * 参考博客：
 * http://www.cnblogs.com/tracyjfly/p/5640752.html
 */

public class CrossXwalkActivity extends XWalkActivity {

    @InjectView(R.id.xwalk)
    XWalkView xwalk;

    @Override
    protected void onXWalkReady() {
        Log.e("onXWalkReady","onXWalkReady");

        setContentView(R.layout.activity_xwalk);
        ButterKnife.inject(this);

        //设置允许访问浏览器页面的js方法
        XWalkPreferences.setValue("enable-javascript", true);
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        //获取XWalkView 控件，并让它访问百度
        xwalk.load("http://www.baidu.com", null);


        xwalk.setUIClient(new XWalkUIClient(xwalk){
            @Override
            public void onPageLoadStarted(XWalkView view, String url) {//页面加载前
                Log.e("onPageLoadStarted",url);

            }

            @Override
            public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {//页面加载后
                Log.e("onPageLoadStopped",url+","+status);
            }
        });

        xwalk.setResourceClient(new XWalkResourceClient(xwalk){
            @Override
            public void onLoadStarted(XWalkView view, String url) {//针对于网页里所有资源的加载，例如图片文字等加载都会调用此方法
                Log.e("onLoadStarted",url);
            }

            @Override
            public void onLoadFinished(XWalkView view, String url) {
                Log.e("onLoadFinished",url);//所有资源加载完成后调用
            }

            @Override
            public void onProgressChanged(XWalkView view, int progressInPercent) {//页面加载进度
                Log.e("onProgressChanged",progressInPercent+"");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
