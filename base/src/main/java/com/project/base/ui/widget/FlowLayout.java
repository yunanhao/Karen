package com.project.base.ui.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

public class FlowLayout extends ViewGroup {

    ListAdapter mAdapter;
    AdapterDataSetObserver mDataSetObserver;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int resultWidth = 0;
        int resultHeight = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int paddingHorizontal = getPaddingLeft() + getPaddingRight();
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int realChildWidth = childWidth + mlp.leftMargin + mlp.rightMargin;
            int realChildHeight = childHeight + mlp.topMargin + mlp.bottomMargin;

            //这里要把左右的padding计算在内，以便下面onLayout能正常显示
            if ((lineWidth + realChildWidth) > sizeWidth - paddingHorizontal) {
                resultWidth = Math.max(lineWidth, realChildWidth);
                resultHeight += realChildHeight;
                lineWidth = realChildWidth;
                lineHeight = realChildHeight;
            } else {
                lineWidth += realChildWidth;
                lineHeight = Math.max(lineHeight, realChildHeight);
            }
            if (i == childCount - 1) {
                resultWidth = Math.max(lineWidth, resultWidth);
                resultHeight += lineHeight;
            }
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : resultWidth,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : resultHeight);
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int flowWidth = getWidth();
        int childLeft = 0;
        int childTop = 0;

        int paddingHorizontal = getPaddingLeft() + getPaddingRight();
        for (int i = 0, childCount = getChildCount(); i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == View.GONE) {
                continue;
            }
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();

            //这里要把左右的padding计算在内，不然会出现字View显示不全的问题(极端情况下)
            if (childLeft + mlp.leftMargin + childWidth + mlp.rightMargin > flowWidth - paddingHorizontal) {
                childTop += (mlp.topMargin + childHeight + mlp.bottomMargin);
                childLeft = 0;
            }
            int left = childLeft + mlp.leftMargin;
            int top = childTop + mlp.topMargin;
            int right = childLeft + mlp.leftMargin + childWidth;
            int bottom = childTop + mlp.topMargin + childHeight;
            childView.layout(left, top, right, bottom);
            childLeft += (mlp.leftMargin + childWidth + mlp.rightMargin);
        }
    }

    @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(@NonNull ListAdapter adapter) {
        if (mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;
        mDataSetObserver = new AdapterDataSetObserver();
        mAdapter.registerDataSetObserver(mDataSetObserver);
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override public void onChanged() {
            super.onChanged();
            reloadData();
        }

        @Override public void onInvalidated() {
            super.onInvalidated();
        }
    }

    private void reloadData() {
        removeAllViews();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final View childView = mAdapter.getView(i, null, this);
            MarginLayoutParams lp =
                    new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 0);
            childView.setLayoutParams(lp);
            addView(childView);
        }
    }



}