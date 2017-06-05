package open.main.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import open.main.R;


/**
 * Created by linke on 2015/2/3.
 * 通用工具类
 */
public final class AppUtils {

    /**
     * 获取手机屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;
    }

    /**
     * 获取手机屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.heightPixels;
    }

    /**
     * 转换float为精确到小数点后2位的String
     *
     * @param value float值
     * @return 转换后的String
     */
    public static String convertFloatForTwo(float value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat("0.##");// 精度为小数点后两位
        return df.format(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 转换float为精确到小数点后2位的String
     *
     * @param value float值
     * @return 转换后的String
     */
    public static float convertFloatForTwoFloat(float value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat("0.##");// 精度为小数点后两位
        return Float.parseFloat(df.format(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)));
    }

    /**
     * 转换float为精确到小数点后0位的String
     *
     * @param value
     * @return
     */
    public static String convertFloatForZero(float value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        DecimalFormat df = new DecimalFormat("0");// 精度为小数点后两位
        return String.valueOf(df.format(bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP)));
    }

    /**
     * 判断是否是手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否是固话
     *
     * @param tel
     * @return
     */
    public static boolean isTelNO(String tel) {
        Pattern p = Pattern.compile("0\\d{2}-?\\d{8}|0\\d{3}-?\\d{7}");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /**
     * 判断是否邮箱
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);

        return m.matches();
    }

    /**
     * dp转换为px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 获取系统的SDK版本号
     *
     * @return
     */
    private static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 桌面上是否有快捷按钮
     *
     * @param context 上下文
     * @return 是否有快捷按钮
     */
    private static boolean hasShortcut(Context context) {
        String url;
        if (getSystemVersion() < 8) {
            url = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            url = "content://com.android.launcher2.settings/favorites?notify=true";
        }

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), new String[]{"base_title", "iconResource"},
                "base_title=?", new String[]{context.getString(R.string.app_name)}, null);
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

//    /**
//     * 创建桌面快捷方式
//     *
//     * @param context 上下文
//     */
//    public static void createShortCut(Context context) {
//        if (!hasShortcut(context)) {
//            Intent intent = new Intent(context.getApplicationContext(), SplashAct.class);
//            intent.setAction("android.intent.action.MAIN");
//            intent.addCategory("android.intent.category.LAUNCHER");
//
//            // 创建快捷方式的Intent
//            Intent shortcutIntent = new Intent(
//                    "com.android.launcher.action.INSTALL_SHORTCUT");
//            // 不允许重复创建
//            shortcutIntent.putExtra("duplicate", false);
//            // 需要现实的名称
//            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
//                    context.getString(R.string.app_name));
//            // 快捷图片
//            Parcelable icon = Intent.ShortcutIconResource.fromContext(
//                    context.getApplicationContext(), R.drawable.icon);
//            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//            // 点击快捷图片，运行的程序主入口
//            shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//            // 发送广播。OK
//            context.sendBroadcast(shortcutIntent);
//
//            SPUtils.saveAddShortCut(context, false);
//        }
//    }

    /**
     * android自带计算两经纬度距离的方法
     *
     * @param lon1 第一点的精度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的精度
     * @param lat2 第二点的纬度
     * @return 返回的距离，单位km，精度为小数点后两位
     */
    public static float getDistance(double lon1, double lat1, double lon2,
                                    double lat2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        float distance = results[0] / 1000;
//        DecimalFormat df = new DecimalFormat("0.##");// 精度为小数点后两位
        return distance;
    }

    public static String getDistanceString(double lon1, double lat1, double lon2, double lat2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        float distance = results[0];
        String retDistance = ((int)distance)+"m";
        if(distance >1000) {
            distance = results[0] / 1000;
            distance = convertFloatForTwoFloat(distance);
            retDistance = distance+"km";
        }
        return retDistance;
    }

    /**
     * 从assets 文件夹中获取文件并读取字符串
     *
     * @param context  上下文
     * @param fileName 文件名
     * @return 字符串
     */
    public static String getFromAssets(Context context, String fileName) {
        String result = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            // result = EncodingUtils.getString(buffer, "UTF-8");
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取圆形Bitmap
     *
     * @param bitmap 目标bitmap
     * @return 生成的圆形bitmap
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        //保证是方形，并且从中心画
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        //圆形，所有只用一个

        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 将String的List转换为用逗号隔开的String
     *
     * @param strings
     * @return
     */
    public static String toSplitString(List<String> strings) {
        if (strings == null) {
            return "";
        }
        if (strings.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(strings.size() * 17);
        sb.append(strings.get(0));
        int size = strings.size();
        for (int i = 1; i < size; i++) {
            sb.append(",");
            sb.append(strings.get(i));
        }
        return sb.toString();
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本名称
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 安装文件
     *
     * @param context  上下文
     * @param filePath 文件路径
     */
    public static void installApk(Context context, String filePath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.parse(filePath), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 隐藏输入法
     *
     * @param context     上下文
     * @param windowToken 窗口Token
     */
    public static void hideImm(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }


    /**
     * 显示输入法
     *
     * @param context
     * @param view
     */
    public static void showImm(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 获取圆角Bitmap
     *
     * @param bitmap 原图
     * @param pixels 圆角角度
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str 原字符串
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 半角转全角
     *
     * @param input 原字符串
     * @return
     */
    public static String ToSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 全角转换为半角
     *
     * @param input 原字符串
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 循环滚动ViewPager实际的Index转换为需要显示的Index
     *
     * @param index 实际的大数字Index
     * @param size  viewPager中View数量
     * @return 小数字Index
     */
    public static int convertLargeIndexToSmall(int index, int size) {
        return index % size;
    }

    public static int parseInt(String intVal, int defaultIntVal) {
        int val = defaultIntVal;
        if (TextUtils.isEmpty(intVal) || intVal.equalsIgnoreCase("null")) {
            return val;
        } else {
            try {
                val = Integer.parseInt(intVal);
            } catch (NumberFormatException e) {
                Log.e("----",e.getMessage());
            }
        }
        return val;
    }

    /**
     * 获取Manifest文件配置信息
     *
     * @param context
     * @param key
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getMetaData(Context context, String key) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = context.getPackageManager()
                .getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
        return appInfo.metaData.getString(key);
    }

    /**
     * 获取渠道名称
     *
     * @param context 上下文
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getChannel(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = context.getPackageManager()
                .getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);
        return appInfo.metaData.getString("UMENG_CHANNEL");
    }

    /**
     * 从字符串中截取连续6位数字组合 ([0-9]{" + 6 + "})截取六位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
     *
     * @param str 短信内容
     * @return 截取得到的6位动态密码
     */
    public static String getDynamicPassword(String str) {
        // 6是验证码的位数一般为六位
        Pattern continuousNumberPattern = Pattern.compile("(?<![0-9])([0-9]{"
                + 6 + "})(?![0-9])");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            System.out.print(m.group());
            dynamicPassword = m.group();
        }

        return dynamicPassword;
    }

    /**
     * 取得时间毫秒数
     *
     * @return
     */
    public static String getTimeMilliseconds() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis() + "";
    }

}
