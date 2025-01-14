package com.example.karen.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.example.karen.R

class MyConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    override fun dispatchDraw(canvas: Canvas) {
        if (childCount > 1) {
            // 获取子视图的左上角位置
            val location = IntArray(2)
            val tv = getChildAt(0)
            val m = getChildAt(1)
            // 获取两个子视图的边界
            val rect1 = Rect(tv.left, tv.top, tv.right, tv.bottom)
            val rect2 = Rect(m.left, m.top, m.right, m.bottom)
            // 计算重叠区域
            val overlapRect = Rect()
            if (Rect.intersects(rect1, rect2)) {
                overlapRect.set(
                    maxOf(rect1.left, rect2.left),
                    maxOf(rect1.top, rect2.top),
                    minOf(rect1.right, rect2.right),
                    minOf(rect1.bottom, rect2.bottom)
                )
            }

            // 绘制第一个子视图，排除重叠区域
            if (!overlapRect.isEmpty) {
                canvas.save()
                canvas.clipOutRect(overlapRect) // 排除重叠区域
                canvas.translate(tv.x, tv.y)
                tv.draw(canvas)
                canvas.restore()
            }

            canvas.save()
            canvas.translate(m.x, m.y)
            m.draw(canvas)
            canvas.restore()
        } else {
            super.dispatchDraw(canvas)
        }
    }

}
