package com.example.karen.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CustomEllipsizeTextView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TEXT_MORE = "\u200B 展開"
        const val ELLIPSIS = "…"
        const val GAP_MARK = "\u00A0\u00A0"
    }

    var isEllipsisCounted: Boolean = false
    private val sb = StringBuilder()

    fun fff(){
        TextView(context).apply {
            if (maxLines == Int.MAX_VALUE || width == 0) return
            val lineCount = StaticLayout.Builder
                .obtain(text, 0, text.length, paint, width)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
                .setIncludePad(includeFontPadding)
                .build().lineCount
            if (lineCount < maxLines) return // 没有超出最大行数
            if (paint.measureText(text.toString()) < width * maxLines) return
            sb.clear()
            sb.append(
                text.toString().trimStart().replace(Regex("\\n"), " ")
            )
            if (sb.isEmpty()) {
                text = null
                return
            }
            // 添加省略号和更多标记
            val ellipsisWidth = paint.measureText(ELLIPSIS)
            val textMoreWidth = paint.measureText(TEXT_MORE)
            val gapWidth = paint.measureText(GAP_MARK)

            var totalWidth =
                paint.measureText(sb.toString()) + ellipsisWidth + gapWidth + textMoreWidth * 3
            while (totalWidth >= width * maxLines && sb.isNotEmpty()) {
                sb.deleteCharAt(sb.length - 1)
                totalWidth =
                    paint.measureText(sb.toString()) + ellipsisWidth + gapWidth + textMoreWidth * 3
            }
            sb.append(ELLIPSIS)
            text = sb
            isEllipsisCounted = true
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (childCount < 2) {
            super.dispatchDraw(canvas)
            return
        }
        // 假设只有两个子视图
        val firstChild = getChildAt(0)
        val secondChild = getChildAt(1)

        // 获取两个子视图的边界
        val rect1 = Rect(firstChild.left, firstChild.top, firstChild.right, firstChild.bottom)
        val rect2 = Rect(secondChild.left, secondChild.top, secondChild.right, secondChild.bottom)

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
            canvas.clipRect(0, 0, width, height)
            canvas.clipOutRect(overlapRect) // 排除重叠区域
            firstChild.draw(canvas)
            canvas.restore()
        } else {
//            firstChild.draw(canvas)
        }

        // 绘制第二个子视图
//        secondChild.draw(canvas)
    }
}
