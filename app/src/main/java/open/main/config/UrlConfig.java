package open.main.config;

import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cain on 2017/6/4.
 *
 * 需要用到的url请求地址
 */

public class UrlConfig {

    /**
     * 接口地址
     * @return
     */
    public static String getUrl(){
        return "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1";
    }

    /**
     * 获取网络图片地址
     * @return
     */
    public static List<String> getImageList(){
        List<String> images = new ArrayList<>();
        images.add("http://tupian.aladd.net/photo12/811.jpg");
        images.add("http://pic1.52shijing.com/52shijing/mb/d/file/20170617/cca64b0643d68972aac59bc9a5087086_1000_1000.jpg");
        images.add("http://img.mp.itc.cn/upload/20170528/4fcd9b66f3814839b283cd09f5e7bb8c_th.jpg");
        images.add("http://n.sinaimg.cn/translate/20170523/FhOU-fyfkzhs9750611.jpg");
        images.add("http://img4.imgtn.bdimg.com/it/u=43182179,3795724289&fm=11&gp=0.jpg");
        return images;
    }


    public static String getVideoPath(){
        return Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/Camera/VID_20170705_170033.mp4";
    }
}
