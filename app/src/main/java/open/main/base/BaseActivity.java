package open.main.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import open.main.R;
import open.main.utils.DpUtils;

/**
 * Created by Cain on 2017/6/4.
 *
 * 父activity ，用来设定一些属性和常规操作
 *
 * 侧滑参考博客：http://www.jianshu.com/p/7660bd03a930?from=jiantop.com
 */

public class BaseActivity extends AppComSwipeBackActivity{
    private SwipeBackLayout mSwipeBackLayout;
    private ViewGroup rootView;//用于添加其他界面的rootView
    protected Toolbar toolbar;
    protected TextView toolbarTitle;
    protected View mProgressView;
    private TextView progressTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SuperContent(savedInstanceState);
//        getSwipeBackLayout().setEnableGesture(false);//关闭右滑返回上一级
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(true);
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        //mSwipeBackLayout.setEdgeSize(200);

    }

    public void SuperContent(Bundle savedInstanceState){
        super.setContentView(R.layout.activity_base);
        rootView = (FrameLayout) findViewById(R.id.root_layout);
        initToolbar();
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            setSupportActionBar(toolbar);
            ActionBar mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setDisplayHomeAsUpEnabled(true);
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setHomeAsUpIndicator(R.mipmap.back);
                mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
                mActionBar.setDisplayUseLogoEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mActionBar.setElevation(DpUtils.dp2px(getResources(), 4));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                    localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                        //将侧边栏顶部延伸至status bar
                        toolbar.setFitsSystemWindows(true);
                        //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                        toolbar.setClipToPadding(false);
                    }
                }
            }
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 设置标题栏中间文字内容
     * @param text
     */
    public void setToolbarTitle(String text){
        if (toolbarTitle!=null){
            toolbarTitle.setText(text);
        }
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        if (rootView == null)
            return;
        rootView.addView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
        initProgressLayout();
    }


    protected void initProgressLayout() {
        if (mProgressView == null) {
            mProgressView = getLayoutInflater().inflate(R.layout.progressbar_layout, rootView, false);
            progressTextView = (TextView) mProgressView.findViewById(R.id.text_progress);
            if (mProgressView != null) {
                mProgressView.setVisibility(View.GONE);
            }
            rootView.addView(mProgressView);
        }
    }


    /**
     * 显示进度条
     *
     * @param text
     */
    public void showProgressView(String text) {
        if (progressTextView != null && !TextUtils.isEmpty(text)) {
            progressTextView.setVisibility(View.VISIBLE);
            progressTextView.setText(text);
        } else if (progressTextView != null && TextUtils.isEmpty(text)) {
            progressTextView.setVisibility(View.GONE);
        }
        if (mProgressView != null) {
            mProgressView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 隐藏进度条
     */
    public void dissmissProgressView(){
        if (mProgressView!=null){
            mProgressView.setVisibility(View.GONE);
        }
    }


    /**
     * 防止控件被暴力点击
     * @param view
     * @param listener
     */
    public static void addViewClick(View view, final View.OnClickListener listener) {
        if (view != null && listener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    v.setEnabled(false);
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            v.setEnabled(false);
                        }
                    },800);
                    listener.onClick(v);
                }
            });
        }
    }
}
