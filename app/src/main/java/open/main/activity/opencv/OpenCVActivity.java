package open.main.activity.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;

/**
 * OpenCV简单使用，基于openCV 3.3
 * 下载地址：http://opencv.org/opencv-3-3.html
 * 参考博客:http://www.cnblogs.com/yunfang/p/6149831.html
 * OpenCV教程：http://www.opencv.org.cn/opencvdoc/2.3.2/html/doc/tutorials/tutorials.html
 * OpenCV相关学习blog：http://blog.csdn.net/hbl_for_android/article/details/51941106
 */

public class OpenCVActivity extends BaseActivity {

    @InjectView(R.id.img_huaishi)
    ImageView imgHuaishi;
    @InjectView(R.id.btn_gray_process)
    Button btnGrayProcess;
    Bitmap srcBitmap;
    Bitmap grayBitmap;
    private static final String TAG = "OpenCVActivity";
    private static boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cv);
        ButterKnife.inject(this);

        btnGrayProcess.setOnClickListener(new ProcessClickListener());
    }


    /**
     * OpenCV库加载并初始化成功后的回调
     */
    private BaseLoaderCallback mLoaderCallback=new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS:
                    Log.i(TAG, "成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.i(TAG, "加载失败");
                    break;
            }
        }

    };


    public void procSrc2Gray(){
        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        grayBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.RGB_565);
        Utils.bitmapToMat(srcBitmap, rgbMat);//convert original bitmap to Mat, R G B.
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
        Log.i(TAG, "procSrc2Gray sucess...");
    }

    private class ProcessClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            procSrc2Gray();
            if(flag){
                imgHuaishi.setImageBitmap(grayBitmap);
                btnGrayProcess.setText("查看原图");
                flag = false;
            }
            else{
                imgHuaishi.setImageBitmap(srcBitmap);
                btnGrayProcess.setText("灰度化");
                flag = true;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}
