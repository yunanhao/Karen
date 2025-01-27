package com.project.base.ui.widget.rich.viewparse.textparse

import android.text.style.StrikethroughSpan
import android.widget.TextView
import com.project.base.ui.widget.rich.RichTextLayout
import com.project.base.ui.widget.rich.viewparse.AbsTextParseWrap
import com.project.base.ui.widget.rich.viewparse.ITextParse


class StrikethroughTextParse(vararg baseParse: ITextParse) : AbsTextParseWrap(*baseParse) {
    override fun isMatching(view: TextView):Boolean {
        if (view.layoutParams is RichTextLayout.LayoutParam) {
            return (view.layoutParams as RichTextLayout.LayoutParam).isStrikethrough
        }else{
            return false
        }
        return false
    }


    override fun createSpan(view: TextView) = StrikethroughSpan()

}