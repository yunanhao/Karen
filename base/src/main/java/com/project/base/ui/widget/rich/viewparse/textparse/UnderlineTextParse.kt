package com.project.base.ui.widget.rich.viewparse.textparse

import android.text.style.UnderlineSpan
import android.widget.TextView
import com.project.base.ui.widget.rich.RichTextLayout
import com.project.base.ui.widget.rich.viewparse.AbsTextParseWrap
import com.project.base.ui.widget.rich.viewparse.ITextParse


class UnderlineTextParse(vararg baseParse: ITextParse) : AbsTextParseWrap(*baseParse) {
    override fun isMatching(view: TextView):Boolean {

        if (view.layoutParams is RichTextLayout.LayoutParam){
            return (view.layoutParams as RichTextLayout.LayoutParam).isUnderline
        }else{
            return false
        }

        return false
    }

    override fun createSpan(view: TextView) = UnderlineSpan()
}