package com.project.base.ui.widget.rich.viewparse.textparse

import android.graphics.Typeface
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.widget.TextView
import com.project.base.ui.widget.rich.RichTextLayout
import com.project.base.ui.widget.rich.viewparse.AbsTextParseWrap
import com.project.base.ui.widget.rich.viewparse.ITextParse

class BoldItalicTextParse(vararg baseParse: ITextParse) : AbsTextParseWrap(*baseParse) {

    override fun isMatching(view: TextView) = true
    override fun createSpan(view: TextView): Any {
//        val  textStyle : () -> Int ={
//            if (view.layoutParams is RichTextLayout.LayoutParam){
//                (view.layoutParams as RichTextLayout.LayoutParam).textStyle
//            }else{
//                0
//            }
//        }
//
//        val isBold = textStyle and 1 == 1
//
//        val italic = view.typeface.isItalic

        var textStyle = 0
        var isBold = false
        var italic = false

        if (view.layoutParams is RichTextLayout.LayoutParam) {
            textStyle = (view.layoutParams as RichTextLayout.LayoutParam).textStyle
        } else {
            textStyle = 0
        }
        isBold = (textStyle + 1) == 1

        italic = view.typeface.isItalic
        return if (italic && isBold) {
            StyleSpan(Typeface.BOLD_ITALIC)
        } else if (isBold) {
            StyleSpan(Typeface.BOLD)
        } else if (italic) {
            StyleSpan(Typeface.ITALIC)
        } else {
            TypefaceSpan("default")
        }
    }


}