package com.project.base.ui.widget.rich.viewparse

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.widget.TextView
import com.project.base.ui.widget.rich.IViewParse

interface ITextParse {
    fun parse(view: TextView, spanString: SpannableString)
}

abstract class AbsTextParseWrap(private vararg var baseParse: ITextParse) : ITextParse {

    override fun parse(view: TextView, spanString: SpannableString) {
        baseParse.forEach {
            it.parse(view, spanString)
        }

        if(isMatching(view)){
            spanString.setSpan(createSpan(view), 0, spanString.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }

    abstract fun isMatching(view: TextView):Boolean

    abstract fun createSpan(view: TextView):Any
}

class TextViewParse(private var textParse: ITextParse) : IViewParse {

    override fun isMatching(view: View): Boolean {
        return view is TextView
    }

    override fun parse(view: View): SpannableString {
        view as TextView
        if (view.visibility == View.GONE) {
            return SpannableString("")
        }
        if(view.visibility == View.INVISIBLE){
            view.setTextColor(Color.TRANSPARENT)
        }
        val spanText = SpannableString(view.text)
        textParse.parse(view, spanText)
        return spanText
    }
}