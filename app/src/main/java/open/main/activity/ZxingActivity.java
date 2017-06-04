package open.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.zxing.CaptureActivity;
import open.main.zxing.EncodingHandler;

public class ZxingActivity extends BaseActivity {

    @InjectView(R.id.BC_edt)
    EditText BCEdt;
    @InjectView(R.id.BC_btn)
    Button BCBtn;
    @InjectView(R.id.GOT_btn)
    Button GOTBtn;
    @InjectView(R.id.GOT_img)
    ImageView GOTImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        ButterKnife.inject(this);
    }


    @OnClick({R.id.BC_btn, R.id.GOT_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.BC_btn://生成二维码
                String txt = BCEdt.getText().toString().trim();
                BC_2weima(txt);
                break;
            case R.id.GOT_btn://扫描二维码
                // 打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(ZxingActivity.this,
                        CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(ZxingActivity.this, scanResult, Toast.LENGTH_SHORT).show();
        }
    }

    private void BC_2weima(String str) {
        try {

            String contentString = String.valueOf(str);
            String contentString2 = URLEncoder.encode(contentString.toString(), "utf-8");
            if (!contentString.equals("")) {
                // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
                Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
                        contentString2, 350);
                GOTImg.setImageBitmap(qrCodeBitmap);
            } else {
                Toast.makeText(ZxingActivity.this,
                        "写入字符串为空", Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
