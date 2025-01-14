package com.example.karen.view

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomEllipsizeTextView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        const val TEXT_MORE = "\u200B 展開"
        const val ELLIPSIS = "…"
        const val GAP_MARK = "\u00A0\u00A0"
    }

    var isEllipsisCounted: Boolean = false
    private val sb = StringBuilder()

    private fun setLabelAfterEllipsis() {
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
        sb.append(text.toString().trimStart().replace(Regex("\\n"), " "))
        if (sb.isEmpty()) {
            text = null
            return
        }
        // 添加省略号和更多标记
        val ellipsisWidth = paint.measureText(ELLIPSIS)
        val textMoreWidth = paint.measureText(TEXT_MORE)
        val gapWidth = paint.measureText(GAP_MARK)

        var totalWidth =
            paint.measureText(sb.toString()) + ellipsisWidth + gapWidth + textMoreWidth * 2
        while (totalWidth >= width * maxLines && sb.isNotEmpty()) {
            sb.deleteCharAt(sb.length - 1)
            totalWidth =
                paint.measureText(sb.toString()) + ellipsisWidth + gapWidth + textMoreWidth * 2
        }
        sb.append(ELLIPSIS)
        totalWidth = paint.measureText(sb.toString()) + gapWidth + textMoreWidth
        if (totalWidth + gapWidth < maxLines * width) {
            do {
                sb.append(GAP_MARK)
                totalWidth += gapWidth
            } while (totalWidth + gapWidth * 4 < width)
        }
        sb.append(TEXT_MORE)
        text = sb
        isEllipsisCounted = true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setLabelAfterEllipsis()
    }
}
