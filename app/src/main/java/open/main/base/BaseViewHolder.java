package open.main.base;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import open.main.R;


public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * an exception if the view doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        T result = (T) itemView.findViewById(id);
        if (result == null) {
            throw new IllegalArgumentException("view 0x" + Integer.toHexString(id)
                    + " doesn't exist");
        }
        return result;
    }
    public void initThemeItemView(View v)
    {
        TypedValue typedValue = new TypedValue();
        v.getContext().getTheme()
                .resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        v.setBackgroundResource(typedValue.resourceId);
    }
    public void initThemeItemView()
    {
       initThemeItemView(itemView);
    }
}