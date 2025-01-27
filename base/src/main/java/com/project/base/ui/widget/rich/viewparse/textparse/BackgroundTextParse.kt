package com.project.base.ui.widget.rich.viewparse.textparse

import android.graphics.drawable.ColorDrawable
import android.text.style.BackgroundColorSpan
import android.widget.TextView
import com.project.base.ui.widget.rich.viewparse.AbsTextParseWrap
import com.project.base.ui.widget.rich.viewparse.ITextParse


class BackgroundTextParse(vararg baseParse: ITextParse) : AbsTextParseWrap(*baseParse) {

    override fun isMatching(view: TextView) = view.background is ColorDrawable

    override fun createSpan(view: TextView) =
        BackgroundColorSpan((view.background as ColorDrawable).color)

}