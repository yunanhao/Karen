package com.example.karen.view

import android.content.Context
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class ExpandableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var isExpanded = false // 是否已展开
    private var originalText: String = "" // 原始文本
    private val moreText = " 展开" // “展开”按钮文字
    private val lessText = " 收起" // “收起”按钮文字

    override fun setText(text: CharSequence?, type: BufferType?) {
        originalText = text.toString()
        super.setText(text, type)
        updateText()
    }

    private fun updateText() {
        if (isExpanded) {
            // 展开时显示完整文本并添加“收起”
            val spannableString = SpannableString(originalText + lessText)
            spannableString.setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    isExpanded = false
                    updateText()
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false // 去掉下划线
                    ds.color = currentTextColor // 使用文本的默认颜色
                }
            }, spannableString.length - lessText.length, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        } else {
            // 收起时显示两行文本并在末尾添加“展开”
            val layout = createStaticLayout(originalText)
            if (layout.lineCount > 2) {
                // 获取两行的文本范围
                val end = layout.getLineEnd(1)
                val truncatedText = originalText.substring(0, end).trim() + "..."
                val spannableString = SpannableString(truncatedText + moreText)
                spannableString.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        isExpanded = true
                        updateText()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.isUnderlineText = false
                        ds.color = currentTextColor
                    }
                }, spannableString.length - moreText.length, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                text = spannableString
                movementMethod = LinkMovementMethod.getInstance()
            } else {
                // 如果不足两行，直接显示原文本
                text = originalText
            }
        }
    }

    private fun createStaticLayout(text: String): StaticLayout {
        // 创建静态布局，处理换行符和多行显示
        return StaticLayout.Builder
            .obtain(text, 0, text.length, paint, width)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
            .setIncludePad(includeFontPadding)
            .build()
    }
}
