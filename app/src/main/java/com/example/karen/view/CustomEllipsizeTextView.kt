package com.example.karen.view

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomEllipsizeTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    companion object {
        const val TEXT_MORE = "\u200B 展開"
    }

    var isEllipsisCounted: Boolean = false

    private val sb = StringBuilder()

    private fun setLabelAfterEllipsis() {
        StaticLayout.Builder
            .obtain(text, 0, text.length, paint, width)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
            .setIncludePad(includeFontPadding)
            .build()
        if (maxLines == Int.MAX_VALUE) return
        val ellipsisCount = layout.getEllipsisCount(maxLines - 1)
        if (ellipsisCount == 0) return
        val start = layout.getLineStart(0)
        val end = layout.getLineEnd(lineCount - 1) - ellipsisCount
        if (start < 0) return
        if (start >= end) return
        val baseText = text.toString().trimStart().replace("\n", "\uFEFF")
        sb.clear()
        if (baseText.isBlank()) {
            text = sb
            return
        }
        if (baseText.length < end) return
        sb.append(baseText.substring(start, end))
        while (sb.lastOrNull()?.isWhitespace() == true) {
            sb.deleteRange(sb.length - 1, sb.length)
        }

        val cutMark = "……"
        val markWidth = paint.measureText(cutMark)
        val gapMark = "\u00A0\u00A0"
        val gapWidth = paint.measureText(gapMark)
        val textMoreWidth = paint.measureText(TEXT_MORE)
        var totalWidth = paint.measureText(sb.toString()) + markWidth + gapWidth + textMoreWidth

        while (totalWidth >= maxLines * width) {
            sb.deleteRange(sb.length - 1, sb.length)
            totalWidth = paint.measureText(sb.toString()) + markWidth + gapWidth + textMoreWidth
        }
        sb.append(cutMark)
        totalWidth = paint.measureText(sb.toString()) + gapWidth + textMoreWidth
        for (i in 1..maxLines) {
            if (totalWidth + gapWidth < i * width) {
                do {
                    sb.append(gapMark)
                    totalWidth = if (i == maxLines) {
                        paint.measureText(sb.toString()) + markWidth + gapWidth + textMoreWidth
                    } else {
                        paint.measureText(sb.toString()) + markWidth + gapWidth
                    }
                } while (totalWidth + gapWidth < i * width)
                if (i != maxLines) {
                    sb.append('\n')
                    totalWidth = paint.measureText(sb.toString()) + markWidth + gapWidth
                }
            }
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
