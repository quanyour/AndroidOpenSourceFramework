package open.main.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Cain on 2017/6/5.
 */

public class ToastUtil {

    /**
     * 普通显示的Toast
     * @param context
     * @param str
     */
    public static void showToast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    /**
     * 中间显示的Toast
     * @param context
     * @param str
     */
    public static void showToastCenter(Context context,String str){
        Toast  toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 带图片的Toast
     * @param context
     * @param str
     * @param ResId
     */
    public static void showToastImage(Context context,String str,int ResId){
        Toast toast = Toast.makeText(context,
                "带图片的Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(ResId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }
}
