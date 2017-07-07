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

import org.xwalk.core.XWalkActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.activity.db.GreenDaoActivity;
import open.main.activity.htttp.HttpActivity;
import open.main.activity.image.ImagCompressActivity;
import open.main.activity.ui.CrossXwalkActivity;
import open.main.activity.ui.UiActivity;
import open.main.activity.video.VideoConfigActivity;
import open.main.activity.zxing.ZxingActivity;
import open.main.base.BaseActivity;
import open.main.base.BaseFooterViewAdapter;
import open.main.base.BaseViewHolder;
import open.main.colorful.Colorful;
import open.main.colorful.setter.ViewGroupSetter;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.list)
    RecyclerView list;
    MainAdapter adapter;


    Colorful mColorful;
    boolean isNight = true;
    @InjectView(R.id.sun)
    TextView sun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            ButterKnife.inject(this);
            setSwipeBackEnable(false);//主界面不允许滑动退出
            setToolbarTitle("主界面");//添加主界面bar文字

            adapter = new MainAdapter(this);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(adapter);

            adapter.setList(getData());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupColorful();
    }


    private void changeThemeWithColorful() {
        if (!isNight) {
            mColorful.setTheme(R.style.DayThemeMain);
        } else {
            mColorful.setTheme(R.style.NightThemeMain);
        }
        isNight = !isNight;
    }

    /**
     * 设置各个视图与颜色属性的关联
     */
//    private void setupColorful() {
//        ViewGroupSetter listViewSetter = new ViewGroupSetter(list);
//        // 绑定ListView的Item View中的news_title视图，在换肤时修改它的text_color属性
//        listViewSetter.childViewTextColor(R.id.news_title, R.attr.text_color);
//
//        // 构建Colorful对象来绑定View与属性的对象关系
//        mColorful = new Colorful.Builder(this)
//                .backgroundDrawable(R.id.root_view, R.attr.root_view_bg)
//                // 设置view的背景图片
//                .backgroundColor(R.id.change_btn, R.attr.btn_bg)
//                // 设置背景色
//                .textColor(R.id.textview, R.attr.text_color)
//                .setter(listViewSetter) // 手动设置setter
//                .create(); // 设置文本颜色
//    }

    /**
     * 设置各个视图与颜色属性的关联
     */
    private void setupColorful() {
        ViewGroupSetter recyclerViewSetter = new ViewGroupSetter(list,
                0);
        recyclerViewSetter.childViewTextColor(R.id.text,
                R.attr.text_color);

        mColorful = new Colorful.Builder(this)
                .backgroundColor(R.id.sun, R.attr.btn_bg) // 设置背景色
                .backgroundColor(R.id.root,R.attr.root_view_bg)//设置背景
                .textColor(R.id.sun,R.attr.text_color)
                .setter(recyclerViewSetter) // 手动设置setter
                .create(); // 设置文本颜色
    }

    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("zxing");
        list.add("net");
        list.add("UI");
        list.add("video");
        list.add("greenDao");
        list.add("imageCompress");
        list.add("XWALK");
        //http://opencv.org/releases.html
        return list;
    }

    /**
     * 换肤参考博客：http://www.jianshu.com/p/af7c0585dd5b
     * ColorFul：参考地址：https://github.com/hehonghui/Colorful
     *
     * @param view
     */
    @OnClick({R.id.sun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sun://日间模式
                changeThemeWithColorful();
                break;
        }
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
            ViewHolder holder = (ViewHolder) holder1;
            holder.text.setText(s);

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.equals(s, "zxing")) {
                        startActivity(new Intent(MainActivity.this, ZxingActivity.class));
                    }
                    if (TextUtils.equals(s, "net")) {
                        startActivity(new Intent(MainActivity.this, HttpActivity.class));
                    }
                    if (TextUtils.equals(s, "UI")) {
                        startActivity(new Intent(MainActivity.this, UiActivity.class));
                    }
                    if (TextUtils.equals(s, "video")) {
                        startActivity(new Intent(MainActivity.this, VideoConfigActivity.class));
                    }
                    if (TextUtils.equals(s, "greenDao")) {
                        startActivity(new Intent(MainActivity.this, GreenDaoActivity.class));
                    }
                    if (TextUtils.equals(s, "imageCompress")) {
                        startActivity(new Intent(MainActivity.this, ImagCompressActivity.class));
                    }
                    if (TextUtils.equals(s,"XWALK")){
                        startActivity(new Intent(MainActivity.this, CrossXwalkActivity.class));
                    }
                }
            });
        }
    }

    static class ViewHolder extends BaseViewHolder {
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
