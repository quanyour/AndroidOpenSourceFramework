package open.main.activity.dowload;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import open.main.R;
import open.main.base.BaseActivity;
import open.main.base.BaseFooterViewAdapter;
import open.main.base.BaseViewHolder;


/**
 * 多任务下载界面
 */

public class DowLoadActivity extends BaseActivity {

    @InjectView(R.id.button)
    TextView button;
    @InjectView(R.id.list)
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dow_load);
        ButterKnife.inject(this);


    }


    public class DowLoadAdapter extends BaseFooterViewAdapter {

        protected DowLoadAdapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DowViewHolder(inflater(parent,R.layout.adapter_dow_item));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

         class DowViewHolder extends BaseViewHolder{
            @InjectView(R.id.text)
            TextView text;
            @InjectView(R.id.progress)
            ProgressBar progress;

             DowViewHolder(View view) {
                 super(view);
                 ButterKnife.inject(this, view);
            }
        }
    }
}
