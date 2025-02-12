package com.project.base.ui.rv;

import androidx.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/22
 *     desc  :
 * </pre>
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewArray = new SparseArray<>();

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(@IdRes final int viewId) {
        View view = viewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewArray.put(viewId, view);
        }
        return (T) view;
    }

    public void setOnClickListener(@IdRes final int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }

    public void setOnLongClickListener(@IdRes final int viewId, View.OnLongClickListener listener) {
        findViewById(viewId).setOnLongClickListener(listener);
    }
}
