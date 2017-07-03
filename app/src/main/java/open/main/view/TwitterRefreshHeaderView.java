package open.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;

import open.main.R;

/**
 * Created by Cain on 2017/7/3.
 *
 * 回调的执行顺序是：
 * onFinishInflate - 初始化
 * onReset - 重加载
 *
 * ----------------------------以上两部自动执行
 *
 * onPrepare - 移动前
 * onMove - 移动
 * onRelease -
 *onRefresh -
 * onComplete -
 * onMove
 * onReset
 *
 */

public class TwitterRefreshHeaderView extends SwipeRefreshHeaderLayout {
    public String TAG=TwitterRefreshHeaderView.class.getName();
    private ImageView ivArrow;

    private ImageView ivSuccess;

    private TextView tvRefresh;

    private ProgressBar progressBar;

    private int mHeaderHeight;

    private Animation rotateUp;

    private Animation rotateDown;

    private boolean rotated = false;

    public TwitterRefreshHeaderView(Context context) {
        this(context, null);
    }

    public TwitterRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwitterRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height_twitter);
        rotateUp = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        rotateDown = AnimationUtils.loadAnimation(context, R.anim.rotate_down);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(TAG,"onFinishInflate");
        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        ivArrow = (ImageView) findViewById(R.id.ivArrow);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onRefresh() {
        Log.e(TAG,"onRefresh");
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
        tvRefresh.setText("REFRESHING");
    }

    @Override
    public void onPrepare() {
        Log.e(TAG,"onPrepare");
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        Log.e(TAG,"onMove");
        if (!isComplete) {
            ivArrow.setVisibility(GONE);
            progressBar.setVisibility(VISIBLE);
            ivSuccess.setVisibility(GONE);
            if (y > mHeaderHeight) {
                tvRefresh.setText("RELEASE TO REFRESH");
                if (!rotated) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(rotateUp);
                    rotated = true;
                }
            } else if (y < mHeaderHeight) {
                if (rotated) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(rotateDown);
                    rotated = false;
                }

                tvRefresh.setText("SWIPE TO REFRESH");
            }
        }
    }

    @Override
    public void onRelease() {
        Log.e(TAG,"onRelease");
    }

    @Override
    public void onComplete() {
        Log.e(TAG,"onComplete");
        rotated = false;
        ivSuccess.setVisibility(VISIBLE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        tvRefresh.setText("COMPLETE");
    }

    @Override
    public void onReset() {
        Log.e(TAG,"onReset");
        rotated = false;
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
        progressBar.setVisibility(GONE);
    }
}
