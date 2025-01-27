package com.project.base.ui.widget.rich

import android.text.SpannableString
import android.view.View
import com.project.base.ui.widget.rich.viewparse.textparse.*
import com.project.base.ui.widget.rich.viewparse.ImageViewParse
import com.project.base.ui.widget.rich.viewparse.OtherViewParse
import com.project.base.ui.widget.rich.viewparse.TextViewParse

interface IViewParse {

    fun isMatching(view: View): Boolean

    fun parse(view: View): SpannableString
}

internal object SpannableManager {

    private val viewParseList = arrayListOf(
        TextViewParse(
            ColorTextParse(
                TextSizeParse(
                    BoldItalicTextParse(
                        ClickTextParse(
                            BackgroundTextParse(
                                StrikethroughTextParse(
                                    UnderlineTextParse()
                                )
                            )
                        )
                    )
                )
            )
        ),
        ImageViewParse(),
        OtherViewParse()
    )


    fun parse(view: View) = viewParseList.first { it.isMatching(view) }.parse(view)


}