package open.main.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Cain on 2017/4/21.
 */

public class OKhttpFileUtils {
    public static final String TAG = "TAG";

    public static String upload(File images,String url) {
        File file = new File(images.getAbsolutePath());
        if (!file.exists()){
            //throw new Exception("");
        }else{
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"pstime\""),
                            RequestBody.create(null, ""))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"userId\""),
                            RequestBody.create(null, ""))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"realpath\""),
                            RequestBody.create(null, ""))
                    .addPart(Headers.of(
                            "Content-Disposition",
                            "form-data; name=\"URL\"; filename=\""+file.getName()+"\""), fileBody)
                    .build();
            Request request = new Request.Builder()
                    .url(url)//form-data is null point
                    .post(requestBody)
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(120*1000, TimeUnit.MILLISECONDS)
                    .readTimeout(120*1000,TimeUnit.MILLISECONDS)
                    .writeTimeout(120*1000,TimeUnit.MILLISECONDS).build();
            Call call=null;
            try {
                call = okHttpClient.newCall(request);
                Response response=call.execute();
                return  OKhttpFileUtils.readResponseStream(response);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (SocketException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(call!=null){
                    call.cancel();
                }
            }
            return "";
        }
        return "";
    }

    public static String readResponseStream(Response response) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        String result = null;
        if (response.code() == 200) {
            try {
                is = response.body().byteStream();
                if (is != null) {
                    bos = new ByteArrayOutputStream();
                    byte b[] = new byte[1024];
                    int i = 0;
                    while ((i = is.read(b, 0, b.length)) != -1) {
                        bos.write(b, 0, i);
                    }
                    result = new String(bos.toByteArray(), "UTF-8");
                } else {
                    result = null;
                }
            } catch (Exception e) {
                result = null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            Log.e(TAG, "error code:" + response.code());
        }
        return result;
    }

}
