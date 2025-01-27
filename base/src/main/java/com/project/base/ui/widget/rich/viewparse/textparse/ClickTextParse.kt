package com.project.base.ui.widget.rich.viewparse.textparse

import android.widget.TextView
import com.project.base.ui.widget.rich.viewparse.AbsTextParseWrap
import com.project.base.ui.widget.rich.viewparse.ITextParse
import com.project.base.ui.widget.rich.viewparse.common.ClickableSpanImpl


class ClickTextParse(vararg baseParse: ITextParse) : AbsTextParseWrap(*baseParse) {
    override fun isMatching(view: TextView) = view.hasOnClickListeners()
    override fun createSpan(view: TextView) = ClickableSpanImpl(view)
}