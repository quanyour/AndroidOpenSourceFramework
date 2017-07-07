package open.main.activity.video;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.mabeijianxi.smallvideorecord2.LocalMediaCompress;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
import com.mabeijianxi.smallvideorecord2.model.LocalMediaConfig;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.mabeijianxi.smallvideorecord2.model.OnlyCompressOverBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.config.UrlConfig;

/**
 * small-video-record2
 * <p>
 * github:https://github.com/mabeijianxi/small-video-record
 */

public class VideoConfigActivity extends BaseActivity {

    @InjectView(R.id.goVideo)
    CardView goVideo;
    @InjectView(R.id.goCompress)
    CardView goCompress;
    @InjectView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_config2);
        ButterKnife.inject(this);

        tv.setText("请将代码里面所写的视频地址换成自己的视频存放地址");
    }

    @OnClick({R.id.goVideo, R.id.goCompress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goVideo:
                // 录制
                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                        .smallVideoWidth(360)
                        .smallVideoHeight(480)
                        .recordTimeMax(6 * 1000)
                        .maxFrameRate(20)
                        .videoBitrate(600 * 1000)
                        .captureThumbnailsTime(1)
                        .build();
                MediaRecorderActivity.goSmallVideoRecorder(this, SendSmallVideoActivity.class.getName(), config);
                break;
            case R.id.goCompress:
                tv.setText("正在压缩...请稍等");
                // 选择本地视频压缩
                LocalMediaConfig.Buidler buidler = new LocalMediaConfig.Buidler();
                final LocalMediaConfig config1 = buidler
                        .setVideoPath(UrlConfig.getVideoPath())
                        .captureThumbnailsTime(1)
                        .doH264Compress(new AutoVBRMode())
                        .setFramerate(15)
                        .setScale(1.0f)
                        .build();
                OnlyCompressOverBean onlyCompressOverBean = new LocalMediaCompress(config1).startCompress();
                String size1=getFileSize(new File(UrlConfig.getVideoPath()));
                String size2=getFileSize(new File(onlyCompressOverBean.getVideoPath()));

                tv.setText("原视频大小为:"+size1+"\n"+"压缩后视频为:"+size2);
                break;
        }
    }

    /**
     * 获取一个文件的大小
     * @param file
     * @return
     */
    public String getFileSize(File file){
        double b = 0;
        try {
            FileInputStream fis=new FileInputStream(file);
            byte[] bytes=new byte[1024];
            while (fis.read(bytes)!=-1){
                b=b+bytes.length;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b/1024+"KB";
    }
}
