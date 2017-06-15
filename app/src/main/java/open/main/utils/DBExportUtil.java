package open.main.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Cain on 2017/6/6.
 *
 * 手机数据库导出类，用来导出手机里的数据库文件
 */

public class DBExportUtil {


//    public void test(){
//        try {
//            String path="/data/data/com.biz.sfaSanQuan"+ File.separator + "databases" + File.separator;
//            File file = new File(path);
//            for (String s : file.list()) {
//                Log.e("E", s);
//                copyFile(path+s, Global.FILE_SAVE_TEMP_DIR+""+s);
//            }
//        } catch (Exception e) {
//            Log.e("E", ""+e.getMessage());
//        }
//
//    }


    public static boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                File file = new File(newPath);
                File dir = file.getParentFile();
                if (dir.exists() == false) {
                    dir.mkdirs();
                }
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                OutputStream myOutput = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    myOutput.write(buffer, 0, byteread);
                }
                inStream.close();
                try {
                    myOutput.flush();
                } catch (Exception e2) {
                }
                try {
                    myOutput.close();
                } catch (Exception e2) {
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
