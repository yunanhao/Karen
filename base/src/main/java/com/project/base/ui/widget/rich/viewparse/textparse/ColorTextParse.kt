package com.project.base.ui.widget.rich.viewparse.textparse

import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.project.base.ui.widget.rich.viewparse.AbsTextParseWrap
import com.project.base.ui.widget.rich.viewparse.ITextParse

class ColorTextParse(vararg baseParse: ITextParse) : AbsTextParseWrap(*baseParse) {
    override fun isMatching(view: TextView) = true

    override fun createSpan(view: TextView) = ForegroundColorSpan(view.currentTextColor)
}