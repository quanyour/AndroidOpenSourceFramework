package open.main.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * Created by Cain on 2017/7/6.
 *
 */

public class CameraUtils {
    public final static int CAMERA_SUCCESS =2335;

    public static Uri goCamera(Activity context) {
        File file = new File(context.getExternalCacheDir()
                , System.currentTimeMillis() + ".jpg");
        try {
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri outputFileUri=Uri.fromFile(file);
        Intent intent = new Intent();
        if(Build.VERSION.SDK_INT>=24){
            Uri outputContentUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputContentUri);
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        context.startActivityForResult(intent,CAMERA_SUCCESS);
        return outputFileUri;
    }
}
