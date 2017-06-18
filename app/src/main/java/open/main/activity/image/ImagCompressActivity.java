package open.main.activity.image;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mabeijianxi.camera.util.Log;
import open.main.R;
import open.main.base.BaseActivity;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Cain on 2017/6/16.
 * <p>
 * 图片压缩方法
 * 使用和推荐github地址
 * <p>
 * https://github.com/Curzibn/Luban
 * https://github.com/zetbaitsu/Compressor
 * <p>
 * 使用的图片加载框架 - glide - Google推荐的图片加载库，专注于流畅的滚动
 * glidde 参考博客：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2650.html
 *
 */

public class ImagCompressActivity extends BaseActivity {

    @InjectView(R.id.image1)
    ImageView image1;
    @InjectView(R.id.image1_size)
    TextView image1Size;
    @InjectView(R.id.image2)
    ImageView image2;
    @InjectView(R.id.image2_size)
    TextView image2Size;


    /**
     * 拍照选择照片请求标识
     */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int SELECT_PIC_KITKAT = 3;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static String IMAGE_FILE_NAME = "open"+System.currentTimeMillis()+".jpg";
    private String photoUri=Environment.getExternalStorageDirectory()+File.separator+IMAGE_FILE_NAME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_compress);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.image1, R.id.image2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image1:
                showImageSelectorDialog();
                break;
            case R.id.image2:
                break;
        }
    }

    /**
     * 弹出照片选择框  选择从相册选择  还是 拍照获得
     */
    public void showImageSelectorDialog(){
        Dialog dialog=new Dialog(this);
        View content= LayoutInflater.from(this).inflate(R.layout.dialog_image,null);
        dialog.setContentView(content);
        TextView camera= (TextView) content.findViewById(R.id.camera);
        TextView album = (TextView) content.findViewById(R.id.album);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCamera();
            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAlbum();
            }
        });
        if (dialog.isShowing()){
            dialog.dismiss();
        }else{
            dialog.show();
        }
    }

    /**
     * 拍照
     */
    public void goCamera(){
        Intent intentFromCapture = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储,如果指定了目标uri，data就没有数据，如果没有指定uri，则data就返回有数据
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoUri)));
        startActivityForResult(intentFromCapture,
                CAMERA_REQUEST_CODE);
    }

    /**
     * 点击相册
     * http://blog.csdn.net/tempersitu/article/details/20557383
     */
    public void goAlbum(){
        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        intent1.addCategory(Intent.CATEGORY_OPENABLE);
        intent1.setType("image/*");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            startActivityForResult(intent1, SELECT_PIC_KITKAT);
        } else {
            startActivityForResult(intent1, IMAGE_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    UriToPath(data.getData());
                    break;
                case SELECT_PIC_KITKAT:
                    //图库Intent { dat=content://media/external/images/media/555823 flg=0x1 }
                    if(data!=null){
                        Glide.with(this).load(new File(UriToPath(data.getData()))).into(image1);
                        image1Size.setText(getFileSize(new File(UriToPath(data.getData())))+","+
                                new File(UriToPath(data.getData())).getAbsolutePath());
                        compressImage(new File(UriToPath(data.getData())));
                    }
                    break;
                case CAMERA_REQUEST_CODE:
                    //照相机
                    String sdStatus = Environment.getExternalStorageState();
                    // 检测sd是否可用
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(this,"内存卡异常，请检查内存卡插入是否正确",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (data!=null){
                        Log.e("CAMERA_REQUEST_CODE",data.getData()+"");
                    }else{
                        Log.e("photoUri",photoUri);
                        Glide.with(this).load(new File(photoUri)).into(image1);
                        image1Size.setText(getFileSize(new File(photoUri))+","+new File(photoUri).getAbsolutePath());
                        compressImage(new File(photoUri));
                    }
                    break;
            }
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


    /**
     * 图库的照片选择过后加载到ImageView
     * @param uri
     */
    private String UriToPath(Uri uri){
//      uri  content://com.android.providers.media.documents/document/image%3A555050
        Log.e("----","uri:"+uri.toString());
        String filepath= getPath(this,uri);
        //  filepath   storage/emulated/0/DCIM/Cain/1496662998264/1496662998264.jpg
        if(!TextUtils.isEmpty(filepath)){
            return filepath;
        }else{
            return "";
        }
    }


    public void compressImage(File file){
        Luban.with(this)
                .load(file)//传人要压缩的图片
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }
                    @Override
                    public void onSuccess(File file) {
                        Glide.with(getApplicationContext()).load(file).into(image2);
                        image2Size.setText(getFileSize(file)+","+file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();    //启动压缩
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;

                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
