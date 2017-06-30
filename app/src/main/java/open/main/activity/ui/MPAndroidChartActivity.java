package open.main.activity.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import open.main.R;
import open.main.activity.chart.BarChartActivity;
import open.main.activity.chart.HorizontalBarChartActivity;
import open.main.activity.chart.LineChartActivity;
import open.main.activity.chart.PieChartActivity;
import open.main.activity.chart.RadarChartActivitry;
import open.main.base.BaseActivity;
import open.main.base.BaseFooterViewAdapter;
import open.main.base.BaseViewHolder;

/**
 * Created by Cain on 2017/6/27.
 * <p>
 * android图表库MPAndroidChart集成
 * github：https://github.com/PhilJay/MPAndroidChart
 *
 * 因图表类型太多，此处只列举常用的图表
 */

public class MPAndroidChartActivity extends BaseActivity {

    @InjectView(R.id.list)
    RecyclerView list;
    MPAndroidChartAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpandroid_chart);
        ButterKnife.inject(this);

        adapter=new MPAndroidChartAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        adapter.setList(getListData());
    }

    public List<String> getListData(){
        List<String> list=new ArrayList<>();
        list.add("LineChart");
        list.add("BarChart");
        list.add("HorizontalBarChart");
        list.add("PieChart");
        list.add("RadarChart");
        return list;
    }

    class MPAndroidChartAdapter extends BaseFooterViewAdapter {
        Context context;

        protected MPAndroidChartAdapter(Context context) {
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
                    if (TextUtils.equals(s, "LineChart")) {
                        startActivity(new Intent(MPAndroidChartActivity.this, LineChartActivity.class));
                    }
                    if (TextUtils.equals(s, "BarChart")) {
                        startActivity(new Intent(MPAndroidChartActivity.this, BarChartActivity.class));
                    }
                    if (TextUtils.equals(s, "HorizontalBarChart")) {
                        startActivity(new Intent(MPAndroidChartActivity.this, HorizontalBarChartActivity.class));
                    }
                    if (TextUtils.equals(s, "PieChart")) {
                        startActivity(new Intent(MPAndroidChartActivity.this, PieChartActivity.class));
                    }
                    if (TextUtils.equals(s, "RadarChart")) {
                        startActivity(new Intent(MPAndroidChartActivity.this, RadarChartActivitry.class));
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
