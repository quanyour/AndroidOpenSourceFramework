package open.main.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class BaseFooterViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mList;
    protected BaseFooterViewAdapter(Context context) {
        mContext = context;
    }

    public void removeItem(int position) {
        this.mList.remove(position);
        this.notifyDataSetChanged();
    }

    public void removeItem(T obj) {
        this.mList.remove(obj);
        this.notifyDataSetChanged();
    }



    public List<T> getList() {
        return mList;
    }

    public void setList(T[] list) {
        setList(Arrays.asList(list));
    }

    public void setList(List<T> list) {
        if(list==null){
            if(this.mList!=null)
            {
                this.mList.clear();
            }
            notifyDataSetChanged();
            return;
        }
        ArrayList<T> arrayList = new ArrayList<T>(list.size());
        arrayList.addAll(list);
        this.mList = arrayList;
        notifyDataSetChanged();
//        if (list != null)
//            notifyItemRangeInserted(0, list.size());
    }

    public void addList(List<T> list) {
        if (this.mList == null) {
            ArrayList<T> arrayList = new ArrayList<T>(list.size());
            arrayList.addAll(list);
            this.mList = arrayList;
        }
        this.mList.addAll(list);
        notifyDataSetChanged();
//        if (list != null)
//            notifyItemRangeInserted(0, list.size());
    }



    public T getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public int getItemCount() {
        return getAdapterItemCount();
    }


    public int getAdapterItemCount() {
        return mList == null ? 0 : mList.size();
    }

    Context mContext;


    public View inflater(ViewGroup parent, int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    public BaseActivity getActivity() {
        return (BaseActivity) mContext;
    }

    protected Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationX", new float[]{(float) view.getRootView().getWidth(), 0.0F})};
    }

}
