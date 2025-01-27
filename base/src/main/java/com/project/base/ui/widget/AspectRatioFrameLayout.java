package com.project.base.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.project.base.R;

public class AspectRatioFrameLayout extends FrameLayout {

    private final AspectRatioMeasure.Spec mMeasureSpec = new AspectRatioMeasure.Spec();
    private float mAspectRatio = 0;

    public AspectRatioFrameLayout(Context context) {
        super(context);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        analysisAttrs(attrs);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        analysisAttrs(attrs);
    }

    private void analysisAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray gdhAttrs = getContext().obtainStyledAttributes(
                    attrs,
                    R.styleable.AspectRatioView);
            final int indexCount = gdhAttrs.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                final int attr = gdhAttrs.getIndex(i);
                if (attr == R.styleable.AspectRatioView_aspectRatio) {
                    mAspectRatio = gdhAttrs.getFloat(attr, 0);
                }
            }
            gdhAttrs.recycle();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureSpec.width = widthMeasureSpec;
        mMeasureSpec.height = heightMeasureSpec;
        AspectRatioMeasure.updateMeasureSpec(
                mMeasureSpec,
                mAspectRatio,
                getLayoutParams(),
                getPaddingLeft() + getPaddingRight(),
                getPaddingTop() + getPaddingBottom());
        super.onMeasure(mMeasureSpec.width, mMeasureSpec.height);
    }

    public void setAspectRatio(float aspectRatio) {
        if (aspectRatio == mAspectRatio) {
            return;
        }
        mAspectRatio = aspectRatio;
        requestLayout();
    }
}
