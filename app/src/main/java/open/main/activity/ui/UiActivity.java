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
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.base.BaseFooterViewAdapter;
import open.main.base.BaseViewHolder;

public class UiActivity extends BaseActivity {

    @InjectView(R.id.list)
    RecyclerView list;
    UIAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        ButterKnife.inject(this);

        adapter=new UIAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setList(getData());
    }

    public List<String> getData(){
        List<String> list=new ArrayList<>();
        list.add("时间选择器");
        list.add("图表");
        list.add("轮播图");
        list.add("刷新加载");
        return list;
    }

    class UIAdapter extends BaseFooterViewAdapter {
        Context context;

        protected UIAdapter(Context context) {
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
                    if (TextUtils.equals(s, "时间选择器")) {
                        startActivity(new Intent(UiActivity.this, TimeSelectorActivity.class));
                    }
                    if (TextUtils.equals(s, "图表")) {
                        startActivity(new Intent(UiActivity.this, MPAndroidChartActivity.class));
                    }
                    if (TextUtils.equals(s, "轮播图")) {
                        startActivity(new Intent(UiActivity.this, BannerActivity.class));
                    }
                    if (TextUtils.equals(s, "刷新加载")) {
                        startActivity(new Intent(UiActivity.this, SwipeToLoadLayoutActivity.class));
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
