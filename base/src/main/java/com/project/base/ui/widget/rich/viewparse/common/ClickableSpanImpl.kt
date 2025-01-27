package com.project.base.ui.widget.rich.viewparse.common

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.project.base.ui.widget.rich.RichTextLayout

class ClickableSpanImpl(var view: View) : ClickableSpan() {
    override fun onClick(p0: View) {
        view.performClick()
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = (view.layoutParams as RichTextLayout.LayoutParam).isUnderline
    }
}