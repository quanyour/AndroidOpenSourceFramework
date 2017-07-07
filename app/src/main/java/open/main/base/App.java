package open.main.base;

import android.app.Application;
import android.os.Environment;

import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;


/**
 * Created by Cain on 2017/7/4.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "5f34993292", true);

        //初始化视频拍摄
        initSmallVideo();
    }

    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/openSource/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/openSource/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/openSource/");
        }
        // 初始化拍摄
        JianXiCamera.initialize(false,null);
    }
}
