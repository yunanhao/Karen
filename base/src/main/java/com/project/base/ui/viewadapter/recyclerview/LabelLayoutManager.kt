package com.project.base.ui.viewadapter.recyclerview

import android.graphics.Rect
import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @author : ydli
 * @description 实现每个item根据自身宽度动态变化动态换行
 * @time : 17:13
 */
class LabelLayoutManager(private val isFullyLayout: Boolean? = false) :
    RecyclerView.LayoutManager() {
    private val cachedViews: SparseArray<View> = SparseArray<View>()
    private val layoutPoints: SparseArray<Rect> = SparseArray<Rect>()
    private var totalWidth = 0
    private var totalHeight = 0
    private var mContentHeight = 0
    private var mOffset = 0

    override fun supportsPredictiveItemAnimations(): Boolean = true

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        for (i in 0 until itemCount) {
            val v = cachedViews[i]
            val rect = layoutPoints[i]
            layoutDecorated(v, rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return dx
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        var shouldOffset = 0
        if (mContentHeight - totalHeight > 0) {
            var targetOffset = mOffset + dy
            if (targetOffset < 0) {
                targetOffset = 0
            } else if (targetOffset > mContentHeight - totalHeight) {
                targetOffset = mContentHeight - totalHeight
            }
            shouldOffset = targetOffset - mOffset
            offsetChildrenVertical(-shouldOffset)
            mOffset = targetOffset
        }

        if (isFullyLayout!!) {
            shouldOffset = dy
        }
        return shouldOffset
    }

    override fun canScrollHorizontally(): Boolean = true

    override fun canScrollVertically(): Boolean = true

    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) = removeAllViews()

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)

        val widthMode = View.MeasureSpec.getMode(widthSpec)
        val heightMode = View.MeasureSpec.getMode(heightSpec)
        val widthSize = View.MeasureSpec.getSize(widthSpec)
        val heightSize = View.MeasureSpec.getSize(heightSpec)

        var height: Int

        when (widthMode) {
            View.MeasureSpec.UNSPECIFIED -> {
            }
            View.MeasureSpec.AT_MOST -> {
            }
            View.MeasureSpec.EXACTLY -> {
            }
        }

        removeAndRecycleAllViews(recycler)
        recycler.clear()
        cachedViews.clear()

        mContentHeight = 0

        totalWidth = widthSize - paddingRight - paddingLeft

        var left = paddingLeft
        var top = paddingTop

        var maxTop = top

        for (i in 0 until itemCount) {
            val v = recycler.getViewForPosition(i)
            addView(v)
            measureChildWithMargins(v, 0, 0)
            cachedViews.put(i, v)
        }

        for (i in 0 until itemCount) {
            val v = cachedViews[i]
            val w = getDecoratedMeasuredWidth(v)
            val h = getDecoratedMeasuredHeight(v)
            if (w > totalWidth - left) {
                left = paddingLeft
                top = maxTop
            }
            val rect = Rect(left, top, left + w, top + h)
            layoutPoints.put(i, rect)
            left += w
            if (top + h >= maxTop) {
                maxTop = top + h
            }
        }

        mContentHeight = maxTop - paddingTop

        height = mContentHeight + paddingTop + paddingBottom

        when (heightMode) {
            View.MeasureSpec.EXACTLY -> height = heightSize
            View.MeasureSpec.AT_MOST -> if (height > heightSize) {
                height = heightSize
            }
            View.MeasureSpec.UNSPECIFIED -> {
            }
        }

        totalHeight = height - paddingTop - paddingBottom

        setMeasuredDimension(widthSize, height)
    }
}