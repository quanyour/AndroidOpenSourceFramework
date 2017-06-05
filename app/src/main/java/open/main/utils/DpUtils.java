package open.main.utils;

import android.content.res.Resources;

/**
 * Created by Cain on 2017/6/5.
 *
 * dp px 转化
 */

public class DpUtils {

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }
}
