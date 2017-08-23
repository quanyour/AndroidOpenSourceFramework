package open.main.activity.htttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.OBConvert;
import open.main.bean.GitBean;
import open.main.config.UrlConfig;
import open.main.utils.GsonUtil;

/**
 * OKGO :https://github.com/jeasonlzy/okhttp-OkGo/
 * <p>
 * OkRx
 */

public class OkGoActivity extends AppCompatActivity {

    @InjectView(R.id.okgo)
    Button okgo;
    @InjectView(R.id.text)
    TextView text;
    public static final String TAG="OkGoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_go);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.okgo)
    public void onViewClicked() {
        doGet();
    }

    /**
     * get请求
     */
    public void doGet(){
        OkGo.<GitBean>get(UrlConfig.getUrl())
                .tag(this)
                .execute(new OBConvert<GitBean>(GitBean.class){
                    @Override
                    public void onSuccess(Response response) {
                        text.setText(response.body().toString());
                    }
                });
    }

    /**
     * post请求
     * ps:无论做什么请求，第一行的泛型一定要加！！！
     */
    public void doPost(){
        OkGo.<GitBean>post(UrlConfig.getUrl())
                .tag(this)//请求的tag，主要用于取消对应的请求
                .isMultipart(true)//强制使用multipart/form-data表单上传
                .isSpliceUrl(true)//强制将params的参数拼接到url后面
                .retryCount(3)//超时重连次数
                .cacheKey("cacheKey")//设置当前请求的缓存key
                .cacheTime(5000)//缓存的过期时间，单位毫秒
                .cacheMode(CacheMode.DEFAULT)//缓存模式
                .headers("hearder1","hearderValue1")//添加请求头参数
                .headers("hearder2","hearderValue2")//支持多请求头参数同时添加
                .params("param1","paramValue1")//添加请求参数
                .params("patam2","paramValue2")//支持多请求参数同时添加
                .params("file1",new File("filepath1"))//可以添加文件上传
                .params("file2",new File("filepath2"))//支持多文件同时添加上传
                .addUrlParams("key",new ArrayList<String>())//这里支持一个key传多个参数
                .addFileParams("key",new ArrayList<File>())//这里支持一个key传多个文件
                .addFileWrapperParams("key",new ArrayList<HttpParams.FileWrapper>())//这里支持一个key传多个文件
                .execute(new Callback<GitBean>() {
                    @Override
                    public void onStart(Request<GitBean, ? extends Request> request) {
                        //UI线程，请求网络之前调用，可以显示对话框，添加/修改/移除 请求参数
                    }

                    @Override
                    public void onSuccess(Response<GitBean> response) {
                        //UI 线程，请求成功后回调
                    }

                    @Override
                    public void onCacheSuccess(Response<GitBean> response) {
                        //UI 线程，缓存读取成功后回调
                    }

                    @Override
                    public void onError(Response<GitBean> response) {
                        //UI线程 ， 请求失败后回调
                    }

                    @Override
                    public void onFinish() {
                        //UI 线程，请求结束后回调，无论网络请求成功还是失败都会调用
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        //UI 线程，文件上传工程中回调，只有请求方式包含请求体才回调
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        //UI 线程，文件上传过程中回调，只有请求方式包含请求体才回调
                    }

                    @Override
                    public GitBean convertResponse(okhttp3.Response response) throws Throwable {
                        return null;
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        OkGo.getInstance().cancelAll();
    }
}
