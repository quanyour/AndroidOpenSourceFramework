package open.main.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Cain on 2017/6/5.
 */

public class ToastUtil {

    public static void showToast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

}
