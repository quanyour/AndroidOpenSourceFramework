package open.main.activity.ui;

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

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.base.BaseFooterViewAdapter;
import open.main.base.BaseViewHolder;

/**
 * 刷新加载
 * github:https://github.com/Aspsine/SwipeToLoadLayout
 *
 * ps：三个控件的id不能改变，不然会报错
 */

public class SwipeToLoadLayoutActivity extends BaseActivity {

    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    SwipeToLoadAdapter adapter;
    int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_to_load_layout);
        ButterKnife.inject(this);

        adapter=new SwipeToLoadAdapter(this);
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        swipeTarget.setAdapter(adapter);

        initData(count);

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                count=1;
                initData(count);
            }
        });

        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                initData(3);
            }
        });
    }

    public void initData(int x){
        if (x==1){
            swipeToLoadLayout.setRefreshing(true);
        }else{
            swipeToLoadLayout.setLoadingMore(true);
        }
        List<String> list=new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add(i+"");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (x==1){
            swipeToLoadLayout.setRefreshing(false);
            adapter.setList(list);
        }else{
            swipeToLoadLayout.setLoadingMore(false);
            adapter.addList(list);
        }

    }


    class SwipeToLoadAdapter extends BaseFooterViewAdapter {
        Context context;

        protected SwipeToLoadAdapter(Context context) {
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
