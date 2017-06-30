package open.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import mabeijianxi.camera.util.Log;
import open.main.activity.db.GreenDaoActivity;
import open.main.activity.htttp.HttpActivity;
import open.main.activity.image.ImagCompressActivity;
import open.main.activity.ui.UiActivity;
import open.main.activity.video.VideoConfigActivity;
import open.main.activity.zxing.ZxingActivity;
import open.main.base.BaseActivity;
import open.main.base.BaseFooterViewAdapter;
import open.main.base.BaseViewHolder;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.list)
    RecyclerView list;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            ButterKnife.inject(this);
            setSwipeBackEnable(false);//主界面不允许滑动退出
            setToolbarTitle("主界面");//添加主界面bar文字

            adapter=new MainAdapter(this);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(adapter);

            adapter.setList(getData());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getData(){
        List<String> list=new ArrayList<>();
        list.add("zxing");
        list.add("net");
        list.add("UI");
        list.add("video");
        list.add("greenDao");
        list.add("imageCompress");
        return list;
    }


    class MainAdapter extends BaseFooterViewAdapter {
        Context context;

        protected MainAdapter(Context context) {
            super(context);
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater(parent, R.layout.item_main));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
            final String s = (String) mList.get(position);
            ViewHolder holder= (ViewHolder) holder1;
            holder.text.setText(s);

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.equals(s,"zxing")){
                        startActivity(new Intent(MainActivity.this, ZxingActivity.class));
                    }
                    if (TextUtils.equals(s,"net")){
                        startActivity(new Intent(MainActivity.this, HttpActivity.class));
                    }
                    if (TextUtils.equals(s,"UI")){
                        startActivity(new Intent(MainActivity.this, UiActivity.class));
                    }
                    if (TextUtils.equals(s,"video")){
                        startActivity(new Intent(MainActivity.this, VideoConfigActivity.class));
                    }
                    if (TextUtils.equals(s,"greenDao")){
                        startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
                    }
                    if (TextUtils.equals(s,"imageCompress")){
                        startActivity(new Intent(MainActivity.this, ImagCompressActivity.class));
                    }
                }
            });
        }
    }

    static class ViewHolder extends BaseViewHolder{
        @InjectView(R.id.text)
        TextView text;
        @InjectView(R.id.cv)
        CardView cv;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }

}
